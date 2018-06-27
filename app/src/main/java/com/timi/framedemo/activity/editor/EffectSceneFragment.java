package com.timi.framedemo.activity.editor;

import android.view.View;
import android.widget.TextView;

import com.timi.framedemo.base.BaseFragment;

/**
 *编辑器 - 效果 - 场景
 */
public class EffectSceneFragment extends BaseFragment {

    @Override
    protected View initView() {
        TextView tv = new TextView(mContext);
        tv.setText("背景图");
        return tv;
    }
}
