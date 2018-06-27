package com.timi.framedemo.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.activity.read.ReadDetails;
import com.timi.framedemo.adapter.CoverShowAdapter;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.bean.Collect;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 众创 - 创作 - 更新 - 漫画
 */
public class UpdateCartoonFragment extends BaseFragment{

    private GridView mGridView;
    private List<Collect> mList;
    private Handler handler=null;
    private int userId;

    @Override
    protected View initView() {
        final View view = View.inflate(mContext, R.layout.fragment_common_grid_view, null);

        Intent intent = getActivity().getIntent();
        Bundle data = intent.getBundleExtra("data");
        int code = data.getInt("code");
        String title = data.getString("name");
        handler=new Handler();

        System.out.println("漫画列表获取的参数" + code + " 标题 " + title);

        mGridView = (GridView) view.findViewById(R.id.layout_content);
        SharedPreferences share_get=null;
        share_get=mContext.getSharedPreferences("data", mContext.MODE_PRIVATE);
        //根据键获取数据，第二个参数为默认值，若没有指定的键，则返回默认值

        userId = share_get.getInt("userId",0);
        //添加视图
        addView();
        return view;
    }


    private void addView() {

        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("pageNum", "1")
                        .add("pageSize","10")
                        .add("userId", String.valueOf(userId))
                        .build();
                try {
                    String url = "/great/userCollect";

                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    mList = new ArrayList<>();
                    System.out.println("收藏数据：" + jsonArray);

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Collect collect = new Collect();
                        collect.setId(json.getInt("id"));
                        collect.setBookName(json.getString("bookName"));
                        collect.setCartoonId(json.getInt("cartoonId"));
                        collect.setChapterCover(json.getString("chapterCover"));
                        collect.setChapterName(json.getString("chapterName"));
                        collect.setChapterSection(json.getString("chapterSection"));
                        mList.add(collect);
                    }

                    handler.post(runnableUi);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableUi = new Runnable(){
        @Override
        public void run() {
            CoverShowAdapter adapter = new CoverShowAdapter(mContext,mList);
            mGridView.setAdapter(adapter);
            adapter.setOnItemClickListener(new CoverShowAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int tag) {
                    Intent intent = new Intent(mContext, ReadDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name","logo");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


        }

    };



}
