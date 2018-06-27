package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;

import java.util.ArrayList;


/**
 * 用户信息适配器   调用数据接口时传入json数据
 */
public class UserAttentionAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Object> mList = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private Integer[] logins = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    private String[] name = {"用户1","用户2","用户3","用户4"};
    private String[] fans = {"粉丝1","粉丝2","粉丝3","粉丝4"};
    private String[] attention = {"5","8","23","33"};

    public UserAttentionAdapter(Context context, ArrayList<Object> list) {
        mContext = context;
        mList = list;
    }

    class ViewHolder {

         ImageView mUser_login;          //用户头像
         TextView mUser_name;            //用户名
         TextView mUser_fans;            //用户粉丝
         TextView mUser_attention;       //关注
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {

            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_common_user_attention_item, null);

            viewHolder.mUser_login = (ImageView) view.findViewById(R.id.user_login);
            viewHolder.mUser_name = (TextView) view.findViewById(R.id.user_name);
            viewHolder.mUser_fans =  (TextView) view.findViewById(R.id.user_fans);
            viewHolder.mUser_attention = (TextView) view.findViewById(R.id.user_add_attention);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }



        viewHolder.mUser_login.setImageResource(logins[position]);
        viewHolder.mUser_name.setText(name[Utility.getRandom(4)]);
        viewHolder.mUser_fans.setText(fans[position]);
        viewHolder.mUser_attention.setTag(attention[position]);

        viewHolder.mUser_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(int tag);
    }

}
