package com.timi.framedemo.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.timi.framedemo.R;
import com.timi.framedemo.adapter.FindCircleMoreAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现 - 圈子 - 更多 - 全部
 */
public class FindCircleMoreAllFragment extends BaseFragment {

    private RadioGroup mGroup_filtrate;
    private ListView mListView;
    private List<Integer> list;

    String[] data = {"推荐","爱好","漫画","情感","游戏","校园","男生","女生","其它"};

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.find_circle_more_all,null);
        mGroup_filtrate = (RadioGroup) view.findViewById(R.id.rg_filtrate);
        mListView = (ListView) view.findViewById(R.id.more_list);

        return view;
    }

    @Override
    protected void initData() {
        for (String aData : data) {
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(aData);
            RadioGroup.LayoutParams rl = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            rl.setMargins(20, 0, 20, 0);
            radioButton.setButtonDrawable(null);

            radioButton.setTextColor(getResources().getColorStateList(R.color.button_txt_color));
            radioButton.setBackgroundResource(R.drawable.button_bg);
            radioButton.setLayoutParams(rl);
            radioButton.setTextSize(16);
            radioButton.setPadding(20,0,20,0);
            mGroup_filtrate.addView(radioButton);
       }


        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        //list数据适配器
        FindCircleMoreAdapter adapter = new FindCircleMoreAdapter(mContext, list);
        mListView.setAdapter(adapter);

    }
}
