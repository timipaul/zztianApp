package com.timi.framedemo.activity.read;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.BackHandlerHelper;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.adapter.ReadBodyTextAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 漫画阅读 -- 文字
 */
public class ReadContentText extends BaseFragment{

    private List datas;
    private ListView listView;

    @Override
    protected View initView() {
        final View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        listView = (ListView) view.findViewById(R.id.listview);
        initData();
        final ReadBodyTextAdapter adapter = new ReadBodyTextAdapter(mContext, datas);
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listView);


        adapter.setOnItemDeleteClickListener(new ReadBodyTextAdapter.onItemListener() {
            @Override
            public void onAddEditClick(int i) {
                Intent intent = new Intent(mContext,AddEditText.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }

    @Override
    protected void initData() {
        datas=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            datas.add(i);
        }
    }





}
