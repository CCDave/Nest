package com.apocalypse.browser.nest.BrowserFrame;

import android.graphics.Bitmap;
import android.view.ViewGroup;

import com.apocalypse.browser.nest.WebViewCore.IWebBrowser;

/**
 * Created by Dave on 2016/1/19.
 */
public interface ITabBrowser extends IWebBrowser {
    public int getID();
    public String getTitle();
    public Bitmap getWebCacheBitmap();
    public ViewGroup getContentView();
}
