package com.sg.funcrecyclerlib.smaple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import com.sg.funcrecyclerlib.FuncHeader;
import com.sg.funcrecyclerlib.FuncRecycler;
import com.sg.funcrecyclerlib.FuncRecyclerBehavior;
import com.sg.funcrecyclerlib.Utils;

/**
 * Created by SG on 2017/9/21.
 */

public class RoundProgressHeader extends View implements FuncHeader {

    private String TAG = "RoundProgressHeader";

    public RoundProgressHeader(Context context) {
        super(context);
        init();
    }

    private Paint mPaint;
    private float mProgress;
    private RectF mCircleRect;
    private int xCenter;//圆心x坐标
    private int radius;//圆半径
    private int xTextStart;//文字起始坐标
    private final String TEXT_LOADING_PROGRESS = "下拉刷新";
    private final String TEXT_LOAD_READY = "释放立即刷新";
    private final String TEXT_LOADING = "正在刷新...";
    private boolean mIsRefreshing = false;

    private void init(){
        mPaint = new Paint();
        mCircleRect = new RectF();
        xCenter = Utils.dp2px(getContext(), 100);
        radius = Utils.dp2px(getContext(), 20);
        xTextStart = Utils.dp2px(getContext(), 160);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hSpec = MeasureSpec.makeMeasureSpec(Utils.dp2px(getContext(), 100), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, hSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int h = getMeasuredHeight();

        int yCenter = (int) ((h * (1 - FuncRecyclerBehavior.REFRESH_THRESHOLD)) + (h * FuncRecyclerBehavior.REFRESH_THRESHOLD / 2));

        setCirclePaint();

        mCircleRect.left = xCenter - radius;
        mCircleRect.top = yCenter - radius;
        mCircleRect.right = xCenter + radius;
        mCircleRect.bottom = yCenter + radius;

        Log.d(TAG, "onDraw: " + mCircleRect);

        canvas.drawArc(mCircleRect, 0, 360 * mProgress, false, mPaint);

        setTextPaint();

        if (mIsRefreshing){
            canvas.drawText(TEXT_LOADING, xTextStart, yCenter + (Utils.getFontHeight(mPaint) / 2), mPaint);
        }else if (mProgress == 1){
            canvas.drawText(TEXT_LOAD_READY, xTextStart, yCenter + (Utils.getFontHeight(mPaint) / 2), mPaint);
        }else{
            canvas.drawText(TEXT_LOADING_PROGRESS, xTextStart, yCenter + (Utils.getFontHeight(mPaint) / 2), mPaint);
        }
    }

    private void setCirclePaint(){
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(Utils.dp2px(getContext(), 5));
    }

    private void setTextPaint(){
        mPaint.setTextSize(Utils.sp2px(getContext(), 20));
        mPaint.setStyle(Paint.Style.FILL);
    }



    @Override
    public void onPullProgress(float progress) {
        mIsRefreshing = false;
        this.mProgress = progress;
        invalidate();
        Log.d(TAG, "onPullProgress: invalidate");
    }

    @Override
    public void onRefresh() {
        mIsRefreshing = true;
        invalidate();
    }
}
