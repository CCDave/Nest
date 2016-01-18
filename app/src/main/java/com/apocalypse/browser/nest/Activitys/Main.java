package com.apocalypse.browser.nest.Activitys;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apocalypse.browser.nest.Env.AppEnv;
import com.apocalypse.browser.nest.NestHttpRequester.NestHttpRequest;
import com.apocalypse.browser.nest.R;
import com.apocalypse.browser.nest.WebViewCore.IWebCoreDelegate;
import com.apocalypse.browser.nest.WebViewCore.NestWebCore;
import com.apocalypse.browser.nest.WebViews.ContainWebFrame;
import com.apocalypse.browser.nest.utils.SimpleLog;
import com.apocalypse.browser.nest.utils.UrlUtils;

import java.util.UUID;

/**
 * Created by Dave on 2016/1/13.
 */
public class Main extends Activity implements IWebCoreDelegate {

    private Context mContext;
    private NestWebCore mWebCore;
    private ViewGroup mViewGroup;

    private EditText mAddrEditText;
    private ProgressBar mProgressBar;

    private TabView vBaidu = new TabView();
    private TabView vWangyi = new TabView();

    public class TabView{
        private NestWebCore mWebCore;
        private ViewGroup mViewGroup;
        TabView(){
            mWebCore = null;
            mViewGroup = null;
        }
    }

    private ToolBarClickListener mToolBarClickListener = new ToolBarClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        AppEnv.MAIN_CONTEXT = this;

        Init();
    }

    protected void Init() {
        initWebBrowser();
        initViewControls();
    }

    protected void initViewControls() {
        initTopToolbar();
        initBottomToolbar();
        initProgressBar();
    }

    protected void initProgressBar() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        mProgressBar.setVisibility(ProgressBar.GONE);
    }

    protected void ChangeView(TabView v){
        ContainWebFrame containFrame = (ContainWebFrame) findViewById(R.id.content_frame);
        if (v.mWebCore == null)
            addSecondWebView();

        containFrame.removeAllView();
        containFrame.addContentView(v.mViewGroup);
    }

    protected void initWebBrowser() {
        mWebCore = new NestWebCore(mContext, this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mWebCore.getView().setLayoutParams(layoutParams);
        RelativeLayout rl = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        rl.setLayoutParams(params);
        rl.addView(mWebCore.getView());
        mViewGroup = rl;

        ContainWebFrame containFrame = (ContainWebFrame) findViewById(R.id.content_frame);
        mWebCore.loadUrl("http://www.baidu.com");
        containFrame.addContentView(mViewGroup);

        vBaidu.mWebCore = mWebCore;
        vBaidu.mViewGroup = mViewGroup;
    }

    protected void addSecondWebView() {
        NestWebCore mWebCore2 = new NestWebCore(mContext, this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mWebCore2.getView().setLayoutParams(layoutParams);
        RelativeLayout rl = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        rl.setLayoutParams(params);
        rl.addView(mWebCore2.getView());
        mWebCore2.loadUrl("http://www.163.com");

        vWangyi.mWebCore = mWebCore2;
        vWangyi.mViewGroup = rl;
    }

    class ToolBarClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_backward: {
                    if (mWebCore.canGoBack())
                        mWebCore.goBack();
                }
                break;
                case R.id.rl_forward: {
                    if (mWebCore.canGoForward())
                        mWebCore.goForward();
                }
                break;
                case R.id.rl_menu: {
                    //TestHttpCall();
                    ChangeView(vBaidu);
                }
                break;
                case R.id.rl_home: {
                    mWebCore.loadUrl(AppEnv.HOME_PAGE);
                }
                break;
                case R.id.rl_multiwindow: {
                    ChangeView(vWangyi);
                }
                break;

                default:
                    break;
            }
            SimpleLog.d("OnClick", v.toString());
        }
    }

    protected void initBottomToolbar() {
        RelativeLayout Item = (RelativeLayout) findViewById(R.id.rl_backward);
        Item.setOnClickListener(mToolBarClickListener);

        Item = (RelativeLayout) findViewById(R.id.rl_forward);
        Item.setOnClickListener(mToolBarClickListener);

        Item = (RelativeLayout) findViewById(R.id.rl_menu);
        Item.setOnClickListener(mToolBarClickListener);

        Item = (RelativeLayout) findViewById(R.id.rl_home);
        Item.setOnClickListener(mToolBarClickListener);

        Item = (RelativeLayout) findViewById(R.id.rl_multiwindow);
        Item.setOnClickListener(mToolBarClickListener);
    }

    protected void initTopToolbar() {
        mAddrEditText = (EditText) findViewById(R.id.edit_url);
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
                    mWebCore.loadUrl(url);

                    hideKeyBoard(v);
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
                    mAddrEditText.selectAll();
                } else {
                    // 此处为失去焦点时的处理内容
                    hideKeyBoard(v);
                    mAddrEditText.setText(mWebCore.getUrl());
                }
            }
        });

    }

    protected void hideKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mProgressBar.getVisibility() != ProgressBar.VISIBLE && newProgress != 100) {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }
        mProgressBar.setProgress(newProgress);
        SimpleLog.d("onPageStarted", "Progress : " + String.valueOf(newProgress));
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        findViewById(R.id.forward).setEnabled(mWebCore.canGoForward());
        findViewById(R.id.backward).setEnabled(mWebCore.canGoBack());
        mProgressBar.setVisibility(ProgressBar.GONE);
        SimpleLog.d("onPageFinished", "Comming");
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mAddrEditText.setText(url);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        SimpleLog.d("onPageStarted", "Comming");
    }

    protected void TestHttpCall() {

        String filePath = String.format("%s/%s.tmp", getCacheDir(), UUID.randomUUID().toString() );
        NestHttpRequest.getQueueTask("http://www.aurorabrowser.com/favicon.ico", 3000, 6000, "", filePath,
                new NestHttpRequest.NestHttpCallback() {
                    @Override
                    public void CallBack(NestHttpRequest.NestHttpResultData data) {
                        SimpleLog.d("getQueueTask", data.filePath);
                    }
                });

        //NestHttpRequest.postQueueTask("http://www.aurorabrowser.com/favicon.ico", 3000, 6000, "111111", "PostDataTest", filePath, null);
    }
}
