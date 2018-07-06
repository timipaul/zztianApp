package com.timi.framedemo.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.timi.framedemo.R;
import com.timi.framedemo.activity.common.CartoonSeek;
import com.timi.framedemo.activity.home.BrowseHistory;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 大分类 发现
 */
public class FindFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = FindFragment.class.getSimpleName();

    private RadioGroup mRgMenu;
    private Button mRecord;
    private Button mSeek;
    private RadioButton mWorld;
    private RadioButton mCircle;
    private RadioButton mAttention;

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
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.community_top_menu,null);

        mRgMenu = (RadioGroup)view.findViewById(R.id.rg_find_menu);
        mSeek = (Button) view.findViewById(R.id.find_menu_seek);
        mRecord = (Button) view.findViewById(R.id.find_menu_record);
        mWorld = (RadioButton) view.findViewById(R.id.rb_find_world);
        mCircle = (RadioButton) view.findViewById(R.id.rb_find_circle);
        mAttention = (RadioButton) view.findViewById(R.id.rb_find_attention);
        mSeek.setOnClickListener(this);
        mRecord.setOnClickListener(this);

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();

        return view;
    }


    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new FindWorldFragment());//世界Fragment
        mBaseFragment.add(new FindCircleFragment());//圈子Fragment
        mBaseFragment.add(new FindAttentionFragment());//关注Fragment

    }

    private void setListener() {

        mRgMenu.setOnCheckedChangeListener(new MyOnCheckedChangeListenerRead());
        //设置默认选中世界
        mRgMenu.check(R.id.rb_find_world);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.find_menu_seek: //跳转到搜索页面
                intent = new Intent(mContext, CartoonSeek.class);
                break;
            case R.id.find_menu_record://历史
                intent = new Intent(mContext,BrowseHistory.class);
                break;

        }
        startActivity(intent);
    }

    private class MyOnCheckedChangeListenerRead implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            setMenuByChange(checkedId);
            switch (checkedId){
                case R.id.rb_find_world://世界
                    position = 0;
                    break;
                case R.id.rb_find_circle://圈子
                    position = 1;
                    break;
                case R.id.rb_find_attention://关注
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
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    /**
     *
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to 马上要切换到的Fragment，一会要显示
     */

    private void switchFrament(Fragment from,Fragment to) {

        if(from != to){
            mContent = to;
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
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
                    ft.add(R.id.f_find_content,to).commit();
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


    //每次改变调用,改变字体大小
    public void setMenuByChange(int checkedId) {
        mWorld.setTextSize(15);
        mCircle.setTextSize(15);
        mAttention.setTextSize(15);
        if(checkedId == R.id.rb_find_world){
            mWorld.setTextSize(18);
        }else if(checkedId == R.id.rb_find_circle){
            mCircle.setTextSize(18);
        }else if(checkedId == R.id.rb_find_attention){
            mAttention.setTextSize(18);
        }
    }


}
