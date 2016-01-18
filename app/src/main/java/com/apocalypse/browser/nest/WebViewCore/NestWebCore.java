package com.apocalypse.browser.nest.WebViewCore;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.apocalypse.browser.nest.Env.AppEnv;

/**
 * Created by Dave on 2016/1/12.
 */
public class NestWebCore implements IWebBrowser{

    private NestWebView mWebView;
    private IWebCoreDelegate mWebCoreDelegate;

    public NestWebCore(Context c, IWebCoreDelegate webCoreDelegate) {
        mWebView = new NestWebView(c);
        mWebCoreDelegate = webCoreDelegate;

        //mast be last call
        initialize();
    }

    protected void initialize(){

        mWebView.setWebViewClient(new NestWebViewClient(mWebCoreDelegate));
        mWebView.setWebChromeClient(new NestWebChromeClient(mWebCoreDelegate));
        mWebView.requestFocusFromTouch();

        WebSettings webSettings = mWebView.getSettings();

        webSettings.setUseWideViewPort(true);

        webSettings.setLoadWithOverviewMode(true);

        webSettings.setJavaScriptEnabled(true);

        webSettings.setSupportZoom(true);

        webSettings.setAllowFileAccess(true);

        webSettings.setNeedInitialFocus(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setLoadsImagesAutomatically(true);

        webSettings.setPluginState(WebSettings.PluginState.ON);

        webSettings.setDefaultTextEncodingName("UTF-8");

        webSettings.setGeolocationDatabasePath(AppEnv.MAIN_CONTEXT.getFilesDir().getPath());
    }

    @Override
    public WebView getView(){
        return mWebView;
    }

    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public boolean canGoForward() {
        return mWebView.canGoForward();
    }

    @Override
    public void goForward() {
        mWebView.goForward();
    }

    @Override
    public String getUrl() {
        return mWebView.getUrl();
    }

    @Override
    public void goBack() {
        mWebView.goBack();
    }

    @Override
    public boolean canGoBack() {
        return mWebView.canGoBack();
    }
}
