package com.timi.framedemo.activity.read;

import android.view.View;
import android.widget.ListView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.adapter.ReadCommentAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 阅读 - 评论
 */
public class ReadCommentFragment extends BaseFragment {

    private ListView listView;
    private List datas;

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.fragment_common_frame,null);

        listView = (ListView) view.findViewById(R.id.listview);
        initData();
        final ReadCommentAdapter adapter = new ReadCommentAdapter(mContext, datas);
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listView);

        return view;
    }

    @Override
    protected void initData() {
        datas=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            datas.add(i);
        }
    }
}
