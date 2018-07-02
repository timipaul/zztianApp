package com.timi.framedemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.MyGridView;
import com.timi.framedemo.activity.common.MoreReadDataActivity;
import com.timi.framedemo.activity.home.ReadClassifyActivity;
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
    众创空间 -- 阅读
 */
public class HomeReadFragment extends BaseFragment implements View.OnClickListener{

    private Button mButton_create;
    private Button mButton_ranking;
    private Button mButton_classify;
    private Button mButton_joint;
    private TextView mTextMore;
    private List<Cartoon> datas;
    private MyGridView mGridView;
    private Handler handler;

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.home_read,null);
        mGridView = (MyGridView) view.findViewById(R.id.layout_content);

        mButton_create = (Button) view.findViewById(R.id.read_create_but);
        mButton_ranking = (Button) view.findViewById(R.id.read_ranking_but);
        mButton_classify = (Button) view.findViewById(R.id.read_classify_but);
        mButton_joint = (Button) view.findViewById(R.id.read_joint_but);
        mTextMore = (TextView) view.findViewById(R.id.read_more_but);

        mButton_create.setOnClickListener(this);
        mButton_ranking.setOnClickListener(this);
        mButton_classify.setOnClickListener(this);
        mButton_joint.setOnClickListener(this);
        mTextMore.setOnClickListener(this);

        handler = new Handler();

        return view;
    }

    @Override
    public void onClick(View v) {
        Class clazz = null;
        Intent intent = null;
        Bundle data = new Bundle();
        switch (v.getId()){
            case R.id.read_create_but:      //众创
                clazz = MoreReadDataActivity.class;
                data.putInt("code",1);
                data.putString("name","众创");
                break;
            case R.id.read_joint_but:    //接龙
                clazz = MoreReadDataActivity.class;
                data.putInt("code",2);
                data.putString("name","接龙");
                break;
            case R.id.read_ranking_but:     //排行
                clazz = MoreReadDataActivity.class;
                data.putInt("code",3);
                data.putString("name","排行");
                break;
            case R.id.read_more_but:        //更多
                clazz = MoreReadDataActivity.class;
                data.putInt("code",4);
                data.putString("name","推荐更多");
                break;
            case R.id.read_classify_but:    //分类
                clazz = ReadClassifyActivity.class;
                break;
        }

        if(clazz != null){
            intent = new Intent(mContext, clazz);
            intent.putExtra("data",data);
            startActivity(intent);
        }

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

    Runnable runnableUi = new Runnable() {
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
