package com.timi.framedemo.activity.find;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.adapter.FindDetailCommentAdapter;
import com.timi.framedemo.adapter.FindWorldAdapter;
import com.timi.framedemo.bean.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发现 世界 帖子详情页
 */
public class IssueParticularsActivity extends AppCompatActivity implements View.OnClickListener{

    private ActionBar mBar;
    private ListView head_layout;
    private List<Topic> list;
    private ArrayList<Object> commentList;
    private GridView mGridView;
    private ListView mComment_list;
    private Button mReply;  //回复
    private Button mShare;  //分享


    int[] imageIds = new int[]
            {
                    R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,
                    R.drawable.store_4,R.drawable.store_5,R.drawable.store_6,
                    R.drawable.store_2,R.drawable.store_6
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        setContentView(R.layout.find_issue_particulars);
        head_layout = (ListView) findViewById(R.id.head_data);
        mGridView = (GridView) findViewById(R.id.member_logo);
        mComment_list = (ListView) findViewById(R.id.comment_list);
        mReply = (Button) findViewById(R.id.reply);
        mShare = (Button) findViewById(R.id.share);


        mReply.setOnClickListener(this);
        mShare.setOnClickListener(this);

        mBar = getSupportActionBar();
        mBar.setTitle("帖子详情");
        mBar.setDisplayShowHomeEnabled(true);
        mBar.setHomeButtonEnabled(true);
        mBar.setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        list.add(new Topic());
        FindWorldAdapter adapter = new FindWorldAdapter(this,list);
        head_layout.setAdapter(adapter);



        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imageIds.length; i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("image", imageIds[i]);
            listItems.add(listItem);


        }

        //点赞人
        SimpleAdapter likeAdapter = new SimpleAdapter(this,
                listItems,
                R.layout.circle_home_page_image_item,
                new String[]{"image"},
                new int[]{R.id.circle_image});
        mGridView.setAdapter(likeAdapter);

        commentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            commentList.add(i);
        }
        //评论
        FindDetailCommentAdapter commentAdapter = new FindDetailCommentAdapter(this,commentList,1);
        mComment_list.setAdapter(commentAdapter);
        Utility.setListViewHeightBasedOnChildren(mComment_list);
        commentAdapter.setOnItemClickListener(new FindDetailCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Integer parameter) {
                //跳转到回贴详情  //返回时需要刷新
                Intent intent = new Intent(IssueParticularsActivity.this, ReplyDetails.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:            //回复
                replyComment();
                break;
            case R.id.share:        //分享
                popupShowShare();
                break;

        }
    }

    //回复评论
    private void replyComment() {
        //检查是否输入内容
        //隐藏输入法
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //提交回复
    }

    //底部弹出分享信息  预计可修改放在一个类里直接调用  传context
    private void popupShowShare() {

        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.fragment_common_share, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        contentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消
                bottomDialog.dismiss();
            }
        });

    }


}
