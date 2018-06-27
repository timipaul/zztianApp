package com.timi.framedemo.activity.common;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.timi.framedemo.R;

/**
 * 通用视图
 */
public class CommonView {

    private Context mContext;

    public CommonView(Context context){
        this.mContext = context;
    }

    //创建单选按钮
    public RadioButton createRadioButton(String text){
        RadioButton radioButton = new RadioButton(mContext);
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 10, 3);
        radioButton.setLayoutParams(layoutParams);
        radioButton.setText(text);
        radioButton.setTextSize(12);
        radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setPadding(10, 10, 10, 10);
        radioButton.setTextColor(mContext.getResources().getColor(R.color.white));
        radioButton.setBackgroundResource(R.drawable.compile_writing_button_bg_selector);
        return radioButton;
    }

    //创建图片
    public ImageView createImageView(){
        ImageView iv = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(3,0,3,0);
        iv.setLayoutParams(params);
        return iv;
    }
}
