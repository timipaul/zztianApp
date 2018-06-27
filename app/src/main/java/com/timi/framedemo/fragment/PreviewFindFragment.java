package com.timi.framedemo.fragment;

import android.view.View;
import android.widget.TextView;

import com.timi.framedemo.base.BaseFragment;

/**
 *浏览历史 - 发现
 */
public class PreviewFindFragment extends BaseFragment {

    @Override
    protected View initView() {
        TextView tv = new TextView(mContext);
        tv.setText("发现");
        return tv;
    }
}
