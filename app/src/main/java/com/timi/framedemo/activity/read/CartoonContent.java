package com.timi.framedemo.activity.read;

import android.view.View;
import android.widget.ListView;

import com.timi.framedemo.R;
import com.timi.framedemo.adapter.ReadBodyCartoonAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 漫画阅读界面
 */
public class CartoonContent extends BaseFragment {

    private ListView listView;
    private List datas;

    @Override
    protected View initView() {
        final View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        listView = (ListView) view.findViewById(R.id.listview);

        initData();

        final ReadBodyCartoonAdapter adapter = new ReadBodyCartoonAdapter(mContext, datas);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    protected void initData() {
        datas=new ArrayList<>();
        for (int i = 0; i < 10  ; i++) {
            datas.add(i);
        }
    }
}
