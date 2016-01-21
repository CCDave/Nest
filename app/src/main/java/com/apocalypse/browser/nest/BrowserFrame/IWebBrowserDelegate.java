package com.apocalypse.browser.nest.BrowserFrame;

/**
 * Created by Dave on 2016/1/19.
 */

public interface IWebBrowserDelegate {

    public void navigate(String url);
    public boolean navigate(String url, boolean isNewWindow, boolean isBack);

    public boolean canGoBack();
    public void goBack();

    public boolean canGoForward();
    public void goForward();

    public String getUrl();
    public String getTitle();
    public boolean changeCurrentWebiew(int Id);
    public void updateWebCacheBitmap();
    public boolean removeTabItam(int id);
}
