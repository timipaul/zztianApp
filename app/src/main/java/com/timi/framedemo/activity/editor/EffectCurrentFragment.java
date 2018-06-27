package com.timi.framedemo.activity.editor;

import android.view.View;
import android.widget.TextView;

import com.timi.framedemo.base.BaseFragment;

/**
 * 编辑器 - 效果 - 当前框
 */
public class EffectCurrentFragment extends BaseFragment {

    @Override
    protected View initView() {
        TextView tv = new TextView(mContext);
        tv.setText("当前框");
        return tv;
    }
}
