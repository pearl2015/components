package com.example.victoria.recyclerviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecycleView;
    private List<String> mDatas;
    private NewAdapter mAdapter;

    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView = (RecyclerView)findViewById(R.id.list_rv);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        initData();
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewAdapter(mDatas,MainActivity.this);
        mRecycleView.setAdapter(mAdapter);
    }

    public void initData(){
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'e'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu,menu);
        return true;
    }
}
