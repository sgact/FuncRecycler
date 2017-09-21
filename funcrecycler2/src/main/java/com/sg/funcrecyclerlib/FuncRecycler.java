package com.sg.funcrecyclerlib;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sg.funcrecyclerlib.sample.ProgressFooter;
import com.sg.funcrecyclerlib.sample.RoundProgressHeader;

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
    private FuncRecyclerBehavior mBehavior;

    private LoadListener mListener;
    private RecyclerTouchListener mTouchListener;

    /**
     * 标识是否正在刷新，为了防止调用刷新多次
     */
    private boolean mIsRefreshingState = false;
    /**
     * 标识是否正在加载更多，为了防止调用加载更多多次
     */
    private boolean mIsLoadingMoreState = false;

    /**
     * 初始化：布局、Behavior
     */
    private void init(Context context){
        mFooter = new ProgressFooter(context);
        mHeader = new RoundProgressHeader(context);
        mRecycler = new RecyclerView(context);

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
                    }else if (mBehavior.ismRefreshCondition() && deltaY > 0){
                        dockHeader();
                    }else if(mBehavior.ismLoadMoreCondition() && deltaY < 0){
                        dockFooter();
                    }else{
                        restoreView();
                    }
                    mBehavior.setmRefreshCondition(false);
                    mBehavior.setmLoadMoreCondition(false);
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
        if (!mIsRefreshingState){
            mIsRefreshingState = true;
            if (mListener != null) {
                mListener.onRefresh();
                ((FuncHeader) mHeader).onRefresh();
            }
        }
    }

    /**
     * 让footer保持不动，并且加载更多
     */
    private void dockFooter(){
        ObjectAnimator.ofInt(this, "ScrollY", this.getScrollY(),
                (int)(mFooter.getMeasuredHeight() * mBehavior.LOADMORE_THRESHOLD)).start();
        if (!mIsLoadingMoreState){
            mIsLoadingMoreState = true;
            if (mListener != null) {
                mListener.onLoadMore();
            }
        }
    }

    /**
     * 还原布局 动画
     */
    private void restoreView(){
        ObjectAnimator.ofInt(this, "ScrollY", this.getScrollY(), 0).start();
    }

    /**
     * 还原布局 立即
     */
    private void restoreViewImmediate(){
        this.setScrollY(0);
    }

    /**
     * onMeasure 初始化子View的LayoutParams
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRecycler.getLayoutParams().height = getMeasuredHeight();
        mRecycler.getLayoutParams().width = getMeasuredWidth();
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

    public void setmFooter(View mFooter) {
        this.mFooter = mFooter;
        removeViewAt(getChildCount() - 1);
        addView(mFooter, getChildCount());
    }

    public void setmHeader(View mHeader) {
        this.mHeader = mHeader;
        removeViewAt(0);
        addView(mHeader, 0);
    }

    public void setmRecycler(RecyclerView mRecycler) {
        this.mRecycler = mRecycler;
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

    /**
     * 设置mIsFreshingState，当其为false时，刷新完毕。
     */
    public void setRefreshingState(boolean mIsFreshingState) {
        if (!mIsFreshingState){
            restoreView();
        }
        this.mIsRefreshingState = mIsFreshingState;
    }

    /**
     * 设置mIsLoadingMoreState，当其为false时，加载更多完毕。
     */
    public void setLoadingMoreState(boolean mIsLoadingMoreState) {
        if (!mIsLoadingMoreState){
            restoreViewImmediate();
            mRecycler.scrollBy(0, (int) (mFooter.getMeasuredHeight() * mBehavior.LOADMORE_THRESHOLD));
        }
        this.mIsLoadingMoreState = mIsLoadingMoreState;
    }

}
