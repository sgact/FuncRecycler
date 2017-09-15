package com.sg.funcrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sg.funcrecyclerlib.R;

/**
 * Created by SG on 2017/9/15.
 */

public class SampleAdaper extends RecyclerView.Adapter<SampleAdaper.SViewHolder> {


    @Override
    public SViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SViewHolder holder, int position) {
        holder.tv.setText("This is " + position);
    }

    @Override
    public int getItemCount() {
        return 40;
    }

    class SViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public SViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
