package com.sg.funcrecyclerlib.sample;

import android.content.Context;
import android.view.Gravity;

import com.sg.funcrecyclerlib.utils.FuncUtils;

/**
 * Created by SG on 2017/9/21.
 */

public class TextFooter extends android.support.v7.widget.AppCompatTextView {

    public TextFooter(Context context) {
        super(context);
        init();
    }

    private void init(){
        setGravity(Gravity.CENTER);
        setText("正在加载...");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hSpec = MeasureSpec.makeMeasureSpec(FuncUtils.dp2px(getContext(), 70), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, hSpec);
    }
}
