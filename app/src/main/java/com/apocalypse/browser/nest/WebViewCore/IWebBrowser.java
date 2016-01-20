package com.apocalypse.browser.nest.WebViewCore;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * Created by Dave on 2016/1/12.
 */
public interface IWebBrowser {
    public WebView getView();

    public void loadUrl(String url);

    public boolean canGoBack();
    public void goBack();

    public boolean canGoForward();
    public void goForward();

    public String getUrl();
}
