package com.timi.framedemo.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.adapter.RecommendMoreAdapter;
import com.timi.framedemo.bean.Cartoon;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 *  更多推荐等
 */
public class MoreReadDataActivity extends AppCompatActivity implements View.OnClickListener{

    ActionBar actionBar;
    private ListView mListView;
    private List<Cartoon> datas;
    private Handler handler;
    private GridView mGridView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载视图
        initView();
        //添加数据
        initData();

    }

    private void initView() {
        setContentView(R.layout.fragment_common_frame);
        mGridView = (GridView) findViewById(R.id.layout_content);

        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        //1表示 众创 2表示接龙 3表示排行 4表示更多
        int code = data.getInt("code");
        String title = data.getString("name");


        actionBar = getSupportActionBar();
        //显示应用程序图标
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setLogo(R.drawable.ic_return);
        actionBar.setDisplayUseLogoEnabled(true);
        //将应用程序图标设置为可点击的按钮，并在图标上添加向左箭头
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue));

        datas = new ArrayList();
        handler = new Handler();
        mListView = (ListView) findViewById(R.id.listview);
    }

    private void initData() {
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

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Cartoon car = new Cartoon();
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
            //更新视图
            RecommendMoreAdapter adapter = new RecommendMoreAdapter(MoreReadDataActivity.this, datas);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new clickRead());
        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class clickRead implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
            Toast.makeText(MoreReadDataActivity.this, "Click item" + i, Toast.LENGTH_SHORT).show();
        }
    }
}
