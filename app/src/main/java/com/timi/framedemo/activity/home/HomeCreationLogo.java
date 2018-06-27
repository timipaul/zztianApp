package com.timi.framedemo.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.timi.framedemo.R;
import com.timi.framedemo.activity.read.ReadDetails;
import com.timi.framedemo.base.BaseFragment;

/**
 * 众创空间 -- 创建 -- logo
 */
public class HomeCreationLogo  extends BaseFragment{
    private ImageView homeImg = null;

    private static final String TAG = HomeCreationLogo.class.getCanonicalName();

    private RollPagerView mRollViewPager;

    @Override
    protected View initView() {
        Log.e(TAG,"logo图片初始化了");

        final View view = View.inflate(mContext, R.layout.home_creation_logo,null);

        mRollViewPager = (RollPagerView)view.findViewById(R.id.roll_create_view_pager);

        /*homeImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("name","logo");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(2000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        TestNormalAdapter logingAdapter = new TestNormalAdapter();
        mRollViewPager.setAdapter(logingAdapter);

        mRollViewPager.setHintView(new ColorPointHintView(mContext, Color.YELLOW,Color.WHITE));

        return view;
    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.community_course,
                R.drawable.home_logo,
                R.drawable.store_material_logo,
                R.drawable.store_story_logo,
        };


        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
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
            return imgs.length;
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
