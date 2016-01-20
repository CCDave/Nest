package com.apocalypse.browser.nest.BrowserFrame;

import java.util.ArrayList;

/**
 * Created by Dave on 2016/1/19.
 */
public interface IMainFrameEventCall {
    void showMultViewsFrame();
    void showBrowserFrame();
    void showMainMenu();
    boolean addNewTab();
    void changeCurrentView(int Id);
    ArrayList<ITabBrowser> getBrowserData();
    boolean removeTabItam(int id);
}
