package com.timi.framedemo.activity.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.adapter.FindDetailCommentAdapter;

import java.util.ArrayList;

/**
 * 帖子详情 - 回帖详情
 */
public class ReplyDetails extends AppCompatActivity{

    private ListView mListView;
    private ActionBar mBar;
    private ArrayList<Object> commentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_reply_details);

        initView();

        initData();

    }



    //添加视图
    private void initView() {
        mListView = (ListView) findViewById(R.id.comment_list);

        mBar = getSupportActionBar();
        mBar.setTitle("回帖详情");
        mBar.setDisplayShowHomeEnabled(true);
        mBar.setHomeButtonEnabled(true);
        mBar.setDisplayHomeAsUpEnabled(true);

    }

    //获取数据
    private void initData() {

        commentList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            commentList.add(i);
        }
        FindDetailCommentAdapter commentAdapter = new FindDetailCommentAdapter(this,commentList,0);
        mListView.setAdapter(commentAdapter);
        Utility.setListViewHeightBasedOnChildren(mListView);
    }
}
