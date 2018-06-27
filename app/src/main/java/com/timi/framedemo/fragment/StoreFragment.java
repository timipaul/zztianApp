package com.timi.framedemo.fragment;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * 大分类 编辑器
 */
public class StoreFragment extends BaseFragment {
    private static final String TAG = StoreFragment.class.getSimpleName();

    private RadioGroup mRgMenu;
    private RadioButton mRbWriting;
    private RadioButton mRbStory;
    private RadioButton mRbMaterial;
    private ImageView mReturn;

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

        final View view = View.inflate(mContext, R.layout.store_top_menu,null);

        mRgMenu = (RadioGroup)view.findViewById(R.id.rg_store_menu);
        mRbWriting = (RadioButton) view.findViewById(R.id.rb_store_writing);
        mRbStory = (RadioButton) view.findViewById(R.id.rb_store_story);
        mRbMaterial = (RadioButton) view.findViewById(R.id.rb_store_material);


        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();

        SharedPreferences share_get=null;
        share_get=mContext.getSharedPreferences("data", MODE_PRIVATE);
        //根据键获取数据，第二个参数为默认值，若没有指定的键，则返回默认值
        SharedPreferences.Editor editor = share_get.edit();
        editor.remove("loginUser");
        editor.commit();

        return view;
    }


    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        //mBaseFragment.add(new StoreWritingFragment());//写作Fragment
        mBaseFragment.add(new StoreStoryFragment());//漫画Fragment
        mBaseFragment.add(new StoreMaterialFragment());//素材Fragment

    }


    private void setListener() {


        mRgMenu.setOnCheckedChangeListener(new MyOnCheckedChangeListenerRead());
        //设置默认选中阅读
        mRgMenu.check(R.id.rb_store_writing);
    }

    private class MyOnCheckedChangeListenerRead implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            //每次改变调用,改变字体大小
            setMenuByChange(checkedId);
            switch (checkedId){
                case R.id.rb_store_writing: //写作
                    position = 0;
                    break;
                case R.id.rb_store_story://漫画
                    position = 1;
                    break;
                case R.id.rb_store_material://素材
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
                    ft.add(R.id.fStore_menu,to).commit();
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
        mRbWriting.setTextSize(15);
        mRbStory.setTextSize(15);
        mRbMaterial.setTextSize(15);
        if(checkedId == R.id.rb_store_writing){
            mRbWriting.setTextSize(18);
        }else if(checkedId == R.id.rb_store_story){
            mRbStory.setTextSize(18);
        }else if(checkedId == R.id.rb_store_material){
            mRbMaterial.setTextSize(18);
        }
    }
}
