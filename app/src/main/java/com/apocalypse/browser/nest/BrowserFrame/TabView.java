package com.apocalypse.browser.nest.BrowserFrame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.ThumbnailUtils;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.apocalypse.browser.nest.Env.AppEnv;
import com.apocalypse.browser.nest.WebViewCore.IWebCoreCallBack;
import com.apocalypse.browser.nest.WebViewCore.NestWebCore;
import com.apocalypse.browser.nest.utils.SimpleLog;
import com.apocalypse.browser.nest.utils.ViewUtils;

/**
 * Created by Dave on 2016/1/19.
 */
public class TabView implements IWebCoreCallBack, ITabBrowser {
    private NestWebCore mWebCore;
    private ViewGroup mContentView;
    private Context mContext;
    private IWebCoreCallBack mWebCoreCallBack;
    private Bitmap mSmallBitmap;

    private int mId;
    private String mTitle;

    TabView(Context c, IWebCoreCallBack webCoreCallBack, String url){
        mSmallBitmap = null;
        mContext = c;
        mWebCoreCallBack= webCoreCallBack;
        mWebCore = new NestWebCore(c, this);

        AppEnv.BROWSER_ID_COUNTER ++;
        mId = AppEnv.BROWSER_ID_COUNTER;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mWebCore.getView().setLayoutParams(layoutParams);

        RelativeLayout rl = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        rl.setLayoutParams(params);
        rl.addView(mWebCore.getView());

        mContentView = rl;

        if (url != null)
            mWebCore.loadUrl(url);
    }

    //call back
    @Override
    public void onProgressChanged(WebView view, int newProgress){
        mWebCoreCallBack.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        mTitle = title;
        mWebCoreCallBack.onReceivedTitle(view, title);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        mWebCoreCallBack.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mWebCoreCallBack.onPageStarted(view, url, favicon);
    }

    @Override
    public void destory() {
        SimpleLog.d("TabView", "destory");

    }

    @Override
    public int getID() {
        return mId;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public ViewGroup getContentView(){return mContentView;}

    @Override
    public void updateWebCacheBitmap() {
        if (mSmallBitmap != null){
            if (!mSmallBitmap.isRecycled())
                mSmallBitmap.recycle();
            mSmallBitmap = null;
        }

        if (mSmallBitmap == null){
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(mContentView.getWidth(), (mContentView.getHeight() / 2), Bitmap.Config.RGB_565 );
            Canvas canvas = new Canvas(bitmap);
            mContentView.draw(canvas);
            mSmallBitmap = ViewUtils.getMagicBitmap(bitmap, 0.5f, 0.5f);
            if (!bitmap.isRecycled())
                bitmap.recycle();
        }
    }

    @Override
    public Bitmap getWebCacheBitmap(){
        return mSmallBitmap;
    }

    @Override
    public WebView getView() {
        return mWebCore.getView();
    }

    @Override
    public void loadUrl(String url) {
        mWebCore.loadUrl(url);
    }

    @Override
    public void goBack() {
        mWebCore.goBack();
    }

    @Override
    public boolean canGoBack() {
        return mWebCore.canGoBack();
    }

    @Override
    public boolean canGoForward() {
        return mWebCore.canGoForward();
    }

    @Override
    public String getUrl() { return mWebCore.getUrl(); }

    @Override
    public void goForward() {
        mWebCore.goForward();
    }
}
