package com.timi.framedemo.activity.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.fragment.UpdateCartoonFragment;
import com.timi.framedemo.fragment.UpdateCommunityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *   1 更新故事
 *   2 参与作品
 *   3 我的收藏
 */
public class CreateUpdateCartoon extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;
    private TextView mReturn;
    private TextView mEmpty;
    private ActionBar mBar;
    private TextView mTitle;

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
        setContentView(R.layout.create_update_cartoon);



        initView();

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();

    }

    protected void setTitleContent(String title){

        mBar = getSupportActionBar();
        mBar.setTitle(title);
        mBar.setHomeButtonEnabled(true);
        mBar.setDisplayHomeAsUpEnabled(true);
        //显示应用程序图标
        mBar.setDisplayShowHomeEnabled(true);
            //mBar.setBackgroundDrawable(getResources().getDrawable(R.color.white));

    }



    //返回图标调用
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        System.out.println("返回的id：" + item.getItemId());
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    //调用菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }



    protected void initView() {

        mRg_main = (RadioGroup) findViewById(R.id.rb_browse_update);
        mReturn = (TextView) findViewById(R.id.create_update_return);
        mEmpty = (TextView) findViewById(R.id.create_update_empty);
        mTitle = (TextView) findViewById(R.id.title);

        mReturn.setOnClickListener(this);
        mEmpty.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        //1表示更新
        int code = data.getInt("code");
        String title = data.getString("name");

        System.out.println("获取的参数" + code + " 标题 " + title);

        setTitleContent(title);
        mTitle.setText(title);


    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new UpdateCartoonFragment());//漫画Fragment
        mBaseFragment.add(new UpdateCommunityFragment());//剧本Fragment
    }

    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_browse_update_cartoon);

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_browse_update_cartoon://漫画
                    position = 0;
                    break;
                case R.id.rb_browse_update_story://剧本
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
                    ft.add(R.id.fl_browse_update_content,to).commit();
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
            case R.id.create_update_return:    //调用返回
                onBackPressed();
                break;
            case R.id.browse_history_empty:     //调用清空
                Toast.makeText(this,"功能完善中...",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
