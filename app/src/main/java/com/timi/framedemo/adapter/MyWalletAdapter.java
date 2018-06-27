package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.timi.framedemo.R;

/**
 * 我的钱包 和我的vip界面list 适配器
 */
public class MyWalletAdapter extends BaseAdapter{

    private Context mContext;
    private String[] mData1;
    private String[] mData2;
    private String[] mData3;


    public MyWalletAdapter(Context content,String[] data1,String[] data2,String[] data3){
        this.mContext = content;
        this.mData1 = data1;
        this.mData2 = data2;
        this.mData3 = data3;
    }

    @Override
    public int getCount() {
        return mData1.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {

            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.item_my_vip, null);

            viewHolder.horizo_1 = (TextView) view.findViewById(R.id.horizo_1);
            viewHolder.horizo_2 = (TextView) view.findViewById(R.id.horizo_2);
            viewHolder.horizo_3 = (TextView) view.findViewById(R.id.horizo_3);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.horizo_1.setText(mData1[position]);
        viewHolder.horizo_2.setText(mData2[position]);
        viewHolder.horizo_3.setText(mData3[position]);

        return view;
    }

    class ViewHolder {
        //通用一行三个TextView数据
        TextView horizo_1;
        TextView horizo_2;
        TextView horizo_3;

    }
}
