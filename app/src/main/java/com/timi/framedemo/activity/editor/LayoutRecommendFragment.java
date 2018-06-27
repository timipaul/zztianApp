package com.timi.framedemo.activity.editor;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.common.CommonView;
import com.timi.framedemo.base.BaseFragment;

/**
 * 编辑器 - 布局 - 推荐
 */
public class LayoutRecommendFragment extends BaseFragment implements View.OnClickListener{

    private RadioGroup mRadioGroup;
    private LinearLayout mLayout_image_materials;
    private RelativeLayout editor_content;
    private int[] layout;


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

        String[] data = {"二格","三格","四格"};
        Integer[] images = {R.drawable.cartoon_material_1,R.drawable.cartoon_material_2,R.drawable.cartoon_material_3,R.drawable.cartoon_material_4};
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
            imageView.setImageResource(images[Utility.getRandom(4)]);
            mLayout_image_materials.addView(imageView);
            imageView.setOnClickListener(this);
            imageView.setTag(i);
        }
    }

    @Override
    public void onClick(View v) {
        //在父类activity中添加布局视图
        editor_content =(RelativeLayout)getActivity().findViewById(R.id.editor_content);
        editor_content.removeAllViews();
        //View view = View.inflate(mContext, layout[(int) v.getTag()],null);
        View view = LayoutInflater.from(mContext).inflate(layout[(int) v.getTag()], null);
        
        editor_content.addView(view);
    }
}
