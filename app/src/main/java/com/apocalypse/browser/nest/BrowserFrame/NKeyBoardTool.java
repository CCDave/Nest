package com.apocalypse.browser.nest.BrowserFrame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apocalypse.browser.nest.R;
import com.apocalypse.browser.nest.utils.SimpleLog;
import com.apocalypse.browser.nest.utils.SystemFun;

/**
 * Created by Dave on 2016/1/21.
 */
public class NKeyBoardTool extends LinearLayout {

    private int mVisibleHeight = 0;
    private IMainFrameEventCall mMainFrameEventCall;
    public NKeyBoardTool(Context context) {
        super(context);
    }

    public NKeyBoardTool(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(IMainFrameEventCall mainFrameEventCall){
        mMainFrameEventCall = mainFrameEventCall;
        OnClickListener listenClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txv = (TextView)v;
                if (txv != null){
                    mMainFrameEventCall.addEditText(txv.getText().toString());
                    SimpleLog.d("NKeyBoardTool", txv.getText().toString());
                }
            }
        };

        for (int index = 0; index < getChildCount(); index++){
            View v = getChildAt(index);
            if ( v != null ){
                v.setOnClickListener(listenClick);
            }
        }
    }

    public void listenKeyBoardHeight(int nHeight){
        if (mVisibleHeight == 0) {
            mVisibleHeight = nHeight;
            return;
        }
        if (mVisibleHeight == nHeight) {
            return;
        }

        if (nHeight < mVisibleHeight && mMainFrameEventCall.addressEditFocus()){
            //key board tool
            if (SystemFun.isKeyBoardShow(getContext())){
                LinearLayout keyboardTool = (LinearLayout)findViewById(R.id.keyboardtool);
                if (keyboardTool != null){
                    keyboardTool.setY(nHeight - keyboardTool.getHeight());
                    keyboardTool.setVisibility(VISIBLE);
                }
            }
        }
        else{
            LinearLayout keyboardTool = (LinearLayout)findViewById(R.id.keyboardtool);
            if (keyboardTool != null){
                keyboardTool.setVisibility(GONE);
            }
        }

        mVisibleHeight = nHeight;
        SimpleLog.d("Keyboard-Height", "Height: " + String.format("%d",mVisibleHeight));
    }
}
