package com.sg.funcrecyclerlib;

import android.content.Context;
import android.view.View;

/**
 * 下拉刷新接口
 * Created by SG on 2017/9/21.
 */

public interface FuncHeader{

    void onPullProgress(float progress);
    void onRefresh();

}
