package com.timi.framedemo.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 重写  让视图在顶层的时候底层的视图不可点击
 */
public class CustomLinearLayout extends LinearLayout {
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
