package com.timi.framedemo.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;

/**
    空间商城 -- 素材
 */
public class StoreMaterialFragment extends BaseFragment {

    private static final String TAG = StoreMaterialFragment.class.getSimpleName();

    private RadioGroup rg_filtrate = null;

    private RadioGroup rg_filtrate2 = null;

    private LinearLayout ll_material = null;

    private String[] datas = {"人物","场景","武器","动物"};

    private String[] datasF = {"男性","女性","其他"};

    private String[] name = {"PG-0001","PB-0001","PG-0002","PB-0002","PG-0003","PB-0003","PG-0004","PB-0004"};

    private String[] price = {"众创币7800","$63.0","众创币4800","$31.5","众创币1350","$4.8","周免","免费"};

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.store_material,null);

        rg_filtrate = (RadioGroup) view.findViewById(R.id.material_filtrate);

        rg_filtrate2 = (RadioGroup) view.findViewById(R.id.material_filtrate_2);

        ll_material = (LinearLayout) view.findViewById(R.id.store_material_sample);

        //添加筛选TextView
        addFiltrateTextView();
        //添加二级筛选
        addTwoFiltrateTextView();
        //添加筛选后的素材结果显示
        addMaterialLinearLayout();


        return view;
    }

    /**
     * 添加筛选TextView
     */
    public void addFiltrateTextView(){
        for (int i = 0; i < datas.length; i++) {
            RadioButton rb = new RadioButton(mContext);
            setButtonStyle(rb);
            rb.setText(datas[i]);
            rg_filtrate.addView(rb);
        }
    }

    /**
     * 添加二级筛选TextView
     */
    public void addTwoFiltrateTextView(){
        for (int i = 0; i < datasF.length; i++) {
            RadioButton rb = new RadioButton(mContext);

            setButtonStyle(rb);
            rb.setText(datasF[i]);
            rb.setId(i);
            rg_filtrate2.addView(rb);
        }
    }

    /**
     * 设置textView 样式
     */

    public void setButtonStyle(RadioButton rb){
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);


        params.rightMargin = 20;
        params.topMargin = 25;
        rb.setLayoutParams(params);
        rb.setTextSize(20);
        rb.setButtonDrawable(null);
        rb.setPadding(10,5,10,5);
        rb.setBackgroundResource(R.drawable.selector_category_bg);
        rb.setTextColor(getResources().getColorStateList(R.color.selector_category_text));


    }

    /**
     *  添加素材显示
     */
    public void addMaterialLinearLayout(){

        LinearLayout ll_horizontal = null;
        FrameLayout ll_frame;
        LayoutParams lp;
        ll_material.removeAllViews();


        //循环要显示的素材总数
        for (int i = 0; i < name.length; i++) {
            //行布局
            if(i % 2 == 0){
                ll_horizontal = new LinearLayout(mContext);
                lp = new LayoutParams(
                        LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT );
                //设置LinearLayout的上外边距
                lp.topMargin = 10;
                lp.bottomMargin = 10;

                ll_horizontal.setLayoutParams(lp);
                ll_horizontal.setOrientation(LinearLayout.HORIZONTAL);
                ll_material.addView(ll_horizontal);
            }
            ll_frame = new FrameLayout(mContext);
            FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

            fl.topMargin = 30;
            ll_frame.setLayoutParams(fl);
            ll_horizontal.addView(ll_frame);
            //ll_frame.setForegroundGravity(View.SCROLL_AXIS_HORIZONTAL);

            //创建视图
            ImageView iv = new ImageView(mContext);
            //设置样式
            setImageStyle(iv);
            //添加数据
            iv.setImageResource(R.drawable.store_material_1); //图片资源
            //添加视图
            ll_frame.addView(iv);


            TextView tv1 = new TextView(mContext);
            setTextViewNameStyle(tv1);
            tv1.setText(name[i]);
            ll_frame.addView(tv1);

            //tv1.setTextColor();
            TextView tv2 = new TextView(mContext);
            setTextViewPriceStyle(tv2);
            tv2.setText(price[i]);
            ll_frame.addView(tv2);

        }



    }

    /**
     * 设置素材图片样式
     */
    public void setImageStyle(ImageView iv){
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        fl.setMargins(15,10,10,10);
        iv.setLayoutParams(fl);

    }


    /**
     * 设置素材文字名字样式
     */
    public void setTextViewNameStyle(TextView tv) {

        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        fl.gravity = Gravity.BOTTOM | Gravity.CENTER;
        fl.bottomMargin = 10;
        tv.setPadding(5,2,5,2);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setBackgroundColor(Color.parseColor("#262020"));
        tv.setLayoutParams(fl);
    }


    /**
     * 设置素材文字价格
     */
    public void setTextViewPriceStyle(TextView tv) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        lp.bottomMargin = 70;
        tv.setTextColor(Color.parseColor("#ff8f33"));
        tv.setLayoutParams(lp);

    }






}
