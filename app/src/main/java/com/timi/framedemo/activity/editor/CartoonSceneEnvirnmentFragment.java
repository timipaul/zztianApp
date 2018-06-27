package com.timi.framedemo.activity.editor;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.common.CommonView;
import com.timi.framedemo.base.BaseFragment;

/**
 * 编辑器 - 漫画 - 场景 - 环境
 */
public class CartoonSceneEnvirnmentFragment extends BaseFragment {

    private RadioGroup mRadioGroup;
    private LinearLayout mLayout_image_materials;
    private boolean isUiVisible = true;//该fragment是否选中
    private Integer[] images = {R.drawable.cartoon_material_1,R.drawable.cartoon_material_2,R.drawable.cartoon_material_3,R.drawable.cartoon_material_4};
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

        String[] data = {"草地","水面","沙漠"};
        CommonView cv = new CommonView(mContext);
        for (int i = 0; i < data.length; i++) {
            RadioButton button = cv.createRadioButton(data[i]);
            mRadioGroup.addView(button);
            if(i == 0){
                //设置默认选中
                mRadioGroup.check(button.getId());
            }
        }

        for (int i = 0; i < 10; i++) {
            ImageView imageView = cv.createImageView();
            imageView.setImageResource(images[Utility.getRandom(4)]);
            mLayout_image_materials.addView(imageView);
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //简化三目远算符
        isUiVisible = !hidden;
        /*if(hidden){
            isUiVisible = false;
        }else{
            isUiVisible = true;
        }*/
    }
}
