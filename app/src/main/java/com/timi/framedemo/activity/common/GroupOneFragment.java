package com.timi.framedemo.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.find.IssueParticularsActivity;
import com.timi.framedemo.adapter.FindCircleMyAdapter;
import com.timi.framedemo.adapter.FindWorldAdapter;
import com.timi.framedemo.adapter.UserSynopsisAdapter;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.bean.Topic;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 *  菜单一
 */
public class GroupOneFragment extends BaseFragment {

    private ListView mListView;
    private List<Integer> list;
    private List<Topic> mListTopic;
    private Handler handler=null;


    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        handler=new Handler();
        mListView = (ListView) view.findViewById(R.id.listview);
        Intent intent = getActivity().getIntent();
        Bundle data = intent.getBundleExtra("data");
        int code = data.getInt("code");

        switch (code) {
            case 1:
                //我的关注 - 作者
                myAttentionAuthor();
                break;
            case 2:
                //我的帖子 - 世界
                myWorldContent();
                break;
            case 3:
                //我的圈子 - 已浏览
                myAlreadyBrowse();
                break;
        }


        return view;
    }




    /** 我的关注 - 作者*/
    private void myAttentionAuthor() {

        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        //list数据适配器
        UserSynopsisAdapter adapter = new UserSynopsisAdapter(mContext, list);
        mListView.setAdapter(adapter);

    }

    /** 我的帖子 已浏览  */
    private void myAlreadyBrowse() {
        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        //list数据适配器
        FindCircleMyAdapter adapter = new FindCircleMyAdapter(mContext, list);
        mListView.setAdapter(adapter);
    }

    private void myWorldContent() {
        //获取数据
        /*ArrayList<Object> lists = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            lists.add(i);
        }
        //设置适配器
        FindItemAdapter adapter = new FindItemAdapter(mContext,lists);
        //添加适配器
        mListView.setAdapter(adapter);*/

        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("pageNum", "1")
                        .add("pageSize","10")
                        .build();
                try {

                    String url = "/circle/topic";

                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);

                    mListTopic = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {

                        JSONObject json = jsonArray.getJSONObject(i);
                        Topic top = new Topic();

                        top.setId(json.getInt("id"));
                        top.setClickcount(json.getInt("clickcount"));
                        top.setContent(json.getString("content"));
                        top.setHeadimg(json.getString("headimg"));
                        top.setNickName(json.getString("nickName"));
                        top.setPraisecount(json.getInt("praisecount"));
                        top.setPublishtime(new Date(json.getLong("publishtime")));
                        top.setReplycount(json.getInt("replycount"));
                        top.setTitle(json.getString("title"));
                        top.setUserId(json.getString("userId"));

                        mListTopic.add(top);
                    }
                    handler.post(runnableUi);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableUi = new  Runnable(){
        @Override
        public void run() {
            //添加视图
            FindWorldAdapter adapter = new FindWorldAdapter(mContext,mListTopic);
            mListView.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(mListView);

            adapter.setOnItemClickListener(new FindWorldAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Integer parameter) {
                    //跳转到帖子详情页
                    Intent intent = new Intent(mContext,IssueParticularsActivity.class);
                    startActivity(intent);
                }
            });
        }
    };
}
