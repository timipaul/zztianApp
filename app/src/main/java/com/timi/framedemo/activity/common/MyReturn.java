package com.timi.framedemo.activity.common;

import android.view.View;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;

/**
 * 共同的返回
 */
public class MyReturn extends BaseFragment{
    @Override
    protected View initView() {
        final View view = View.inflate(mContext, R.layout.fragment_common_return,null);

        return view;
    }
}
