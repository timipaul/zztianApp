package com.timi.framedemo.debris;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.adapter.UserAttentionAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;

/**
 * 用户推荐碎片
 */
public class User_attention extends BaseFragment implements View.OnClickListener{

    private GridView mLayout_user;          //视图
    private TextView mUpdate_content;       //换一批




    private Integer[] data = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    private String[] name = {"用户1","用户2","用户3","用户4"};
    private String[] fans = {"粉丝1","粉丝2","粉丝3","粉丝4"};
    private String[] attention = {"5","8","23","33"};
    private ArrayList<Object> list;

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_common_user_attention,null);

        mLayout_user = (GridView) view.findViewById(R.id.l_user_content);
        mUpdate_content = (TextView) view.findViewById(R.id.update_content);

        mUpdate_content.setOnClickListener(this);

        //加载推荐用户
        updateUserDate();

        return view;
    }

    //加载推荐用户
    private void updateUserDate() {

        /*List<Map<String, Object>> listItems =
                new ArrayList<Map<String, Object>>();
        for (int i = 0; i < data.length; i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("image", data[i]);
            listItem.put("name",name[i]);
            listItem.put("fans",fans[i]);
            listItem.put("attention",attention[i]);
            listItems.add(listItem);
        }*/

        // 为GridView设置Adapter
        list = new ArrayList<Object>();
        list.add(data);
        list.add(name);
        list.add(fans);
        list.add(attention);
        UserAttentionAdapter adapter = new UserAttentionAdapter(mContext,list);
        mLayout_user.setAdapter(adapter);

        adapter.setOnItemClickListener(new UserAttentionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int tag) {
                System.out.println("关注被点击了 " + tag);
            }
        });



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_content:      //更新
                updateUserDate();
                break;
        
            default:
                break;
        }
    }
}
