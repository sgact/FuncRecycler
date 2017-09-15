package com.sg.funcrecyclerlib;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by SG on 2017/9/15.
 */

public class FuncRecyclerBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private final String TAG = getClass().getSimpleName();


    /**
     * 加载数据监听
     */
    private LoadListener mListener;
    /**
     * 标识是否正在刷新，为了防止调用刷新多次
     */
    private boolean mIsFreshingState = false;
    /**
     * 当下拉的距离大于threshold * header#height时开始刷新
     */
    private float mRefreshThreshold = 0.8f;




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
        boolean c4 = sy + dy < (-header.getMeasuredHeight());//Header玩全显示
        boolean c5 = sy + dy > coordinatorLayout.getMeasuredHeight() + footer.getMeasuredHeight();//Footer完全显示
        boolean c6 = dy < 0;//向下
        boolean c7 = dy > 0;//向上
        boolean c8 = (coordinatorLayout.getScrollY() * (coordinatorLayout.getScrollY() + dy) < 0);//ScrollY归0


        if (c1 && ((c6 && c2) || (c3 && c7))){
            //滑recycler
        }else{
            //滑整体
            consumed[1] = dy;

            if (sy + dy < (-header.getMeasuredHeight())){
                coordinatorLayout.scrollTo(0, - header.getMeasuredHeight());
            }else if (sy + dy + coordinatorLayout.getMeasuredHeight()  > coordinatorLayout.getMeasuredHeight() + footer.getMeasuredHeight()){
                coordinatorLayout.scrollTo(0, footer.getMeasuredHeight());
            }else if (c8){
                coordinatorLayout.scrollTo(0, 0);
            }else{
                dy /= 2;
                coordinatorLayout.scrollBy(0, dy);

                if (dy < 0){
                    doRefresh(header, coordinatorLayout);
                }else{
                    //load more


                }




            }
        }




//        Log.d(TAG, "onNestedPreScroll: " + c2 + c3 + c6 + c7);
//        Log.d(TAG, "===========================");
//        Log.d(TAG, "child.canScrollVertically(1) : " + child.canScrollVertically(1));
//        Log.d(TAG, "child.canScrollVertically(-1) : " + child.canScrollVertically(-1));
//        Log.d(TAG, "onNestedPreScroll: " + dy);
//        Log.d(TAG, "onNestedPreScroll: " + coordinatorLayout.getScrollY());
//        Log.d(TAG, "===========================");



    }

    /**
     * 下拉刷新
     */
    private void doRefresh(View header, CoordinatorLayout parent) {
        int sy = parent.getScrollY();
        float progress = 1.0f * (-sy) / header.getMeasuredHeight();
        if (progress < mRefreshThreshold){
            Log.d(TAG, "onNestedPreScroll: " + "load prapare");
        }else{
            if (mListener != null && mIsFreshingState) {
                mListener.onRefresh();
                mIsFreshingState = true;
            }
        }
    }

    /**
     * 刷新时让Header保持不动
     */
    private void dockHeader(View header, CoordinatorLayout parent){
        //// TODO: 2017/9/15 写到这了
    }


    public void setLoadListener(LoadListener loadListener){
        this.mListener = loadListener;
    }

    public boolean isFreshingState() {
        return mIsFreshingState;
    }

    public void setFreshingState(boolean mIsFreshingState) {
        this.mIsFreshingState = mIsFreshingState;
    }
}
