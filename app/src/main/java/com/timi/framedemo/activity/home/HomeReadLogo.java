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

    private ImageView homeImg = null;

    private static final String TAG = HomeReadLogo.class.getCanonicalName();

    private RollPagerView mRollViewPager;

    private List<String> image;
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
        image = new ArrayList<String>();
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
                    String url = "/homepage/banner";
                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        List<String> list = new ArrayList<String>();
                        list.add(jsonObject2.getString("bookType"));
                        image.add(jsonObject2.getString("cover"));
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

            GetHttpImg.setUserImg(view, image.get(position));

            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到阅读界面去
                    LoginPageJump(position);
                }
            });

            return view;
        }


        @Override
        public int getCount() {
            return image.size();
        }

    }

    public void LoginPageJump(int i){
        Intent intent = new Intent(mContext,ReadDetails.class);
        //设置参数
        Bundle bundle=new Bundle();
        bundle.putString("name",""+i);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
