package com.timi.framedemo.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.timi.framedemo.R;
import com.timi.framedemo.adapter.FindCircleMyAdapter;
import com.timi.framedemo.adapter.FindItemAdapter;
import com.timi.framedemo.adapter.UserSynopsisAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单二
 */
public class GroupTwoFragment extends BaseFragment {

    private ListView mListView;
    private List<Integer> list;

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView = (ListView) view.findViewById(R.id.listview);

        Intent intent = getActivity().getIntent();
        Bundle data = intent.getBundleExtra("data");
        int code = data.getInt("code");


        switch (code) {
            case 1:
                //我的关注 - 用户
                myUser();
                break;
            case 2:
                //我的帖子 - 圈子
                myCircleContent();
                break;
            case 3:
                //我的圈子 - 已加入
                myAlreadyJoin();

                break;
        }
        return view;
    }


    /** 我的关注 - 用户 */
    private void myUser() {

        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        //list数据适配器
        UserSynopsisAdapter adapter = new UserSynopsisAdapter(mContext, list);
        mListView.setAdapter(adapter);

    }

    /** 我的圈子 - 已加入 **/
    private void myAlreadyJoin() {
        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        //list数据适配器
        FindCircleMyAdapter adapter = new FindCircleMyAdapter(mContext, list);
        mListView.setAdapter(adapter);
    }

    /** 我的帖子 圈子 */
    private void myCircleContent() {
        //获取数据
        ArrayList<Object> lists = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            lists.add(i);
        }
        //设置适配器
        FindItemAdapter adapter = new FindItemAdapter(mContext,lists);
        //添加适配器
        mListView.setAdapter(adapter);

    }
}
