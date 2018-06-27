package com.timi.framedemo.activity.compile;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;


/**
    空间商城 --- 故事 --- 热门推荐 下 ImageView
 */
public class StoryListImage extends BaseFragment {

    private  LinearLayout linearLayout = null;

    private DisplayMetrics dm = null;


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected View initView() {

       /* View view = View.inflate(mContext, R.layout.,null);

        linearLayout = (LinearLayout) view.findViewById(R.id.store_story_logos);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        addGroupImage(12);*/


        return null;
    }

    //size:代码中获取到的图片数量
    private void addGroupImage(int size){
        LinearLayout ll_horizontal = null;
        LinearLayout ll_text = null;
        linearLayout.removeAllViews();
        for (int i = 0; i < size; i++) {
            if (i%3 == 0) {
                ll_horizontal = new LinearLayout(mContext);
                ll_horizontal.setOrientation(LinearLayout.HORIZONTAL);
                ll_text = new LinearLayout(mContext);
                ll_text.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(ll_horizontal);
                linearLayout.addView(ll_text);
            }

            //添加图片
            ImageView imageView = new ImageView(mContext);
            setImgLayoutParams(imageView);
            imageView.setImageResource(R.drawable.store_story_2); //图片资源
            ll_horizontal.addView(imageView);

            //添加文字
            TextView textView = new TextView(mContext);
            setTextViewLayoutParams(textView);
            textView.setText("C币3000");
            ll_text.addView(textView);

        }
    }


    /**
     * 设置imageview的尺寸
     */
    private void setImgLayoutParams(ImageView img) {
        // LayoutParams lp = (LayoutParams) img.getLayoutParams();
        LayoutParams lp = new LayoutParams(0, 0);
        lp.width = (int)dm.widthPixels / 3;
        lp.height = lp.width;
        lp.rightMargin = 15;
        lp.bottomMargin = 5;
        img.setLayoutParams(lp);
        img.setScaleType(ScaleType.CENTER_CROP);
    }

    private void setTextViewLayoutParams(TextView tv){
        LayoutParams lp = new LayoutParams((int)dm.widthPixels / 3, LayoutParams.WRAP_CONTENT);

        tv.setGravity(Gravity.CENTER);//居中
        tv.setTextSize(20);
        tv.setHeight(100);
        lp.bottomMargin = 30;
        tv.setLayoutParams(lp);

    }
}
