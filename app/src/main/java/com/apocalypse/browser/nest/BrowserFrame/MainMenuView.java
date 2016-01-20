package com.apocalypse.browser.nest.BrowserFrame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apocalypse.browser.nest.R;
import com.apocalypse.browser.nest.utils.SimpleLog;

/**
 * Created by Dave on 2016/1/19.
 */
public class MainMenuView extends RelativeLayout{

    private IMainFrameEventCall mMainFrameEventCall;
    private TextView mShadow;
    public MainMenuView(Context context) {
        super(context);
    }

    public MainMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(IMainFrameEventCall c){
        mMainFrameEventCall = c;
        mShadow = (TextView)findViewById(R.id.shadowtextview);
        mShadow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                SimpleLog.d("MainMenuView", "onClick");
            }
        });

        mShadow.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setVisibility(GONE);
                SimpleLog.d("MainMenuView", "onTouch");
                return false;
            }
        });

        findViewById(R.id.addfavor).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
