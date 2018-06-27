package com.timi.framedemo.activity.editor;

import android.view.View;
import android.widget.TextView;

import com.timi.framedemo.base.BaseFragment;

/**
 * 编辑器 - 布局 - 收藏夹
 */
public class LayoutCollecFragment extends BaseFragment {
    @Override
    protected View initView() {
        TextView tv = new TextView(mContext);
        tv.setText("收藏夹");
        return tv;
    }
}
