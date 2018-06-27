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
 * 素材显示适配器
 */
public class MyMaterialsAdapter extends BaseAdapter{
    private int[] mImage;
    private String[] mName;
    private String[] mIntegral;
    private Context mContext;

    public MyMaterialsAdapter(){}

    public MyMaterialsAdapter(Context context,int[] image,String[] name,String[] integral){
        this.mContext = context;
        this.mImage = image;
        this.mName = name;
        this.mIntegral = integral;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_materials_item, null);
            holder.Image=(ImageView)convertView.findViewById(R.id.materials_image);
            holder.name=(TextView)convertView.findViewById(R.id.materials_name);
            holder.integral=(TextView)convertView.findViewById(R.id.materials_number);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.integral.setText(mIntegral[position]);
        holder.name.setText(mName[position]);
        holder.Image.setImageResource(mImage[position]);

        return convertView;
    }

    private static class ViewHolder {
        private ImageView Image;
        private TextView name ;
        private TextView integral;

    }
}
