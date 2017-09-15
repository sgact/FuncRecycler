package com.sg.funcrecycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sg.funcrecyclerlib.R;

/**
 * Created by SG on 2017/9/15.
 */

public class TestActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
    }

    public void go(View v){
        v.setTranslationY(100);
    }

    public void print(View v){
        Button bt = (Button) findViewById(R.id.go);
        Log.d("print", "print: " + bt.getTop());


    }
}
