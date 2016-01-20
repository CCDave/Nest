package com.apocalypse.browser.nest.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Dave on 2016/1/19.
 */
public class SystemFun {
    static public void hideKeyBoard(Context context, View v) {
        if (context != null && v != null){
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
