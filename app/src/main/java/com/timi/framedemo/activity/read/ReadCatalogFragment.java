package com.timi.framedemo.activity.read;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.adapter.CatalogListAdapter;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.bean.CartoonChapter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 阅读 - 目录
 */
public class ReadCatalogFragment extends BaseFragment{

    private ListView listView;
    private List<CartoonChapter> datas;
    private Handler handler = null;
    private String cartoonId;


    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        handler = new Handler();
        listView = (ListView) view.findViewById(R.id.listview);

        //新页面接收数据
        Bundle bundle = getActivity().getIntent().getExtras();
        //接收name值
        cartoonId = bundle.getString("id");
        Log.i("目录获取到的请求id值为",cartoonId + "");

        return view;
    }

    @Override
    protected void initData() {
        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("cartoonId", cartoonId)//漫画ID
                        .build();
                try {
                    String url = "/cartoon/getChapterlist";
                    datas=new ArrayList<>();
                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    System.out.println("目录数据：" + jsonArray);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        CartoonChapter car = new CartoonChapter();
                        car.setId(json.getInt("id"));
                        car.setChapterCover(json.getString("chapterCover"));
                        car.setChapterId(json.getString("chapterId"));
                        car.setCreatedate(new Date(Long.valueOf(json.getString("createdate"))));
                        car.setPraiseNum(json.getInt("praiseNum"));
                        car.setChapterName(json.getString("chapterName"));

                        datas.add(car);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                handler.post(runnableUi);
            }
        }.start();

    }

    Runnable runnableUi = new Runnable(){
        @Override
        public void run() {
            //更新视图
            CatalogListAdapter adapter = new CatalogListAdapter(mContext, datas);
            listView.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(listView);

            adapter.setOnItemClickListener(new CatalogListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    Toast.makeText(mContext, "Click item" + view.getTag(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };



}
