package com.timi.framedemo.activity.editor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑器 - 效果分类
 */
public class CartoonSpecialFragment extends BaseFragment {

    private RadioGroup mRadioGroup;
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
        final View view = View.inflate(mContext, R.layout.compile_cartoon_effect,null);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_compile_cartoon_effect);

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
        return view;
    }


    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new EffectBackgroundFragment());//背景图Fragment
        mBaseFragment.add(new EffectSceneFragment());//场景Fragment
        mBaseFragment.add(new EffectRoleFragment());//角色Fragment
        mBaseFragment.add(new EffectCurrentFragment());//当前框Fragment
        mBaseFragment.add(new EffectAllFrameFragment());//所有框Fragment

    }


    private void setListener() {


        mRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListenerRead());
        //设置默认选中阅读
        mRadioGroup.check(R.id.effect_background);
    }

    private class MyOnCheckedChangeListenerRead implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.effect_background: //背景图
                    position = 0;
                    break;
                case R.id.effect_image:     //场景
                    position = 1;
                    break;
                case R.id.effect_role:      //角色
                    position = 2;
                    break;
                case R.id.effect_current:  //当前框
                    position = 3;
                    break;
                case R.id.effect_all:       //所有框
                    position = 4;
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
                    ft.add(R.id.fcompile_cartoon_effect,to).commit();
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
}
