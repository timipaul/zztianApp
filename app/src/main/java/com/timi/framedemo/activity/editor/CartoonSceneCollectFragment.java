package com.timi.framedemo.activity.editor;

import android.view.View;
import android.widget.TextView;

import com.timi.framedemo.base.BaseFragment;

/**
 * 编辑器 - 漫画 - 场景 - 收藏夹
 */
public class CartoonSceneCollectFragment extends BaseFragment {
    @Override
    protected View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("收藏夹.........");
        return textView;
    }
}
