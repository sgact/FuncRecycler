package com.sg.funcrecyclerlib.sample;

import android.content.Context;
import android.view.Gravity;

import com.sg.funcrecyclerlib.FuncHeader;
import com.sg.funcrecyclerlib.utils.FuncUtils;

/**
 * Created by SG on 2017/9/21.
 */

public class NumberHeader extends android.support.v7.widget.AppCompatTextView implements FuncHeader {

    public NumberHeader(Context context) {
        super(context);
        setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hSpec = MeasureSpec.makeMeasureSpec(FuncUtils.dp2px(getContext(), 200), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, hSpec);
    }


    @Override
    public void onPullProgress(float progress) {
        setText(100 * progress + "%");
    }

    @Override
    public void onRefresh() {

    }
}
