package com.timi.framedemo.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.timi.framedemo.R;
import com.timi.framedemo.Utils.GetHttpImg;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.activity.read.ReadDetails;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.bean.Cartoon;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 阅读logo展示
 */
public class HomeReadLogo extends BaseFragment{

    private RollPagerView mRollViewPager;
    private List<Cartoon> mList;
    private Handler handler=null;

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.home_read_logo,null);
        mRollViewPager = (RollPagerView)view.findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(2000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);

        handler=new Handler();
        getImageData();

        return view;
    }

    public void getImageData() {
        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder().build();
                try {
                    mList = new ArrayList<Cartoon>();
                    String url = "/homepage/banner";
                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    System.out.println("logo展示图片");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Cartoon car = new Cartoon();
                        car.setId(json.getInt("id"));
                        car.setCover(json.getString("cover"));
                        car.setType(json.getString("bookType"));
                        mList.add(car);
                    }

                    handler.post(runnableUi);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    Runnable   runnableUi=new  Runnable(){
        @Override
        public void run() {
            //添加视图
            //设置适配器
            TestNormalAdapter logingAdapter = new TestNormalAdapter();
            mRollViewPager.setAdapter(logingAdapter);
            mRollViewPager.setHintView(new ColorPointHintView(mContext, Color.YELLOW,Color.WHITE));
        }

    };

    private class TestNormalAdapter extends StaticPagerAdapter {

        @Override
        public View getView(ViewGroup container, final int position) {

            ImageView view = new ImageView(container.getContext());

            GetHttpImg.setUserImg(view, mList.get(position).getCover());
            view.setId(mList.get(position).getId());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到阅读界面去
                    LoginPageJump(v.getId());
                }
            });

            return view;
        }


        @Override
        public int getCount() {
            return mList.size();
        }

    }

    public void LoginPageJump(int i){
        Intent intent = new Intent(mContext,ReadDetails.class);
        //设置参数
        Bundle bundle=new Bundle();
        bundle.putString("id",""+i);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
