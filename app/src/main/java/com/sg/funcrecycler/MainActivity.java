package com.sg.funcrecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.sg.funcrecyclerlib.FuncRecycler;
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
    }
}
