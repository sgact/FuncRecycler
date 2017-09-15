package com.sg.funcrecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.sg.funcrecyclerlib.FuncRecycler;
import com.sg.funcrecyclerlib.LoadListener;
import com.sg.funcrecyclerlib.R;

public class MainActivity extends AppCompatActivity {

    private FuncRecycler frv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        frv = (FuncRecycler) findViewById(R.id.frv);
        frv.setLayoutManager(new LinearLayoutManager(this));
        frv.setAdapter(new SampleAdaper());
        frv.setLoadListener(new LoadListener() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
