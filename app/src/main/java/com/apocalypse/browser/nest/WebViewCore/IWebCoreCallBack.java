package com.apocalypse.browser.nest.WebViewCore;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * Created by Dave on 2016/1/12.
 */
public interface IWebCoreCallBack {

    //WebUI
    public void onProgressChanged(WebView view, int newProgress);
    public void onReceivedTitle(WebView view, String title);


    //WebCore
    public void onPageFinished(WebView view, String url);
    public void onPageStarted(WebView view, String url, Bitmap favicon);

}
