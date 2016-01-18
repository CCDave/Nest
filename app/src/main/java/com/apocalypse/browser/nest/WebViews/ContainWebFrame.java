package com.apocalypse.browser.nest.WebViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Dave on 2016/1/12.
 */
public class ContainWebFrame extends RelativeLayout {
    public ContainWebFrame(Context context) {
        super(context);
    }

    public ContainWebFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addContentView(ViewGroup view) {
//		getViewTreeObserver().addOnGlobalLayoutListener(mOGLListener);
        addView(view, 0);
    }

    public void removeAllView() {
        if (getChildCount() > 0) {
            removeViewAt(0);
        }
    }
}
