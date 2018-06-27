package com.timi.framedemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.activity.read.ReadDetails;
import com.timi.framedemo.base.BaseFragment;

import java.util.Random;

import cn.wolfspider.autowraplinelayout.AutoWrapLineLayout;

/**
 * 众创 - 创作 - 更新 - 剧本
 */
public class UpdateCommunityFragment extends BaseFragment {

    private String[] name = {"妖神记", "女神纪元", "白鹤三绝", "斗破苍穹", "斗罗大陆", "盘龙", "冰火魔厨"};
    private String[] style = {"玄幻","古风","奇幻","战斗","武侠"};
    private Integer[] image = {R.drawable.home_catroon_1, R.drawable.home_catroon_2, R.drawable.home_catroon_3};
    private LinearLayout ll_layout;
    private ImageView mImageView;
    private TextView mTextStyle;
    private AutoWrapLineLayout mAutoWrapLineLayout;
    private Random r = new Random();

    @Override
    protected View initView() {
        final View view = View.inflate(mContext, R.layout.fragment_common_autowrap_layout, null);

        mAutoWrapLineLayout = (AutoWrapLineLayout) view.findViewById(R.id.awl_layout);
        mAutoWrapLineLayout.setFillMode(AutoWrapLineLayout.MODE_WRAP_CONTENT);
        mAutoWrapLineLayout.removeAllViews();
        //添加视图
        addView(10);
        return view;
    }


    private void addView(int number) {

        for (int i = 0; i < number; i++) {

            //创建视图
            ll_layout = new LinearLayout(mContext);
            mImageView = new ImageView(mContext);
            TextView tvName = new TextView(mContext);
            LinearLayout ll_style = new LinearLayout(mContext);
            ll_style.setOrientation(LinearLayout.HORIZONTAL);
            //添加类型
            for(int y = 0;y < 2; y++){
                mTextStyle = new TextView(mContext);
                mTextStyle.setText(style[r.nextInt(5)]);
                setTextStyle(mTextStyle);
                ll_style.addView(mTextStyle);
            }
            //设置样式
            setLlayoutStyle(ll_layout);
            setImageStyle(mImageView);
            setTextNameStyle(tvName);

            //添加视图
            mAutoWrapLineLayout.addView(ll_layout);
            ll_layout.addView(mImageView);
            ll_layout.addView(tvName);
            ll_layout.addView(ll_style);
            //添加数据

            mImageView.setImageResource(image[r.nextInt(3)]);
            tvName.setText(name[r.nextInt(7)]);




            //事件
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ReadDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name","logo");
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
        }


    }

    //RelativeLayout 样式
    public void setLlayoutStyle(LinearLayout layout) {
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(5,0,0,0);
        layout.setLayoutParams(param);
    }

    //图片样式
    public void setImageStyle(ImageView iv) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LayoutParams param = new LayoutParams(width / 3 - 10, LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(param);

    }

    //漫画名
    public void setTextNameStyle(TextView tv) {
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.setMargins(5,0,5,0);
        tv.setTextSize(18);
        tv.setLayoutParams(param);
    }

    public void setTextStyle(TextView tv) {
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.setMargins(5,0,5,0);
        tv.setLayoutParams(param);
    }

}