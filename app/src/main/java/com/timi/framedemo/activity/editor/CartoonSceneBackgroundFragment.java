package com.timi.framedemo.activity.editor;

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
 * 编辑器 - 漫画 - 场景 - 背景
 */
public class CartoonSceneBackgroundFragment extends BaseFragment implements View.OnClickListener{

    private RadioGroup mRadioGroup;
    private LinearLayout mLayout_image_materials;
    private RelativeLayout editor_content;
    private int[] layout;
    private int viewId;
    Integer[] images = {R.drawable.cartoon_material_1,R.drawable.cartoon_material_2,R.drawable.cartoon_material_3,R.drawable.cartoon_material_4};
    private LinkedHashMap<Integer,int[]> mMap = new LinkedHashMap<>();
    private Integer[] viewData = new Integer[1];
    private boolean isUiVisible = true;//该fragment是否选中

    long mLastTime=0;
    long mCurTime=0;

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

        String[] data = {"背景1","背景2","背景3"};

        CommonView cv = new CommonView(mContext);
        for (int i = 0; i < data.length; i++) {
            RadioButton button =  cv.createRadioButton(data[i]);
            mRadioGroup.addView(button);
            if(i == 0){
                //设置默认选中
                mRadioGroup.check(button.getId());
            }
        }
        layout =new int[]{R.layout.editor_layout_2,R.layout.editor_layout_3,R.layout.editor_layout_4_1,R.layout.editor_layout_4_2};
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

        viewId = Utility.getSecondTimestampTwo(new Date());

        //在父类activity中添加布局视图
        editor_content =(RelativeLayout)getActivity().findViewById(R.id.editor_content);
        //这里有个bug  覆盖的没移除
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(images[(int) v.getTag()]);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setId(viewId);
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
                        viewData[0] = null;
                    }
                }
            }
        });

        editor_content.addView(imageView,0);
        mMap.put(imageView.getId(),new int[2]);
        //移除上一个视图 只保留当前一个
        if(viewData[0] == null){
            viewData[0] = imageView.getId();
        }else{
            ImageView iv = (ImageView) getActivity().findViewById(viewData[0]);
            editor_content.removeView(iv);
            viewData[0] = imageView.getId();
            if(iv != null){
                mMap.remove(iv.getId());
            }
        }
        System.out.println("背景中的数据：" + mMap.toString());
        ((ShareDataApplication)getContext().getApplicationContext()).setDataList(mMap);
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
            }

        }
    }
}
