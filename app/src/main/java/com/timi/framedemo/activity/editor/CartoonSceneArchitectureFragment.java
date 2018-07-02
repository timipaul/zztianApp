package com.timi.framedemo.activity.editor;

import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.timi.framedemo.R;
import com.timi.framedemo.ShareDataApplication;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.common.CommonView;
import com.timi.framedemo.base.BaseFragment;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 编辑器 - 漫画 - 场景 - 建筑
 */
public class CartoonSceneArchitectureFragment extends BaseFragment implements View.OnClickListener{

    private RadioGroup mRadioGroup;
    private LinearLayout mLayout_image_materials;
    private RelativeLayout editor_content;
    private int[] layout;
    Integer[] images = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    private int[] site;

    //主界面顶部视图高度
    private int topHeith;
    //主界面左边视图宽度
    private int leftWidth;

    //需要移动的位置
    private int imageX = 0;
    private int imageY = 0;

    long mLastTime=0;
    long mCurTime=0;

    private int dateId;
    private boolean isUiVisible = true;//该fragment是否选中

    private LinkedHashMap<Integer,int[]> mMap = new LinkedHashMap<>();

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.compile_cartoon_template,null);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_compile_cartoon_template);
        mRadioGroup.removeAllViews();
        mLayout_image_materials = (LinearLayout) view.findViewById(R.id.ll_compile_cartoon_template_image);





        return view;
    }



    @Override
    protected void initData() {

        //加载编辑器共享数据
        ShareDataApplication sd = (ShareDataApplication) getContext().getApplicationContext();
        if(sd.getDataList() != null){
            mMap = sd.getDataList();
            System.out.println("建筑中的数据map：" + mMap.toString());
        }

        //读取图片移动要减去的空间
        SharedPreferences share_get=null;
        share_get=mContext.getSharedPreferences("data", mContext.MODE_PRIVATE);
        //根据键获取数据，第二个参数为默认值，若没有指定的键，则返回默认值
        topHeith = share_get.getInt("topViewHeight", 0);
        leftWidth = share_get.getInt("leftViewWidth", 0);

        String[] data = {"建筑1","建筑2","建筑3"};
        CommonView cv = new CommonView(mContext);
        for (int i = 0; i < data.length; i++) {
            RadioButton button =  cv.createRadioButton(data[i]);
            mRadioGroup.addView(button);
            if(i == 0){
                //设置默认选中
                mRadioGroup.check(button.getId());
            }
        }
        layout = new int[]{R.layout.editor_layout_2,R.layout.editor_layout_3,R.layout.editor_layout_4_1,R.layout.editor_layout_4_2};
        for (int i = 0; i < layout.length; i++) {
            ImageView imageView = cv.createImageView();
            imageView.setImageResource(images[i]);
            mLayout_image_materials.addView(imageView);
            imageView.setOnClickListener(this);
            imageView.setTag(i);

        }
    }



    //手势移动图片位置
    private void moveViewWithFinger(View view, int rawX, int rawY) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //0.05解决一个像素差bug
        params.leftMargin =  rawX - (int) (rawX*0.05);
        params.topMargin =  rawY + (int) (rawY*0.05);
        view.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        //在父类activity中添加布局视图
        dateId = Utility.getSecondTimestampTwo(new Date());

        editor_content =(RelativeLayout)getActivity().findViewById(R.id.editor_content);
        ImageView imageView = new ImageView(mContext);
        imageView.setId(dateId);
        imageView.setImageResource(images[(int) v.getTag()]);


        //双击移除图片
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUiVisible){
                    mLastTime=mCurTime;
                    mCurTime= System.currentTimeMillis();
                    if(mCurTime-mLastTime<300){//双击事件
                        mCurTime =0;
                        mLastTime = 0;
                        editor_content.removeView(v);
                        mMap.remove(v.getId());

                        SharedPreferences share=null;
                        //得到SharePreferences对象，第一个参数：指定文件名，第二个参数：操作模式
                        share= mContext.getSharedPreferences("data", mContext.MODE_PRIVATE);
                        //得到SharedPreferen.Edit对象
                        SharedPreferences.Editor edit=share.edit();
                        //用edit存储数据
                        edit.putInt("finallyView", 0);
                        //提交数据，存储完成
                        edit.commit();
                    }
                }

            }
        });
        imageView.setOnTouchListener(new PicOnTouchListener());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);

        mLastTime=mCurTime;
        mCurTime= System.currentTimeMillis();
        if(mCurTime-mLastTime<300){
            mCurTime =0;
            mLastTime = 0;
            v.setOnClickListener(null);
            try {
                Thread.sleep(1000);
                v.setOnClickListener(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            editor_content.addView(imageView);
            mMap.put(imageView.getId(),null);
        }

    }

    //OnTouch监听器
    private class PicOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event){
            site = new int[2];
            //判断是在当前视图
            if(isUiVisible){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:  //起始位置
                        break;
                    case MotionEvent.ACTION_MOVE:   //实时位置
                        imageX = (int)event.getRawX() - v.getWidth() / 2 - leftWidth;
                        imageY = (int)event.getRawY() - v.getHeight() - topHeith;
                        moveViewWithFinger(v,imageX,imageY);
                        site[0] = imageX;
                        site[1] = imageY;
                        mMap.put(v.getId(),site);
                        break;
                    case MotionEvent.ACTION_UP:     //结束位置
                        ((ShareDataApplication)getContext().getApplicationContext()).setDataList(mMap);
                        SharedPreferences share=null;
                        //得到SharePreferences对象，第一个参数：指定文件名，第二个参数：操作模式
                        share= mContext.getSharedPreferences("data", mContext.MODE_PRIVATE);
                        //得到SharedPreferen.Edit对象
                        SharedPreferences.Editor edit=share.edit();
                        //用edit存储数据
                        edit.putInt("finallyView", v.getId());
                        //提交数据，存储完成
                        edit.commit();
                        break;
                }
            }
            return false;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //简化三目远算符
        //isUiVisible = !hidden;
        if(hidden){
            isUiVisible = false;
        }else{
            isUiVisible = true;
            //加载编辑器共享数据
            ShareDataApplication sd = (ShareDataApplication) getContext().getApplicationContext();
            if(sd.getDataList() != null){
                mMap = sd.getDataList();
                System.out.println("建筑中的数据map：" + mMap.toString());
            }
        }
    }
}
