package com.apocalypse.browser.nest.BrowserFrame;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.apocalypse.browser.nest.R;
import com.apocalypse.browser.nest.utils.SimpleLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dave on 2016/1/18.
 */
public class MultViewsFrame extends RelativeLayout{

    private IMainFrameEventCall mMainFrameEventCall;

    private ListView mListView;
    private List<Map<String, Object>> mData;
    private RelativeLayout mListContent;
    private MultViewAdapter mAdapter;

    public MultViewsFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MultViewsFrame(Context context) {
        super(context);
    }

    public void init(final IMainFrameEventCall mainFrameEventCall ){
        mMainFrameEventCall = mainFrameEventCall;

        mData = new ArrayList<Map<String, Object>>();
        mListView = new ListView(getContext());
        mAdapter = new MultViewAdapter(getContext());
        mListView.setAdapter(mAdapter);

        mListContent = ((RelativeLayout)findViewById(R.id.multviewlist));
        mListContent.addView(mListView);

        findViewById(R.id.gobacktobrowserframe).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                freeMemory();
                //get select ui and change ui
                mMainFrameEventCall.showBrowserFrame();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int nId = ((int) mData.get(position).get("id"));
                freeMemory();
                mainFrameEventCall.changeCurrentView(nId);
                mainFrameEventCall.showBrowserFrame();
            }
        });
    }

    private void freeMemory(){

//        for (Map<String, Object> item : mData){
//            Bitmap image = (Bitmap)item.get("image");
//            image.recycle();
//        }
//        mData.clear();
//        mAdapter.notifyDataSetChanged();
    }

    public void initViews(){
        if (mData.size() != 0){
            mData.clear();
        }

        ArrayList<ITabBrowser> tabList = mMainFrameEventCall.getBrowserData();
        for (int index = 0; index < tabList.size(); index++){
            ITabBrowser tabItem = tabList.get(index);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "title" , tabItem.getTitle() );
            map.put( "id" , tabItem.getID() );
            map.put( "image", tabItem.getWebCacheBitmap());
            mData.add(map);
        }
        mAdapter.notifyDataSetChanged();
    }

    private final class ViewHolder{
        public ImageView image;
        public TextView title;
    }

    private class MultViewAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public MultViewAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.multviewitem_layout, null);
                holder.image = (ImageView)convertView.findViewById(R.id.multviewimage);
                holder.title = (TextView)convertView.findViewById(R.id.multviewtitletext);
                convertView.setTag(holder);

            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }

            if (holder != null){
                holder.image.setImageBitmap((Bitmap) mData.get(position).get("image"));
                holder.title.setText((String)mData.get(position).get("title"));


                if (convertView != null){
                    convertView.setOnTouchListener(new OnTouchListener() {

                        private int startX = 0;
                        private int endX = 0;

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                startX = (int) event.getX();
                                SimpleLog.d("startX", "" + startX);
                                return false;
                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                endX = (int) event.getX();
                                SimpleLog.d("endX", "" + endX);
                                if (startX - endX > 80) {
                                    SimpleLog.d("触发", "左划");
                                    return false;
                                } else if (endX - startX > 80) {
                                    SimpleLog.d("触发", "右划");
                                    return false;
                                } else {
                                    SimpleLog.d("触发","点击");
                                    return false;
                                }
                            }
                            return false;
                        }
                    });
                }
                return convertView;
            }

            return null;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
