package com.apocalypse.browser.nest.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.apocalypse.browser.nest.BrowserFrame.BrowserFrame;
import com.apocalypse.browser.nest.BrowserFrame.MultViewsFrame;
import com.apocalypse.browser.nest.Env.AppEnv;
import com.apocalypse.browser.nest.R;
import com.apocalypse.browser.nest.WelcomeFrame.WelcomeFrame;


/**
 * Created by Dave on 2016/1/13.
 */
public class Main extends Activity {

    // Welcome View
    WelcomeFrame mWelcomeFrame = null;

    // Browser View
    BrowserFrame mBrowserFrame = null;

    // MultViews
    MultViewsFrame mMultViewsFrame = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AppEnv.MAIN_CONTEXT = this;

        LayoutInflater inflater = LayoutInflater.from(this);
        mBrowserFrame = (BrowserFrame)inflater.inflate(R.layout.browser_layout, null);
        mBrowserFrame.init();

        mWelcomeFrame = (WelcomeFrame)inflater.inflate(R.layout.welcome_layout, null);

        Boolean isFirstRun = false;
        if (isFirstRun) {
            setContentView(mWelcomeFrame);
        }
        else{
            setContentView(mBrowserFrame);
        }
    }
}
