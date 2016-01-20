package com.apocalypse.browser.nest.BrowserFrame;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.apocalypse.browser.nest.R;
import com.apocalypse.browser.nest.WebViewCore.IWebCoreCallBack;
import com.apocalypse.browser.nest.utils.SimpleLog;

import java.util.ArrayList;

/**
 * Created by Dave on 2016/1/18.
 */
public class BrowserFrame extends RelativeLayout {

    private NAddressBar mAddressBar;
    private NToolBar mToolBar;
    private TabViewManager mTabViewManager;
    private MultViewsFrame mMultViewsFrame;
    private MainMenuView mMainMenuView;

    private IWebCoreCallBack mWebCoreCallBack;

    public BrowserFrame(Context context) { super(context); }
    public BrowserFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(){
        mWebCoreCallBack = new WebCoreCallBack();
        mTabViewManager = new TabViewManager(getContext(),
                (RelativeLayout)findViewById(R.id.contentwebviewframe),
                mWebCoreCallBack);

        mAddressBar = (NAddressBar)findViewById(R.id.addressbar);
        mAddressBar.init(mTabViewManager);

        IMainFrameEventCall mainFrameEventCall = new MainFrameEventCall();

        mToolBar = (NToolBar)findViewById(R.id.toolbar);
        mToolBar.init(mTabViewManager, mainFrameEventCall);

        mMultViewsFrame = (MultViewsFrame)findViewById(R.id.multviewsframe);
        mMultViewsFrame.init(mainFrameEventCall);

        mMainMenuView = (MainMenuView)findViewById(R.id.mainmenu);
        mMainMenuView.init(mainFrameEventCall);
    }


    private class MainFrameEventCall implements IMainFrameEventCall{
        @Override
        public void showMultViewsFrame() {
            mTabViewManager.updateWebCacheBitmap();
            mMultViewsFrame.initViews();
            realShowMultViewsFrame();
        }

        @Override
        public void changeCurrentView(int Id) {
            realChangeCurrentView(Id);
        }

        @Override
        public void showBrowserFrame() {
            realShowBrowserFrame();
        }

        @Override
        public void showMainMenu() {
            realShowMainMenu();
        }

        @Override
        public boolean addNewTab() {
            return mTabViewManager.navigate("http://www.163.com", true, true);
        }

        @Override
        public ArrayList<ITabBrowser> getBrowserData() {
            return mTabViewManager.getData();
        }

        @Override
        public boolean removeTabItam(int id) {
            return mTabViewManager.removeTabItam(id);
        }
    }
    private void realChangeCurrentView(int Id){
        if (mTabViewManager.changeCurrentWebiew(Id)){
            mAddressBar.setTitle(mTabViewManager.getTitle());
            mAddressBar.setUrl(mTabViewManager.getUrl());
            mAddressBar.setEditText(mTabViewManager.getTitle());
        }
    }
    private void realShowMainMenu(){
        if (mMainMenuView.getVisibility() == VISIBLE){
            mMainMenuView.setVisibility(GONE);
        }
        else {
            mMainMenuView.setVisibility(VISIBLE);
        }
    }

    private void realShowMultViewsFrame(){
        mTabViewManager.setVisibility(GONE);
        mAddressBar.setVisibility(GONE);
        mToolBar.setVisibility(GONE);
        mMultViewsFrame.setVisibility(RelativeLayout.VISIBLE);
    }

    private void realShowBrowserFrame(){
        mTabViewManager.setVisibility(VISIBLE);
        mAddressBar.setVisibility(VISIBLE);
        mToolBar.setVisibility(VISIBLE);
        mMultViewsFrame.setVisibility(GONE);
    }

    private class WebCoreCallBack implements IWebCoreCallBack{

        @Override
        public void onReceivedTitle(WebView view, String title) {
            SimpleLog.d("onReceivedTitle", title);
            mAddressBar.setTitle(title);
            mAddressBar.setEditText(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mAddressBar.setProgress(view, newProgress);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mToolBar.setGoBackEnable(mTabViewManager.canGoBack());
            mToolBar.setGoForwardEnable(mTabViewManager.canGoForward());
            mAddressBar.setProgressBarVisibility(ProgressBar.GONE);

            SimpleLog.d("onPageFinished", "Comming");
        }

        @Override
         public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //此处需要判断是否是当前的view
            mAddressBar.setProgressBarVisibility(ProgressBar.VISIBLE);
            mAddressBar.setUrl(url);
            mAddressBar.setEditText(url);
            SimpleLog.d("onPageStarted", "Comming");
        }
    }
}
