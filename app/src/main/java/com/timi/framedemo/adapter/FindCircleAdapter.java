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
import com.timi.framedemo.Utils.GetHttpImg;
import com.timi.framedemo.bean.Topic;

import java.util.ArrayList;

/**
 * 发现 - 圈子内容适配器
 */
public class FindCircleAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Topic> mList = new ArrayList<>();

    //private Integer[] logins = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    //private Integer[] mImgIds = {R.drawable.store_material_logo,R.drawable.home_logo, R.drawable.home_catroon_1};
    //private String[] name = {"用户1","用户2","用户3","用户4"};


    private OnItemClickListener onItemClickListener;

    public FindCircleAdapter(Context context, ArrayList<Topic> list) {
        mContext = context;
        mList = list;
    }

    class ViewHolder {

        ImageView mCommunity_image;        //圈子头像
        TextView mCommunity_name;            //圈子名称
        TextView mMember_number;            //成员数量
        TextView mData_number;         //帖子数量
        TextView mInto;                 //进入图标
        TextView mContent_title;        //内容标题
        ImageView mContent_image;       //内容图片
        ImageView mUser_image;          //发帖人头像
        TextView mUser_name;            //发帖人名称
        TextView mRead_number;          //阅读量
        TextView mReply_number;         //回复数量
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
    public View getView(final int i, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if (view == null) {

            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.find_circle_item, null);

            viewHolder.layout = (RelativeLayout) view.findViewById(R.id.layout_head);
            viewHolder.mCommunity_image = (ImageView) view.findViewById(R.id.community_image);
            viewHolder.mCommunity_name = (TextView) view.findViewById(R.id.community_name);
            viewHolder.mMember_number =  (TextView) view.findViewById(R.id.presentation);
            viewHolder.mData_number =  (TextView) view.findViewById(R.id.data_number);
            viewHolder.mInto =  (TextView) view.findViewById(R.id.into);
            viewHolder.mContent_title =  (TextView) view.findViewById(R.id.content_title);
            viewHolder.mContent_image =  (ImageView) view.findViewById(R.id.content_image);
            viewHolder.mUser_image =  (ImageView) view.findViewById(R.id.user_image);
            viewHolder.mUser_name =  (TextView) view.findViewById(R.id.user_name);
            viewHolder.mRead_number =  (TextView) view.findViewById(R.id.read_number);
            viewHolder.mReply_number =  (TextView) view.findViewById(R.id.reply_number);
            viewHolder.mPraise =  (TextView) view.findViewById(R.id.praise);
            viewHolder.mPraise_number =  (TextView) view.findViewById(R.id.praise_number);



            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        GetHttpImg.setUserImg(viewHolder.mUser_image, mList.get(i).getHeadimg());
        GetHttpImg.setUserImg(viewHolder.mCommunity_image, mList.get(i).getHeadimg());
        if(mList.get(i).getContentImg() != null){
            GetHttpImg.setUserImg(viewHolder.mContent_image, mList.get(i).getContentImg());
        }

        viewHolder.mUser_name.setText(mList.get(i).getNickName());
        viewHolder.mCommunity_name.setText(mList.get(i).getBoardId());
        viewHolder.mMember_number.setText("成员0");
        viewHolder.mData_number.setText("帖子0");
        viewHolder.mContent_title.setText(mList.get(i).getTitle());
        viewHolder.mRead_number.setText(mList.get(i).getClickcount()+"");
        viewHolder.mReply_number.setText(mList.get(i).getReplycount()+"");
        viewHolder.mPraise.setBackgroundResource(R.color.pink);
        viewHolder.mPraise_number.setText(mList.get(i).getPraisecount()+"");
        viewHolder.layout.setTag(mList.get(i).getNickName());
        viewHolder.layout.setId(mList.get(i).getId());

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v);
            }
        });

        return view;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(View view);
    }
}