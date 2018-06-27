package com.timi.framedemo.activity.read;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.timi.framedemo.R;
import com.timi.framedemo.adapter.myFragmentPagerAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 漫画 阅读 侧滑
 */
public class ReadContentControl extends FragmentActivity {
    private ViewPager viewpager;

    private List<BaseFragment> listfragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化View
        initView();

        FragmentManager fm=getSupportFragmentManager();

        myFragmentPagerAdapter mfpa=new myFragmentPagerAdapter(fm, listfragment);

        viewpager.setAdapter(mfpa);

        viewpager.setCurrentItem(0); //设置当前页是第一页

        /*viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });*/

    }

    private void initView() {
        setContentView(R.layout.fragment_common_pager_adapter);
        viewpager = (ViewPager) findViewById(R.id.read_content_vp); //获取ViewPager
        listfragment = new ArrayList<BaseFragment>(); //new一个List<Fragment>
        listfragment.add(new ReadContentText());
        listfragment.add(new CartoonContent());
    }
}
