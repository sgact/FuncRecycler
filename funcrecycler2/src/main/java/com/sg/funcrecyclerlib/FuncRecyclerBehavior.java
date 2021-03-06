package com.sg.funcrecyclerlib;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by SG on 2017/9/15.
 */

public class FuncRecyclerBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private final String TAG = getClass().getSimpleName();

    /**
     * 标识下拉的距离足够长
     */
    private boolean mRefreshCondition = false;
    /**
     * 标识上拉加载更多的距离足够长
     */
    private boolean mLoadMoreCondition = false;
    /**
     * 当下拉的距离大于threshold * header#height时开始刷新
     */
    public static float REFRESH_THRESHOLD = 0.7f;
    /**
     *当下拉的距离大于threshold * footer#height时开始刷新
     */
    public static float LOADMORE_THRESHOLD = 0.95f;
    /**
     * 标识是否支持上拉加载更多
     */
    private boolean mIsLoadMoreEnable = true;
    /**
     * 标识是否支持下拉刷新
     */
    private boolean mIsRefreshEnable = true;

    public FuncRecyclerBehavior() {
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        View header = coordinatorLayout.getChildAt(0);
        View footer = coordinatorLayout.getChildAt(2);

        int sy = coordinatorLayout.getScrollY();

        boolean c1 = coordinatorLayout.getScrollY() == 0;//RecyclerView位于初始位置
        boolean c2 = child.canScrollVertically(-1);//能向下滑
        boolean c3 = child.canScrollVertically(1);//能向上滑
        boolean c6 = dy < 0;//向下
        boolean c7 = dy > 0;//向上
        boolean c8 = (coordinatorLayout.getScrollY() * (coordinatorLayout.getScrollY() + dy) < 0);//ScrollY归0

        if ( (!mIsLoadMoreEnable && c7 && c1) ||  (!mIsRefreshEnable && c6 && c1)
                ||  c1 && ((c6 && c2) || (c3 && c7))){
            //滑recycler
        }else{
            //滑整体
            consumed[1] = dy;

            if (c8){
                coordinatorLayout.scrollTo(0, 0);
            }else if (sy + dy < (-header.getMeasuredHeight())){//下拉过多
                coordinatorLayout.scrollTo(0, - header.getMeasuredHeight());
                doRefresh(header, coordinatorLayout);
            }else if (sy + dy + coordinatorLayout.getMeasuredHeight()  >
                    coordinatorLayout.getMeasuredHeight() + footer.getMeasuredHeight()){//上拉过多
                coordinatorLayout.scrollTo(0, footer.getMeasuredHeight());
                doLoadMore(footer, coordinatorLayout);
            }else{
                dy /= 2;
                coordinatorLayout.scrollBy(0, dy);
                if (dy < 0){//下拉刷新
                    doRefresh(header, coordinatorLayout);
                }else{//加载更多
                    //load more
                    doLoadMore(footer, coordinatorLayout);
                }
            }
        }
    }


    /**
     * 下拉刷新
     */
    private void doRefresh(View header, CoordinatorLayout parent) {
        int sy = parent.getScrollY();
        float progress = 1.0f * (-sy) / header.getMeasuredHeight();

        if (progress < REFRESH_THRESHOLD){//下拉的距离不足，展示动画
            ((FuncHeader) header).onPullProgress(progress / REFRESH_THRESHOLD);
        }else{//停靠Header并刷新
            ((FuncHeader) header).onPullProgress(1);
            mRefreshCondition = true;
        }
    }

    /**
     * 上拉加载更多
     */
    private void doLoadMore(View footer, CoordinatorLayout parent){
        int sy = parent.getScrollY();
        float progress = 1.0f * (sy) / footer.getMeasuredHeight();
        if (progress < LOADMORE_THRESHOLD){//上拉的距离不足
        }else{
            mLoadMoreCondition = true;
        }
    }

    public boolean ismRefreshCondition() {
        return mRefreshCondition;
    }

    public void setmRefreshCondition(boolean mRefreshCondition) {
        this.mRefreshCondition = mRefreshCondition;
    }

    public boolean ismLoadMoreCondition() {
        return mLoadMoreCondition;
    }

    public void setmLoadMoreCondition(boolean mLoadMoreCondition) {
        this.mLoadMoreCondition = mLoadMoreCondition;
    }

    /**
     * 仅当RecyclerView 归位时才让它响应fling事件
     */
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, float velocityX, float velocityY) {
        if (coordinatorLayout.getScrollY() != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ismIsLoadMoreEnable() {
        return mIsLoadMoreEnable;
    }

    public void setmIsLoadMoreEnable(boolean mIsLoadMoreEnable) {
        this.mIsLoadMoreEnable = mIsLoadMoreEnable;
    }

    public boolean ismIsRefreshEnable() {
        return mIsRefreshEnable;
    }

    public void setmIsRefreshEnable(boolean mIsRefreshEnable) {
        this.mIsRefreshEnable = mIsRefreshEnable;
    }
}
