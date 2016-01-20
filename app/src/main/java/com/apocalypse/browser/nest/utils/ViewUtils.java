package com.apocalypse.browser.nest.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Dave on 2016/1/20.
 */
public class ViewUtils {

    public static Bitmap getMagicBitmap(Bitmap bitmap, float fx, float fy){
        Matrix matrix = new Matrix();
        matrix.postScale(fx,fy); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return resizeBmp;
    }
}
