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
 * 发现 关注页面主体内容适配器
 */
public class FindItemAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Object> mList = new ArrayList<>();

    private Integer[] logins = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    private Integer[] mImgIds = {R.drawable.store_material_logo,R.drawable.home_logo, R.drawable.store_story_logo};
    private String[] name = {"用户1","用户2","用户3","用户4"};


    private OnItemClickListener onItemClickListener;

    public FindItemAdapter(Context context, ArrayList<Object> list) {
        mContext = context;
        mList = list;
    }

    class ViewHolder {

        ImageView mUser_image;          //用户头像
        TextView mUser_name;            //用户名
        TextView mUser_date;            //发布时间
        TextView mUser_content;         //主体类容
        ImageView mContent_image;       //内容图片
        TextView mRead_number;          //阅读量
        TextView mReply_number;         //回复数量
        TextView mPraise;               //赞
        TextView mPraise_number;       //赞数量
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

            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_common_find_item, null);

            viewHolder.mUser_image = (ImageView) view.findViewById(R.id.community_image);
            viewHolder.mUser_name = (TextView) view.findViewById(R.id.user_name);
            viewHolder.mUser_date =  (TextView) view.findViewById(R.id.publish_date);
            viewHolder.mUser_content = (TextView) view.findViewById(R.id.content_title);
            viewHolder.mContent_image = (ImageView) view.findViewById(R.id.content_image);
            viewHolder.mRead_number = (TextView) view.findViewById(R.id.read_number);
            viewHolder.mReply_number = (TextView) view.findViewById(R.id.reply_number);
            viewHolder.mPraise = (TextView) view.findViewById(R.id.praise);
            viewHolder.mPraise_number = (TextView) view.findViewById(R.id.praise_number);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }



        viewHolder.mUser_image.setImageResource(logins[Utility.getRandom(4)]);
        viewHolder.mUser_name.setText(name[Utility.getRandom(4)]);
        viewHolder.mUser_date.setText("一小时前");
        viewHolder.mUser_content.setText("快来评价吧...");
        viewHolder.mContent_image.setImageResource(mImgIds[Utility.getRandom(3)]);
        viewHolder.mRead_number.setText("666");
        viewHolder.mReply_number.setText("222");
        viewHolder.mPraise.setBackgroundResource(R.color.pink);
        viewHolder.mPraise_number.setText("555");

        /*viewHolder.mUser_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到帖子详情  没看到设计页面
                onItemClickListener.onItemClick(position);
            }
        });*/

        return view;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(int tag);
    }
}
