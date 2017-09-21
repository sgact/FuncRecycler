package com.sg.funcrecyclerlib;

/**
 * 下拉刷新接口
 * Created by SG on 2017/9/21.
 */

public interface FuncHeader{

    void onPullProgress(float progress);
    void onRefresh();

}
