package com.timi.framedemo.activity.find;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.adapter.CircleContentAdapter;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 *  圈子首页
 */
public class CircleHomePageActivity extends AppCompatActivity{

    ActionBar mActionBar;
    private GridView mGridView;
    private ListView mListView;
    private ArrayList<Object> list;
    private int mId;
    private Handler handler;

    int[] imageIds = new int[]{
            R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,
            R.drawable.store_4,R.drawable.store_5,R.drawable.store_6,
            R.drawable.store_2
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_home_page);

        initView();

        initData();

    }



    private void initView() {

        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        mId = data.getInt("id");
        String name = data.getString("name");

        handler = new Handler();
        mGridView = (GridView) findViewById(R.id.member_logo);
        mListView = (ListView) findViewById(R.id.content_list);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(name);
    }


    private void initData() {
        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("itemId", String.valueOf(mId))
                        .build();
                try {
                    String url = "/circle/comment";
                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    System.out.println("圈子评论：" + jsonArray);
                    list = new ArrayList();
                    /*for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Topic top = new Topic();
                        top.setId(json.getInt("id"));
                        top.setBoardId(json.getString("boardId"));
                        list.add(top);
                    }*/
                    handler.post(runnableUi);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        /*
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }*/


    }

    Runnable runnableUi = new  Runnable(){
        @Override
        public void run() {

            List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < imageIds.length; i++) {
                Map<String, Object> listItem = new HashMap<String, Object>();
                listItem.put("image", imageIds[i]);
                listItems.add(listItem);
            }

            //活跃成员
            SimpleAdapter adapter = new SimpleAdapter(CircleHomePageActivity.this,
                    listItems,
                    R.layout.circle_home_page_image_item,
                    new String[]{"image"},
                    new int[]{R.id.circle_image});
            mGridView.setAdapter(adapter);

            //内容list
            CircleContentAdapter contentAdapter = new CircleContentAdapter(CircleHomePageActivity.this,list);
            mListView.setAdapter(contentAdapter);
            Utility.setListViewHeightBasedOnChildren(mListView);
        }
    };
}
