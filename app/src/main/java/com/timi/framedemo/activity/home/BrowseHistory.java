package com.timi.framedemo.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.fragment.PreviewCommunityFragment;
import com.timi.framedemo.fragment.PreviewFindFragment;
import com.timi.framedemo.fragment.PreviewStoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 浏览历史
 */
public class BrowseHistory extends FragmentActivity implements View.OnClickListener{

    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;
    private TextView mReturn;
    private TextView mEmpty;

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
        setContentView(R.layout.home_browse_history);

        initView();

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    private void initView() {

        mRg_main = (RadioGroup) findViewById(R.id.rb_browse_history);
        mReturn = (TextView) findViewById(R.id.browse_history_return);
        mEmpty = (TextView) findViewById(R.id.browse_history_empty);

        mReturn.setOnClickListener(this);
        mEmpty.setOnClickListener(this);


        //设置状态栏文字颜色及图标为深色
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //修改顶部背景
        //StatusBarUtil.statusBarTintColor(this,getResources().getColor(R.color.white));

    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new PreviewStoryFragment());//漫画Fragment
        mBaseFragment.add(new PreviewCommunityFragment());//剧本Fragment
        mBaseFragment.add(new PreviewFindFragment());//发现Fragment

    }

    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_history_story);

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_history_story://漫画
                    position = 0;
                    break;
                case R.id.rb_history_community://剧本
                    position = 1;
                    break;
                case R.id.rb_history_find:      //发现
                    position = 2;
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
                    ft.add(R.id.fl_history_content,to).commit();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browse_history_return:    //调用返回
                onBackPressed();
                break;
            case R.id.browse_history_empty:     //调用清空
                Toast.makeText(this,"功能完善中...",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
