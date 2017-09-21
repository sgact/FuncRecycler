package com.sg.funcrecyclerlib;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;

/**
 * Created by SG on 2017/9/21.
 */

public class Utils {

    /**
     * 获得文字的高度
     */
    public static int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    public static int getTextLength(Paint paint, String str){
        int ww = (int) paint.measureText(str, 0, str.length());
        return ww;
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return Math.round(px);
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
