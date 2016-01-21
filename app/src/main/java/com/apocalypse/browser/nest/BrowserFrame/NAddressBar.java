package com.apocalypse.browser.nest.BrowserFrame;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.apocalypse.browser.nest.R;
import com.apocalypse.browser.nest.utils.SimpleLog;
import com.apocalypse.browser.nest.utils.SystemFun;
import com.apocalypse.browser.nest.utils.UrlUtils;

/**
 * Created by Dave on 2016/1/18.
 */
public class NAddressBar extends RelativeLayout{
    private EditText mAddrEditText;
    private ProgressBar mProgressBar;

    private String mTitle;
    private String mUrl;

    private IWebBrowserDelegate mBrowserDelegate;
    private IMainFrameEventCall mMainFrameEventCall;

    public NAddressBar(Context context) {
        super(context);

    }

    public NAddressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUrl(String url){mUrl = url;}
    public String getUrl(){return mUrl;}

    public void setTitle(String title){mTitle = title;}
    String getTitle(){return mTitle;}

    public void setEditText(String s){mAddrEditText.setText(s);}
    String getEditText(){return mAddrEditText.getText().toString();}

    public void addEditText(String str){
        int index = mAddrEditText.getSelectionStart();//获取光标所在位置
        Editable edit = mAddrEditText.getEditableText();//获取EditText的文字
        edit.delete(index, mAddrEditText.getSelectionEnd());
        if (index < 0 || index >= edit.length() ){
            edit.append(str);
        }else{
            edit.insert(index, str);//光标所在位置插入文字
        }
    }

    public boolean hasFocus(){
        if (mAddrEditText != null)
            return mAddrEditText.hasFocus();
        return false;
    }

    void setProgressBarVisibility(int v){
        mProgressBar.setVisibility(v);
    }

    void setProgress(View v, int newProgress){
        if (mProgressBar.getVisibility() != ProgressBar.VISIBLE && newProgress != 100) {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        mProgressBar.setProgress(newProgress);

        if (newProgress == 100)
            mProgressBar.setVisibility(ProgressBar.GONE);

        SimpleLog.d("onPageStarted", "Progress : " + String.valueOf(newProgress));
    }

    public void init(IWebBrowserDelegate browserDelegate, IMainFrameEventCall mainFrameEventCall){

        mAddrEditText = null;
        mProgressBar = null;
        mBrowserDelegate = browserDelegate;
        mMainFrameEventCall = mainFrameEventCall;
        initEdit();
        initProgress();
    }

    protected void initProgress(){
        mProgressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        mProgressBar.setVisibility(ProgressBar.GONE);
    }

    protected  void initEdit(){

        mAddrEditText = (EditText)findViewById(R.id.edit_url);
        mAddrEditText.setSelectAllOnFocus(true);
        mAddrEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                SimpleLog.d("onEditorAction", "Coming");
                if (actionId == EditorInfo.IME_ACTION_GO || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    String url = v.getText().toString();
                    if (!UrlUtils.isUrl(url)) {
                        url = "http://m.baidu.com/s?from=1012674q&tn=0&ref=0&st=111041&word=" + url;
                    } else if (!url.contains(UrlUtils.PROTOCOL_MARK)) {
                        url = UrlUtils.HTTP_PREFIX + url;
                    }

                    mBrowserDelegate.navigate(url);
                    SystemFun.hideKeyBoard(getContext(), v);
                    SimpleLog.d("onEditorAction", "Url: " + url.toString());

                    return true;
                }
                return false;
            }
        });

        mAddrEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    mAddrEditText.setText(mUrl);
                    mAddrEditText.selectAll();

                } else {
                    // 此处为失去焦点时的处理内容
                    mAddrEditText.setText(mTitle);
                    SystemFun.hideKeyBoard(getContext(), v);
                }
            }
        });
    }
}
