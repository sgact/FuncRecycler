package com.sg.funcrecyclerlib.smaple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.view.menu.MenuPresenter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sg.funcrecyclerlib.FuncRecyclerBehavior;
import com.sg.funcrecyclerlib.Utils;

/**
 * Created by SG on 2017/9/21.
 */

public class ProgressFooter extends RelativeLayout {

    public ProgressFooter(Context context) {
        super(context);
        init();
    }

    private ProgressBar mProgressBar;
    private TextView mTextView;
    private final String hint = "正在加载...";

    public void init(){
        mProgressBar = new ProgressBar(getContext());
        mProgressBar.setDrawingCacheBackgroundColor(Color.BLUE);
        addView(mProgressBar);
        mTextView = new TextView(getContext());
        mTextView.setText(hint);
        addView(mTextView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hDp = 70;
        int hSpec = MeasureSpec.makeMeasureSpec(Utils.dp2px(getContext(), hDp), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, hSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int progressRadius = Utils.dp2px(getContext(), 15);
        mProgressBar.layout(Utils.dp2px(getContext(), 80),
                (int)((b - t) * FuncRecyclerBehavior.LOADMORE_THRESHOLD / 2) - progressRadius,
                Utils.dp2px(getContext(), 80) + (2 * progressRadius),
                (int)((b - t) * FuncRecyclerBehavior.LOADMORE_THRESHOLD / 2) - progressRadius + (2 * progressRadius));
        int halfTextHeight = Utils.getFontHeight(mTextView.getPaint()) / 2;
        mTextView.layout(Utils.dp2px(getContext(), 130),
                (int)((b - t) * FuncRecyclerBehavior.LOADMORE_THRESHOLD / 2) - halfTextHeight,
                Utils.dp2px(getContext(), 130) + Utils.getTextLength(mTextView.getPaint(), hint),
                (int)((b - t) * FuncRecyclerBehavior.LOADMORE_THRESHOLD / 2) + halfTextHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
