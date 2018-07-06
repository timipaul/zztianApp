package com.timi.framedemo.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.find.IssueParticularsActivity;
import com.timi.framedemo.adapter.FindWorldAdapter;
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
 *  发现  -- 世界
 */
public class FindWorldFragment extends BaseFragment {

    private ListView mListView;
    private List<Topic> mListTopic;
    private Handler handler=null;


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.fragment_common_frame,null);

        mListView = (ListView) view.findViewById(R.id.listview);

        handler=new Handler();
        return view;
    }

    @Override
    protected void initData() {

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

                    Toast.makeText(mContext, "点赞", Toast.LENGTH_SHORT).show();
                }
            });

            mListView.setOnItemClickListener(new clickRead());


        }
    };

    //适配器点击
    private class clickRead implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
            //跳转到帖子详情页
            Intent intent = new Intent(mContext,IssueParticularsActivity.class);

            startActivity(intent);
        }
    }
}
