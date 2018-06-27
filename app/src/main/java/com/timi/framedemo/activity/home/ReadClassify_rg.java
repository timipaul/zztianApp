package com.timi.framedemo.activity.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.FlowRadioGroup;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 众创空间 - 阅读 - 分类 - RadioGroup
 */
public class ReadClassify_rg extends BaseFragment{

    private List<String> listDatas;
    private RadioButton rb_buttion;
    private FlowRadioGroup rg_group;
    private String[] datas = {"全部","独创","众创","接龙","恐怖","校园","搞笑","男生","女生","玄幻","奇幻","武侠","战斗","古风"};


    @Override
    protected void initData() {
        listDatas = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            listDatas.add(datas[i]);
        }

    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.home_read_classify_rg,null);
        rg_group = (FlowRadioGroup) view.findViewById(R.id.classify_rg);
        rg_group.removeAllViews();

        addRBView();
        return view;
    }

    private void addRBView() {

        for (int i = 0; i < datas.length; i++) {
            //设置好的button样式
            RadioButton rdbtn = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.tabmenu_radiobutton, null);
            rdbtn.setId(i);
            rdbtn.setText(datas[i]);
            rg_group.addView(rdbtn);
        }

        rg_group.check(0);


        
    }



}
