package com.apocalypse.browser.nest.BrowserFrame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.apocalypse.browser.nest.Env.AppEnv;
import com.apocalypse.browser.nest.R;
import com.apocalypse.browser.nest.utils.SimpleLog;

/**
 * Created by Dave on 2016/1/18.
 */
public class NToolBar extends LinearLayout{

    private IWebBrowserDelegate mBrowserDelegate;
    private IMainFrameEventCall mToolBarEventCall;
    public NToolBar(Context context) {
        super(context);
    }

    public NToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void setGoBackEnable(Boolean isEnable){
        findViewById(R.id.backward).setEnabled(isEnable);
    }

    void setGoForwardEnable(Boolean isEnable){
        findViewById(R.id.forward).setEnabled(isEnable);
    }

    public void init(IWebBrowserDelegate browserDelegate, IMainFrameEventCall toolBarEvent){
        mToolBarEventCall = toolBarEvent;
        mBrowserDelegate = browserDelegate;
        initButtons();
    }

    protected void initButtons(){

        ToolBarClickListener toolBarClickListener = new ToolBarClickListener();

        RelativeLayout Item = (RelativeLayout) findViewById(R.id.rl_backward);
        Item.setOnClickListener(toolBarClickListener);

        Item = (RelativeLayout) findViewById(R.id.rl_forward);
        Item.setOnClickListener(toolBarClickListener);

        Item = (RelativeLayout) findViewById(R.id.rl_menu);
        Item.setOnClickListener(toolBarClickListener);

        Item = (RelativeLayout) findViewById(R.id.rl_home);
        Item.setOnClickListener(toolBarClickListener);

        Item = (RelativeLayout) findViewById(R.id.rl_multiwindow);
        Item.setOnClickListener(toolBarClickListener);
    }


    class ToolBarClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_backward: {
                    if (mBrowserDelegate.canGoBack())
                        mBrowserDelegate.goBack();
                }
                break;
                case R.id.rl_forward: {
                    if (mBrowserDelegate.canGoForward())
                        mBrowserDelegate.goForward();
                }
                break;
                case R.id.rl_menu: {
                    mToolBarEventCall.showMainMenu();
                }
                break;
                case R.id.rl_home: {
                    mBrowserDelegate.navigate(AppEnv.HOME_PAGE);
                }
                break;
                case R.id.rl_multiwindow: {
                    mToolBarEventCall.showMultViewsFrame();
                }
                break;

                default:
                    break;
            }
            SimpleLog.d("OnClick", v.toString());
        }
    }
}
