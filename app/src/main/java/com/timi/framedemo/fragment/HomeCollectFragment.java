package com.timi.framedemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.SharedPreferencesUtils;
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
 * 众创 -- 收藏
 */
public class HomeCollectFragment extends BaseFragment {

    private GridView mGridView;
    private List<Collect> mList;
    private Handler handler=null;
    private int userId;

    @Override
    protected View initView() {
        final View view = View.inflate(mContext, R.layout.fragment_common_grid_view, null);

        mGridView = (GridView) view.findViewById(R.id.layout_content);
        handler = new Handler();

        userId = (Integer)SharedPreferencesUtils.getParam(mContext,"userId",0);

        return view;
    }

    @Override
    protected void initData() {
        if(userId != 0){
            addView();
        }
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
