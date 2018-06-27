package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;

import java.util.ArrayList;

/**
 * 圈子主要内容适配器
 */
public class CircleContentAdapter extends BaseAdapter{


    private Context mContext;
    private ArrayList<Object> mList = new ArrayList<>();

    private Integer[] logins = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    private Integer[] mImgIds = {R.drawable.store_material_logo,R.drawable.home_logo, R.drawable.home_catroon_1};
    private String[] name = {"用户1","用户2","用户3","用户4"};


    private OnItemClickListener onItemClickListener;

    public CircleContentAdapter(Context context, ArrayList<Object> list) {
        mContext = context;
        mList = list;
    }

    class ViewHolder {

        ImageView mUser_image;          //发帖人头像
        TextView mUser_name;            //发帖人名称
        TextView mUser_date;            //发布时间
        TextView mAdd;                 //加关注
        TextView mContent_title;        //内容标题
        ImageView mContent_image;       //内容图片
        TextView mRead_number;          //阅读量
        TextView mStamp_number;         //踩数量
        TextView mPraise;               //赞
        TextView mPraise_number;       //赞数量
        RelativeLayout layout;          //头部视图
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if (view == null) {

            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.circle_content_item, null);

            viewHolder.layout = (RelativeLayout) view.findViewById(R.id.layout_head);

            viewHolder.mUser_image =  (ImageView) view.findViewById(R.id.user_image);
            viewHolder.mUser_name =  (TextView) view.findViewById(R.id.user_name);
            viewHolder.mUser_date =  (TextView) view.findViewById(R.id.presentation);
            viewHolder.mAdd =  (TextView) view.findViewById(R.id.add_attention);
            viewHolder.mContent_title =  (TextView) view.findViewById(R.id.content_title);
            viewHolder.mContent_image =  (ImageView) view.findViewById(R.id.content_image);
            viewHolder.mRead_number =  (TextView) view.findViewById(R.id.read_number);
            viewHolder.mStamp_number =  (TextView) view.findViewById(R.id.reply_number);
            viewHolder.mPraise =  (TextView) view.findViewById(R.id.praise);
            viewHolder.mPraise_number =  (TextView) view.findViewById(R.id.praise_number);



            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }



        viewHolder.mUser_image.setImageResource(logins[Utility.getRandom(4)]);
        viewHolder.mUser_name.setText(name[Utility.getRandom(4)]);
        viewHolder.mUser_date.setText(Utility.getRandom(12) + "小时前");
        viewHolder.mAdd.setTag(position);
        viewHolder.mContent_title.setText("快来评价吧...");
        viewHolder.mContent_image.setImageResource(mImgIds[Utility.getRandom(3)]);
        viewHolder.mRead_number.setText("666");
        viewHolder.mStamp_number.setText("222");
        viewHolder.mPraise.setBackgroundResource(R.color.pink);
        viewHolder.mPraise_number.setText("555");
        viewHolder.layout.setTag(position);

        /*viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v);
            }
        });*/

        return view;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(View view);
    }
}
