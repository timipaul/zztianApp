package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.timi.framedemo.R;

/**
 * 横向list
 */
public class HorizontalListViewAdapter extends BaseAdapter{

    private int[] mIconIDs;
    private String[] mTitles;
    private Context mContext;

    public HorizontalListViewAdapter(Context context, String[] titles, int[] ids){
        this.mContext = context;
        this.mIconIDs = ids;
        this.mTitles = titles;
    }

    @Override
    public int getCount() {
        return mTitles.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.horizontal_list_item, null);
            holder.mImage=(ImageView)convertView.findViewById(R.id.img_list_item);
            holder.mTitle=(TextView)convertView.findViewById(R.id.text_list_item);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.mTitle.setText(mTitles[position]);
        holder.mImage.setImageResource(mIconIDs[position]);

        return convertView;
    }

    private static class ViewHolder {
        private TextView mTitle ;
        private ImageView mImage;
    }
}
