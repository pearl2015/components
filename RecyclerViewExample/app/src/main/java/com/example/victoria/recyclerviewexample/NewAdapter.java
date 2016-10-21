package com.example.victoria.recyclerviewexample;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victoria on 10/18/16.
 */

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {

    private List<String> mDatas = new ArrayList<>();
    private Context context;

    public NewAdapter(List<String> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewViewHolder holder = new NewViewHolder(LayoutInflater.from(context)
                                .inflate(R.layout.itemlayout,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NewViewHolder holder, int position) {
            holder.topic_tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class NewViewHolder extends RecyclerView.ViewHolder{

        private TextView topic_tv;
        private TextView time_tv;
        private Button del_btn;

        public NewViewHolder(View itemView) {
            super(itemView);
    //       ButterKnife.bind(this, itemView);
             topic_tv = (TextView)itemView.findViewById(R.id.topic_tv);
            time_tv = (TextView)itemView.findViewById(R.id.time_tv);
            del_btn = (Button)itemView.findViewById(R.id.del_btn);

        }
    }
}
