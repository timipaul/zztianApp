package com.timi.framedemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.activity.common.CartoonSeek;
import com.timi.framedemo.activity.home.BrowseHistory;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;



/**
 * 大分类  众创空间  //BaseFragment
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = HomeFragment.class.getSimpleName();

    private RadioGroup mRgMenu;

    private TextView mRecord;
    private TextView mSeek;
    private RadioButton mCollect;
    private RadioButton mRanking;
    private RadioButton mRecommend;

    private List<BaseFragment> mBaseFragment;



    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new HomeReadFragment());//阅读Fragment
        mBaseFragment.add(new HomeCreationFragment());//创作Fragment
        mBaseFragment.add(new HomeCollectFragment());//收藏Fragment
    }


    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.home_top_menu,null);
        mRgMenu = (RadioGroup)view.findViewById(R.id.rg_second_menu);
        mSeek = (TextView) view.findViewById(R.id.home_menu_seek);
        mRecord = (TextView) view.findViewById(R.id.home_menu_record);
        mRanking = (RadioButton) view.findViewById(R.id.rb_three_ranking);
        mRecommend = (RadioButton) view.findViewById(R.id.rb_three_recommend);
        mCollect = (RadioButton) view.findViewById(R.id.read_collect);

        mSeek.setOnClickListener(this);
        mRecord.setOnClickListener(this);

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
        //请求数据
        OkhttpPost();


        /*点击其它位置隐藏键盘*/
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(getActivity().getCurrentFocus()!=null && getActivity().getCurrentFocus().getWindowToken()!=null){
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.home_menu_seek: //跳转到搜索页面
                intent = new Intent(mContext, CartoonSeek.class);
                break;
            case R.id.home_menu_record://历史
                intent = new Intent(mContext,BrowseHistory.class);
                break;

        }
        startActivity(intent);
    }


    //请求后台数据
    private void OkhttpPost() {

    }


    private void setListener() {

        mRgMenu.setOnCheckedChangeListener(new MyOnCheckedChangeListenerRead());
        //设置默认选中阅读
        mRgMenu.check(R.id.rb_three_ranking);
    }


    private class MyOnCheckedChangeListenerRead implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            //每次改变调用,改变字体大小
            setMenuByChange(checkedId);
            switch (checkedId){
                case R.id.rb_three_ranking://阅读
                    position = 0;
                    break;
                case R.id.rb_three_recommend://创作
                    position = 1;
                    break;
                case R.id.read_collect:   //收藏
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
                    ft.add(R.id.fhome_menu,to).commit();
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
        mRanking.setTextSize(15);
        mRecommend.setTextSize(15);
        mCollect.setTextSize(15);
        if(checkedId == R.id.rb_three_ranking){
            mRanking.setTextSize(18);
        }else if(checkedId == R.id.rb_three_recommend){
            mRecommend.setTextSize(18);
        }else if(checkedId == R.id.read_collect){
            mCollect.setTextSize(18);
        }
    }

}
