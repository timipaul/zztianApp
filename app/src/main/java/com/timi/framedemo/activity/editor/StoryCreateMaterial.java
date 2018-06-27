package com.timi.framedemo.activity.editor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *  编辑器 - 漫画
 */
public class StoryCreateMaterial extends BaseFragment implements View.OnClickListener{

    private RadioGroup mRadioGroup;
    private RadioButton mRb_layout;
    private RadioButton mRb_scene;
    private RadioButton mRb_role;
    private RadioButton mRb_special;
    private RadioButton mRb_text;
    private FrameLayout mFl_layout;
    //用来判断菜单是否隐藏
    private boolean state = false;

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


        View view = View.inflate(mContext, R.layout.store_story_create_material,null);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.story_material_rg);
        mRb_layout = (RadioButton) view.findViewById(R.id.rb_material_layout);
        mRb_scene = (RadioButton) view.findViewById(R.id.rb_material_scene);
        mRb_role = (RadioButton) view.findViewById(R.id.rb_material_role);
        mRb_special = (RadioButton) view.findViewById(R.id.rb_material_specialEffects);
        mRb_text = (RadioButton) view.findViewById(R.id.rb_material_text);
        mFl_layout = (FrameLayout) view.findViewById(R.id.fmaterial_material_library);

        mRb_layout.setOnClickListener(this);
        mRb_scene.setOnClickListener(this);
        mRb_role.setOnClickListener(this);
        mRb_special.setOnClickListener(this);
        mRb_text.setOnClickListener(this);

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
        return view;
    }


    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new CartoonLayoutFragment());//布局选项Fragment
        mBaseFragment.add(new CartoonSceneFragment());//场景Fragment
        mBaseFragment.add(new CartoonRoleFragment());//角色Fragment
        mBaseFragment.add(new CartoonSpecialFragment());//效果Fragment
        mBaseFragment.add(new CartoonTextFragment());//文字Fragment

    }

    private void setListener() {


        mRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListenerRead());
        //设置默认选中阅读
        mRadioGroup.check(R.id.rb_material_layout);
    }

    @Override
    public void onClick(View v) {
        if(state){
            mFl_layout.setVisibility(View.GONE);
            state = false;
        }else{
            mFl_layout.setVisibility(View.VISIBLE);
            state = true;
        }
    }

    private class MyOnCheckedChangeListenerRead implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            //每次改变调用,改变字体大小
            setMenuByChange(checkedId);
            state = false;
            switch (checkedId){
                case R.id.rb_material_layout: //布局
                    position = 0;
                    break;
                case R.id.rb_material_scene://场景
                    position = 1;
                    break;
                case R.id.rb_material_role://角色
                    position = 2;
                    break;
                case R.id.rb_material_specialEffects://效果
                    position = 3;
                    break;
                case R.id.rb_material_text://文字
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
                    ft.add(R.id.fmaterial_material_library,to).commit();
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
        mRb_layout.setTextSize(16);
        mRb_scene.setTextSize(16);
        mRb_role.setTextSize(16);
        mRb_special.setTextSize(16);
        mRb_text.setTextSize(16);
        if(checkedId == R.id.rb_material_layout){
            mRb_layout.setTextSize(18);
        }else if(checkedId == R.id.rb_material_scene){
            mRb_scene.setTextSize(18);
        }else if(checkedId == R.id.rb_material_role){
            mRb_role.setTextSize(18);
        }else if(checkedId == R.id.rb_material_specialEffects){
            mRb_special.setTextSize(18);
        }else if(checkedId == R.id.rb_material_text){
            mRb_text.setTextSize(18);
        }
    }
}
