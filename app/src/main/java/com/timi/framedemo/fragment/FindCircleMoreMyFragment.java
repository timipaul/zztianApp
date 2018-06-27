package com.timi.framedemo.fragment;

import android.view.View;
import android.widget.ListView;

import com.timi.framedemo.R;
import com.timi.framedemo.adapter.FindCircleMyAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现 - 圈子 - 更多 - 我的
 */
public class FindCircleMoreMyFragment extends BaseFragment {

    private ListView mListView;
    private List<Integer> list;
    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView = (ListView) view.findViewById(R.id.listview);

        return view;
    }

    @Override
    protected void initData() {

        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        //list数据适配器
        FindCircleMyAdapter adapter = new FindCircleMyAdapter(mContext, list);
        mListView.setAdapter(adapter);

    }
}
