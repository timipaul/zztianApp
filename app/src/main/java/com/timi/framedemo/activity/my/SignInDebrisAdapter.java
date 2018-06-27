package com.timi.framedemo.activity.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.timi.framedemo.R;



/**
 *  签到弹框
 */
public class SignInDebrisAdapter extends BaseAdapter{

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private String[] mIntegral = {"1","5","10","15","20","25","礼包"};
    private int mPeriod;
    private int mDay;

    public SignInDebrisAdapter(Context context, int period,String[] integral,int day) {
        this.mContext = context;
        this.mIntegral = integral;
        this.mPeriod = period;
        this.mDay = day;
    }

    @Override
    public int getCount() {
        return mIntegral.length;
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
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {

            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.sign_in_item, null);

            viewHolder.day = (TextView) view.findViewById(R.id.day);
            viewHolder.integral = (TextView) view.findViewById(R.id.integral);
            viewHolder.stare = (TextView) view.findViewById(R.id.state);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.day.setText("第" + (i +1) +  "天");
        //判断显示的最后一个显示礼包
        if (i == mIntegral.length - 1) {
            viewHolder.integral.setText(mIntegral[i]);
        } else {
            viewHolder.integral.setText(mIntegral[i] + "积分");
        }

        viewHolder.stare.setOnClickListener(null);

        // i == 6 表示一个循环的最后一天
        if(mDay == i || i == 6){
            viewHolder.stare.setText("领取");
            viewHolder.stare.setBackgroundResource(R.color.orange);
            viewHolder.stare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(mDay);
                }
            });
        }else if (mDay > i){
            viewHolder.stare.setText("已领");
            viewHolder.stare.setBackgroundResource(R.color.gray);
        }
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(int tag);
    }

    class ViewHolder {
        TextView day;
        TextView integral;
        TextView stare;

    }

}
