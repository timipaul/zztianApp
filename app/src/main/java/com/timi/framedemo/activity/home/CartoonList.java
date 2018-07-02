package com.timi.framedemo.activity.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.activity.read.ReadDetails;
import com.timi.framedemo.adapter.ProductionAdapter;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.bean.Cartoon;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 众创空间 -- 阅读 -- 漫画列表
 */
public class CartoonList extends BaseFragment {


    private GridView mGridView;
    private Handler handler=null;
    private List<Cartoon> datas;


    @Override
    protected View initView() {
        final View view = View.inflate(mContext, R.layout.fragment_common_grid_view, null);
        mGridView = (GridView) view.findViewById(R.id.layout_content);

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
                        .add("cartoonType","1")
                        .build();
                try {
                    String url = "/cartoon/cartoonlist";

                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    datas = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Cartoon car = new Cartoon();
                        JSONObject json = jsonArray.getJSONObject(i);
                        car.setId(json.getInt("id"));
                        car.setBookName(json.getString("bookName"));
                        car.setBookType(json.getString("bookType"));
                        car.setCover(json.getString("cover"));
                        datas.add(car);
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
            ProductionAdapter adapter = new ProductionAdapter(mContext,datas);
            mGridView.setAdapter(adapter);
            adapter.setOnItemClickListener(new ProductionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int id) {
                    Intent intent = new Intent(mContext, ReadDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id+"");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

    };
}