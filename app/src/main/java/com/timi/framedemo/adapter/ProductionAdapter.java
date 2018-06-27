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
 * 作品显示适配器
 */
public class ProductionAdapter extends BaseAdapter{

    private int[] mImage;
    private String[] mName;
    private String[] mStyle;
    private Context mContext;

    public ProductionAdapter(Context context, int[] image,String[] name,String[] type){
        this.mContext = context;
        this.mImage = image;
        this.mName = name;
        this.mStyle = type;
    }

    @Override
    public int getCount() {
        return mImage.length;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_user_message, null);
            holder.mImage=(ImageView)convertView.findViewById(R.id.user_cartoon_image);
            holder.name=(TextView)convertView.findViewById(R.id.user_cartoon_name);
            holder.type=(TextView)convertView.findViewById(R.id.user_cartoon_type);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.name.setText(mName[position]);
        holder.type.setText(mStyle[position]);
        holder.mImage.setImageResource(mImage[position]);

        return convertView;
    }

    private static class ViewHolder {
        private TextView name ;
        private TextView type;
        private ImageView mImage;
    }
}
