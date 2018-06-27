package com.timi.framedemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.find.CircleHomePageActivity;
import com.timi.framedemo.activity.find.FindCircleMoreActivity;
import com.timi.framedemo.adapter.FindCircleAdapter;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.bean.Topic;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 *  发现 -- 圈子
 */
public class FindCircleFragment extends BaseFragment implements View.OnClickListener{


    private ArrayList<Topic> list;
    private ListView mListView;
    private TextView mMore_but;
    private Handler handler=null;


    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.find_circle, null);
        mListView = (ListView) view.findViewById(R.id.content_list);
        mMore_but = (TextView) view.findViewById(R.id.create_more_but);

        mMore_but.setOnClickListener(this);

        handler = new Handler();

        //添加视图数据
        getContentDate();

        return view;
    }


    public void getContentDate() {

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
                    System.out.println("圈子信息：" + jsonArray);
                    list = new ArrayList();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Topic top = new Topic();
                        top.setId(json.getInt("id"));
                        top.setBoardId(json.getString("boardId"));
                        top.setClickcount(json.getInt("clickcount"));
                        top.setContent(json.getString("content"));
                        top.setHeadimg(json.getString("headimg"));
                        top.setNickName(json.getString("nickName"));
                        top.setPraisecount(json.getInt("praisecount"));
                        top.setPublishtime(new Date(json.getLong("publishtime")));
                        top.setReplycount(json.getInt("replycount"));
                        top.setTitle(json.getString("title"));
                        top.setUserId(json.getString("userId"));

                        try {
                            top.setContentImg(json.getString("contentImg"));
                        }catch (Exception e){
                            top.setContentImg(null);
                        }
                        list.add(top);
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
            FindCircleAdapter adapter = new FindCircleAdapter(mContext, list);
            mListView.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(mListView);

            adapter.setOnItemClickListener(new FindCircleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v) {

                    System.out.println("id:" + v.getId());
                    System.out.println("name:" + v.getTag());

                    //跳转到某圈子首页
                    Intent intent = new Intent(mContext,CircleHomePageActivity.class);
                    Bundle data = new Bundle();
                    data.putInt("id",v.getId());
                    data.putString("name", (String) v.getTag());
                    intent.putExtra("data",data);
                    startActivity(intent);
                }
            });
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_more_but:      //更多跳转
                Intent intent = new Intent(mContext,FindCircleMoreActivity.class);
                startActivity(intent);
                break;
        
            default:
                break;
        }
    }
}