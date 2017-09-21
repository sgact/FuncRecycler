package com.sg.funcrecyclerlib.smaple;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.sg.funcrecyclerlib.FuncHeader;

/**
 * Created by SG on 2017/9/21.
 */

public class NumberHeader extends android.support.v7.widget.AppCompatTextView implements FuncHeader {

    public NumberHeader(Context context) {
        super(context);
        setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
    }


    @Override
    public void onPullProgress(float progress) {
        setText(100 * progress + "%");
    }

    @Override
    public void onRefresh() {

    }
}
