package com.sg.funcrecyclerlib;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.logging.LogManager;

/**
 * Created by SG on 2017/9/15.
 */

public class FuncRecycler extends CoordinatorLayout {

    private final String TAG = getClass().getSimpleName();

    public FuncRecycler(Context context) {
        super(context);
        init(context);
    }

    public FuncRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FuncRecycler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private View mFooter, mHeader;
    private RecyclerView mRecycler;
    private int mFooterHeight = 200;
    private int mHeaderHeight = 600;
    private ViewGroup.LayoutParams mHeaderParams, mRecyclerParams, mFooterParams;
    private FuncRecyclerBehavior mBehavior;

    private LoadListener mListener;
    private RecyclerTouchListener mTouchListener;

    /**
     * 标识是否正在刷新，为了防止调用刷新多次
     */
    private boolean mIsFreshingState = false;

    /**
     * 初始化：布局、Behavior
     */
    private void init(Context context){
        mFooter = new ImageView(context);
        mFooter.setBackgroundColor(Color.parseColor("#123456"));
        mHeader = new ImageView(context);
        mHeader.setBackgroundColor(Color.parseColor("#987654"));
        mRecycler = new RecyclerView(context);
        mRecycler.setBackgroundColor(Color.parseColor("#aaaaaa"));

        addView(mHeader);
        addView(mRecycler);
        addView(mFooter);

        mBehavior = new FuncRecyclerBehavior();
        ((LayoutParams) mRecycler.getLayoutParams()).setBehavior(mBehavior);

        mTouchListener = new RecyclerTouchListener();
        mRecycler.setOnTouchListener(mTouchListener);
    }


    private float yDown, yUp;

    private class RecyclerTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    yDown = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    yUp = event.getRawY();
                    float deltaY = yUp - yDown;
                    if (deltaY == 0){
                        //屏蔽对RecyclerView的点击
                    }else if (mBehavior.ismIsGoodLength() && deltaY > 0){
                        dockHeader();
                    }else{
                        restoreView();
                    }
                    mBehavior.setmIsGoodLength(false);
                    break;
            }
            return false;
        }
    }

    /**
     * 让Header保持不动, 并且刷新数据
     */
    private void dockHeader(){
        ObjectAnimator.ofInt(this, "ScrollY", this.getScrollY(),
                -(int)(mHeader.getMeasuredHeight() * mBehavior.REFRESH_THRESHOLD)).start();
        if (!mIsFreshingState){
            mIsFreshingState = true;
            if (mListener != null) {
                mListener.onRefresh();
            }
        }
    }

    /**
     * 还原布局
     */
    private void restoreView(){
        ObjectAnimator.ofInt(this, "ScrollY", this.getScrollY(), 0).start();
    }



    /**
     * onMeasure 初始化子View的LayoutParams
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mHeader.getLayoutParams().height = mHeaderHeight;
        mRecycler.getLayoutParams().height = getMeasuredHeight();
        mFooter.getLayoutParams().height = mFooterHeight;

        mHeader.getLayoutParams().width = getMeasuredWidth();
        mRecycler.getLayoutParams().width = getMeasuredWidth();
        mFooter.getLayoutParams().width = getMeasuredWidth();
    }

    /**
     * 布局
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mHeader.layout(0, -mHeader.getMeasuredHeight(), mHeader.getMeasuredWidth(), 0);
        mRecycler.layout(0, 0, getWidth(), getHeight());
        mFooter.layout(0, getHeight(), getWidth(), getHeight() + mFooter.getMeasuredHeight());
    }

    /**
     * 暴露RecyclerView的setLayoutManager方法
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        mRecycler.setLayoutManager(layoutManager);
    }

    /**
     * 暴露RecyclerView的setLayoutManager方法
     */
    public void setAdapter(RecyclerView.Adapter adapter){
        mRecycler.setAdapter(adapter);
    }

    public View getmFooter() {
        return mFooter;
    }

    public View getmHeader() {
        return mHeader;
    }

    public RecyclerView getmRecycler() {
        return mRecycler;
    }

    public void setLoadListener(LoadListener loadListener){
        this.mListener = loadListener;
    }

}
