package com.timi.framedemo.activity.editor;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.timi.framedemo.R;
import com.timi.framedemo.ShareDataApplication;
import com.timi.framedemo.Utils.SharedPreferencesUtils;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.common.CommonView;
import com.timi.framedemo.base.BaseFragment;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 编辑器 - 漫画 - 场景 - 建筑
 */
public class CartoonSceneArchitectureFragment extends BaseFragment implements View.OnClickListener{

    private RadioGroup mRadioGroup;
    private LinearLayout mLayout_image_materials;
    private RelativeLayout editor_view;
    private int[] layout;
    Integer[] images = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    /** 图片的移动坐标 */
    private int[] site;

    //主界面顶部视图高度
    private int topHeith;
    //主界面左边视图宽度
    private int leftWidth;

    //需要移动的位置
    private int imageX = 0;
    private int imageY = 0;

    //图片宽高
    private Map<Integer,int[]> imageMap;
    /**当前操作图片的宽高*/
    private int[] image_w_h;
    /** 当前操作视图的宽高 */
    private int[] view_w_h;

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


        //获取当前编辑的视图ID
        int presentViewId = (int) SharedPreferencesUtils.getParam(mContext,"presentViewId",0);
        //在父类activity中添加布局视图
        editor_view = (RelativeLayout) getActivity().findViewById(presentViewId);

        imageMap = new HashMap<>();

        image_w_h = new int[2];
        view_w_h = new int[2];
        return view;
    }



    @Override
    protected void initData() {

        //加载编辑器共享数据
        ShareDataApplication sd = (ShareDataApplication) getContext().getApplicationContext();
        if(sd.getDataList() != null){
            mMap = sd.getDataList();
        }

        //读取图片移动要减去的空间
        topHeith = (int)SharedPreferencesUtils.getParam(mContext,"topViewHeight",0);
        leftWidth = (int)SharedPreferencesUtils.getParam(mContext,"leftViewWidth",0);

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


    @Override
    public void onClick(View v) {
        //在父类activity中添加布局视图
        dateId = Utility.getSecondTimestampTwo(new Date());

        final ImageView imageView = new ImageView(mContext);
        imageView.setId(dateId);
        imageView.setImageResource(images[(int) v.getTag()]);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.post(new Runnable(){
            @Override
            public void run() {
                int[] wAndh = new int[2];
                wAndh[0] = imageView.getMeasuredWidth();
                wAndh[1] = imageView.getMeasuredHeight();
                //记录图片的宽高
                imageMap.put(dateId,wAndh);
            }
        });
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
                        editor_view.removeView(v);
                        mMap.remove(v.getId());
                        //清除最后点击视图
                        SharedPreferencesUtils.setParam(mContext,"finallyView",0);
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
            editor_view.addView(imageView);
            mMap.put(imageView.getId(),null);
            ((ShareDataApplication)getContext().getApplicationContext()).setDataList(mMap);
        }

    }

    //OnTouch监听器
    private class PicOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event){
            //判断是在当前视图
            if(isUiVisible){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:  //起始位置
                        //获取当前图片的宽高
                        image_w_h = imageMap.get(v.getId());
                        view_w_h[0] = editor_view.getWidth();
                        view_w_h[1] = editor_view.getHeight();
                        //当前图片的坐标/距上距左的数据
                        site = new int[2];
                        break;
                    case MotionEvent.ACTION_MOVE:   //实时位置
                        imageX = (int)event.getRawX() - v.getWidth() / 2 - leftWidth;
                        imageY = (int)event.getRawY() - v.getHeight() - topHeith;
                        moveViewWithFinger(v,imageX,imageY,image_w_h);
                        site[0] = imageX;
                        site[1] = imageY;
                        mMap.put(v.getId(),site);
                        break;
                    case MotionEvent.ACTION_UP:     //结束位置
                        ((ShareDataApplication)getContext().getApplicationContext()).setDataList(mMap);
                        SharedPreferencesUtils.setParam(mContext,"finallyView",v.getId());
                        break;
                }
            }
            return false;
        }
    }

    //手势移动图片位置
    private void moveViewWithFinger(View view, int rawX, int rawY,int[] image_w_h) {

        //防止图片移出视图外
        rawX = rawX >= 0 ? rawX : 0;
        rawY = rawY >= 0 ? rawY : 0;

        rawX = image_w_h[0] + rawX < view_w_h[0] ? rawX : view_w_h[0] - image_w_h[0];
        rawY = image_w_h[1] + rawY < view_w_h[1] ? rawY : view_w_h[1] - image_w_h[1];

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //0.05解决一个像素差bug
        //params.leftMargin =  rawX - (int) (rawX*0.05);
        //params.topMargin =  rawY + (int) (rawY*0.05);
        params.leftMargin =  rawX;
        params.topMargin =  rawY;
        view.setLayoutParams(params);
    }


    @Override
    public void onHiddenChanged(boolean hidden){
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

            //获取当前编辑的视图ID
            int presentViewId = (int) SharedPreferencesUtils.getParam(mContext,"presentViewId",0);
            //在父类activity中添加布局视图
            editor_view = (RelativeLayout) getActivity().findViewById(presentViewId);
        }
    }
}
