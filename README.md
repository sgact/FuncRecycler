# FuncRecycler 支持下拉刷新和上拉加载更多的Recycler
![下拉刷新](https://github.com/sgact/FuncRecycler/blob/master/preview/down.gif)
![下拉刷新](https://github.com/sgact/FuncRecycler/blob/master/preview/up.gif)

### 特性
* 支持自定义Header和Footer,能方便的为Header添加动画
* RecyclerView向上Fling时，不会触发加载更多

###用法
* 监听事件

```java
frv = (FuncRecycler) findViewById(R.id.frv);
frv.setLoadListener(new LoadListener() {
            @Override
            public void onLoadMore() {
                //加载更多
            }

            @Override
            public void onRefresh() {
                //下拉刷新
            }
        });
```
在完成刷新时，你需要调用:
```java
frv.setRefreshingState(false);
```
在完成加载更多时，你需要调用：
```java
frv.setLoadingMoreState(false);
```

* 简单使用

就像RecyclerView一样：
```java
frv.setLayoutManager(new LinearLayoutManager(this));
frv.setAdapter(mAdapter);
```

* 自定义Header
你只需为你的自定义View实现这个接口：

```java
public interface FuncHeader{

    void onPullProgress(float progress);
    void onRefresh();

}
```

onPullProgress会在下拉时被调用，参数progress表示下拉的进度，介于0和1之间，在这个方法中你可以根据progress的值方便的重新绘制你的自定义View。

onRefresh();会在开始刷新时被调用。

完成了你自定义的Header之后,你只需调用

```java
frv.setmHeader(new YourHeader(context));
```

* 自定义Footer

Footer只是一个普通的View，同样你需要调用
```java
frv.setmFooter(new YourFooter(context));
```

* 屏蔽下拉或上拉

你需要根据需要调用这两个方法之一：

```java
  frv.setLoadMoreEnable(false);
  frv.setRefreshEnable(false);
```  

* 设置加载数据的距离阈值

对于下拉刷新你需要调用
```java
  frv.setRefreshThreshold(threshold);
```
threshold的值表示，当你滑动的距离 >= threshold * header#height时，就能触发下拉刷新
threshold的值越小，需要滑动的距离越小。
对于上拉加载更多，你需要调用
```java
  frv.setLoadMoreThreshold(threshold);
```

* RecyclerView的原生操作
FuncRecycler实际上是一个ViewGroup,如果你需要调用FuncRecycler未支持的RecyclerView方法，你只需调用：
```java
  frv.getmRecycler();
```

        











