package com.sg.funcrecyclerlib;

/**
 * Created by SG on 2017/9/15.
 */

public interface LoadListener {
    /**
     * 加载更多
     */
    void onLoadMore();

    /**
     * 下拉刷新
     */
    void onRefresh();
}
