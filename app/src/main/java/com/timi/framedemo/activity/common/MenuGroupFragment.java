package com.timi.framedemo.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *  通用菜单
 *  code
 *      1 我的关注
 *      2 我的帖子
 *      3 我的关注
 *
 */
public class MenuGroupFragment extends FragmentActivity {

    private RadioGroup mRg_main;
    private RadioButton mRadioOne;
    private RadioButton mRadioTwo;
    private Integer mCode = null;


    private List<BaseFragment> mBaseFragment;

    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_common_menu_group);

        initView();

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();


    }

    protected void initView() {

        //获取请求来的信息  code name等

        mRg_main = (RadioGroup) findViewById(R.id.rg_menu);
        mRadioOne = (RadioButton) findViewById(R.id.rb_one);
        mRadioTwo = (RadioButton) findViewById(R.id.rb_two);

        /*actionBar = getSupportActionBar();
        //显示应用程序图标
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setLogo(R.drawable.ic_return);
        actionBar.setDisplayUseLogoEnabled(true);
        //将应用程序图标设置为可点击的按钮，并在图标上添加向左箭头
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue));*/

        //获取传入信息
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        int code = data.getInt("code");
        String title = data.getString("title");
        String radioOne = data.getString("radioOne");
        String radioTwo = data.getString("radioTwo");
        System.out.println("跳转来的标题: " + title );
        mRadioOne.setText(radioOne);
        mRadioTwo.setText(radioTwo);
        mCode = code;


    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new GroupOneFragment());     //菜单一
        mBaseFragment.add(new GroupTwoFragment());      //菜单二
    }

    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_one);

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_one:       //菜单一
                    position = 0;
                    break;
                case R.id.rb_two:       //菜单二
                    position = 1;
                    break;
                default:
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFrament(mContent,to);
        }
    }

    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from,Fragment to) {
        if(from != to){
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //添加to
                if(to != null){
                    ft.add(R.id.fl_menu_content,to).commit();
                }
            }else{
                //to已经被添加
                // from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }

    }

    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }
}
