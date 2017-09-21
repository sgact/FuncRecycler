package com.sg.funcrecycler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.sg.funcrecyclerlib.FuncRecycler;
import com.sg.funcrecyclerlib.LoadListener;
import com.sg.funcrecyclerlib.R;
import com.sg.funcrecyclerlib.sample.NumberHeader;
import com.sg.funcrecyclerlib.sample.RoundProgressHeader;
import com.sg.funcrecyclerlib.sample.TextFooter;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FuncRecycler frv;
    private SampleAdaper mAdapter = new SampleAdaper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        frv = (FuncRecycler) findViewById(R.id.frv);
        frv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setmList(generateTestData(0, 30));
        frv.setAdapter(mAdapter);
//        frv.setmHeader(new NumberHeader(this));
//        frv.setmFooter(new TextFooter(this));
        frv.setLoadListener(new LoadListener() {
            @Override
            public void onLoadMore() {
                Toast.makeText(MainActivity.this, "loading", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            handler.sendEmptyMessage(LOAD_MORE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            handler.sendEmptyMessage(REFRESH);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private MyHandler handler = new MyHandler();
    private static final int REFRESH = 1;
    private static final int LOAD_MORE = 2;

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH:
                    mAdapter.setmList(generateTestData(100, 130));
                    mAdapter.notifyDataSetChanged();
                    frv.setRefreshingState(false);
                    break;
                case LOAD_MORE:
                    mAdapter.setmList(generateTestData(0, 60));
                    mAdapter.notifyDataSetChanged();
                    frv.setLoadingMoreState(false);
                    break;
            }
        }
    }

    private List<Integer> generateTestData(int from, int to){
        List<Integer> list = new LinkedList<>();
        for (int  i = 0; i< to - from; i++){
            list.add(i + from);
        }
        return list;
    }
}
