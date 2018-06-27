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
 * 积分兑换阅读卷
 */
public class IntegralExchangeAdapter extends BaseAdapter{

    private int[] mImage;
    private String[] mNumber;
    private String[] mIntegral;
    private Context mContext;

    public IntegralExchangeAdapter(){}

    public IntegralExchangeAdapter(Context context,int[] image,String[] number,String[] integral){
        this.mContext = context;
        this.mImage = image;
        this.mNumber = number;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.integral_exchange_item, null);
            holder.Image=(ImageView)convertView.findViewById(R.id.integral_image);
            holder.number=(TextView)convertView.findViewById(R.id.number);
            holder.integral=(TextView)convertView.findViewById(R.id.integral_number);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.integral.setText(mNumber[position]);
        holder.number.setText(mNumber[position]);
        holder.Image.setImageResource(mImage[position]);

        return convertView;
    }

    private static class ViewHolder {
        private ImageView Image;
        private TextView number ;
        private TextView integral;

    }
}
