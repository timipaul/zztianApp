package com.timi.framedemo.activity.editor;

import android.view.View;
import android.widget.TextView;

import com.timi.framedemo.base.BaseFragment;

/**
 * 编辑器 - 漫画 - 场景 - 物件
 */
public class CartoonSceneArticleFragment extends BaseFragment {
    @Override
    protected View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("物件.........");
        return textView;
    }
}
