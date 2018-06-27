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
import java.util.List;

/**
 * 发现 - 圈子 - 更多 - 我的 下的适配器
 */
public class FindCircleMyAdapter extends BaseAdapter{


    private Context mContext;
    private List<Integer> mList = new ArrayList<>();
    private Integer[] logins = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    private String[] names = {"百货大楼","声控","甜美的咬痕","怦然心动","手绘"};

    public FindCircleMyAdapter(Context context, List<Integer> list) {
        mContext = context;
        mList = list;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
        TextView member;
        TextView affect;
        TextView new_data;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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

            view = LayoutInflater.from(mContext).inflate(R.layout.find_circle_more_my_item, null);

            viewHolder.image = (ImageView) view.findViewById(R.id.logo);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.member = (TextView) view.findViewById(R.id.content);
            viewHolder.affect = (TextView) view.findViewById(R.id.affect);
            viewHolder.new_data = (TextView) view.findViewById(R.id.new_data);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.image.setImageResource(logins[Utility.getRandom(4)]);
        viewHolder.name.setText(names[Utility.getRandom(5)]);
        viewHolder.member.setText("成员" + Utility.getRandom(1000));
        viewHolder.affect.setText("影响力" + Utility.getRandom(1000));
        viewHolder.new_data.setText(Utility.getRandom(1000) + "新贴");

        return view;
    }
}
