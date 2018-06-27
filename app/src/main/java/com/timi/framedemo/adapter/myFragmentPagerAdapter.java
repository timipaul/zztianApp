package com.timi.framedemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.timi.framedemo.base.BaseFragment;

import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class myFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmetnmanager;  //创建FragmentManager
    private List<BaseFragment> listfragment; //创建一个List<Fragment>

    public myFragmentPagerAdapter(FragmentManager fm,List<BaseFragment> list) {
        super(fm);
        this.fragmetnmanager=fm;
        this.listfragment=list;
    }

    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position); //返回第几个fragment
    }

    @Override
    public int getCount() {
        return listfragment.size(); //总共有多少个fragment
    }
}
