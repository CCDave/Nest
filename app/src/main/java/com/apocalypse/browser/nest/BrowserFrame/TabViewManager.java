package com.apocalypse.browser.nest.BrowserFrame;

import android.content.Context;
import android.widget.RelativeLayout;
import com.apocalypse.browser.nest.Env.AppEnv;
import com.apocalypse.browser.nest.WebViewCore.IWebCoreCallBack;
import com.apocalypse.browser.nest.utils.SimpleLog;

import java.util.ArrayList;

/**
 * Created by Dave on 2016/1/18.
 */
public class TabViewManager implements IWebBrowserDelegate {

    private Context mContext;
    private RelativeLayout mContentFrame;
    private ArrayList<ITabBrowser> mTabViews;
    private ITabBrowser mCurrentTabView;
    private IWebCoreCallBack mWebCoreCallBack;

    public TabViewManager(Context c, RelativeLayout v, IWebCoreCallBack webCoreCallBack) {
        mWebCoreCallBack = webCoreCallBack;
        mTabViews = new ArrayList<ITabBrowser>();
        mContentFrame = v;
        mContext = c;
        init();
    }

    public void setVisibility(int v){
        mContentFrame.setVisibility(v);
    }

    public void init() {
        initWebCore();
    }

    public ArrayList<ITabBrowser> getData(){return mTabViews;}

    protected void initWebCore() {
        if (mCurrentTabView == null)
            mCurrentTabView = new TabView(mContext, mWebCoreCallBack, AppEnv.HOME_PAGE);

        mTabViews.add(mCurrentTabView);
        mContentFrame.addView(mCurrentTabView.getContentView());
    }

    @Override
    public boolean removeTabItam(int id) {
        boolean isSucceed = false;
        if (mTabViews.size() <= 1){
            return isSucceed;
        }

        for (ITabBrowser tabBrowser : mTabViews){
            if (tabBrowser.getID() == id){
                mTabViews.remove(tabBrowser);
                if (mCurrentTabView.getID() == tabBrowser.getID()){
                    changeCurrentWebiew(mTabViews.get(0).getID());
                }
                isSucceed = true;
                tabBrowser.destory();
                break;
            }
        }
        return isSucceed;
    }

    @Override
    public boolean changeCurrentWebiew(int Id) {
        boolean isSucceed = false;
        if (mCurrentTabView.getID() == Id)
            return isSucceed;
        ITabBrowser newTabItem = null;
        for (ITabBrowser item : mTabViews ){
            if (item.getID() == Id){
                newTabItem = item;
                break;
            }
        }
        if (newTabItem != null){
            mContentFrame.removeAllViews();
            mContentFrame.addView(newTabItem.getContentView());
            mCurrentTabView = newTabItem;
            isSucceed = true;
        }
        return isSucceed;
    }

    @Override
    public void navigate(String url) {
        mCurrentTabView.loadUrl(url);
    }

    @Override
    public boolean navigate(String url, boolean isNewWindow, boolean isBack){
        boolean result = false;
        if (url == null)
            return result;
    
        try {
            ITabBrowser tabItem = new TabView(mContext, mWebCoreCallBack, url);
            mTabViews.add(tabItem);

            mContentFrame.removeAllViews();
            mContentFrame.addView(tabItem.getContentView());
            mCurrentTabView = tabItem;
            result = true;

        }catch (Exception e){
            SimpleLog.e(e);
        }

        return result;
    }

    @Override
    public boolean canGoBack() {
        return mCurrentTabView.canGoBack();
    }

    @Override
    public void goBack() {
        mCurrentTabView.goBack();
    }

    @Override
    public void goForward() {
        mCurrentTabView.goForward();
    }

    @Override
    public String getUrl() {
        return mCurrentTabView.getUrl();
    }

    @Override
    public String getTitle() {
        return mCurrentTabView.getTitle();
    }

    @Override
    public boolean canGoForward() {
        return mCurrentTabView.canGoForward();
    }

    @Override
    public void updateWebCacheBitmap() {
        mCurrentTabView.updateWebCacheBitmap();
    }
}