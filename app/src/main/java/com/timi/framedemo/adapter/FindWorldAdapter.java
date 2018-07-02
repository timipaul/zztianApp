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
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.bean.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现 世界 适配器  帖子详情共用
 */
public class FindWorldAdapter extends BaseAdapter{

    private Context mContext;
    private List<Topic> mList = new ArrayList<>();

    //private Integer[] logins = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    //private Integer[] mImgIds = {R.drawable.store_material_logo,R.drawable.home_logo, R.drawable.home_catroon_1};
   // private String[] name = {"用户1","用户2","用户3","用户4"};
    //private String[] types = {"漫画","小说","汉文化","都市","谈情","说爱"};
    private Integer[] vips = {View.INVISIBLE,View.VISIBLE};

    private Integer parameter;

    private OnItemClickListener onItemClickListener;

    public FindWorldAdapter(Context context, List<Topic> list) {
        mContext = context;
        mList = list;
    }

    class ViewHolder {

        ImageView mUser_image;          //发帖人头像
        TextView mUser_name;            //发帖人名称
        TextView mVip;                  //VIP
        TextView mType;                 //类型
        TextView mAdd;                 //加关注
        TextView mContent_title;        //内容标题
        ImageView mContent_image;       //内容图片
        TextView mIssue_date;            //发布时间
        TextView mRead_number;          //阅读量
        TextView mReply_number;         //踩数量
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

            view = LayoutInflater.from(mContext).inflate(R.layout.find_world_item, null);

            viewHolder.layout = (RelativeLayout) view.findViewById(R.id.layout_head);

            viewHolder.mUser_image =  (ImageView) view.findViewById(R.id.user_image);
            viewHolder.mUser_name =  (TextView) view.findViewById(R.id.user_name);
            viewHolder.mVip =  (TextView) view.findViewById(R.id.user_vip);
            viewHolder.mType =  (TextView) view.findViewById(R.id.presentation);
            viewHolder.mIssue_date =  (TextView) view.findViewById(R.id.issue_date);
            viewHolder.mAdd =  (TextView) view.findViewById(R.id.add_attention);
            viewHolder.mContent_title =  (TextView) view.findViewById(R.id.content_title);
            viewHolder.mContent_image =  (ImageView) view.findViewById(R.id.content_image);
            viewHolder.mRead_number =  (TextView) view.findViewById(R.id.read_number);
            viewHolder.mReply_number =  (TextView) view.findViewById(R.id.reply_number);
            viewHolder.mPraise =  (TextView) view.findViewById(R.id.praise);
            viewHolder.mPraise_number =  (TextView) view.findViewById(R.id.praise_number);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }



        //判断内容里面是图片
        if(mList.get(i).getHeadimg() != null){
            GetHttpImg.setUserImg(viewHolder.mUser_image, mList.get(i).getHeadimg());
        }

        viewHolder.mUser_name.setText(mList.get(i).getNickName());
        viewHolder.mVip.setVisibility(vips[Utility.getRandom(2)]);
        viewHolder.mType.setText(mList.get(i).getBoardId() + "(圈子)");
        if(mList.get(i).getPublishtime() != null){
            viewHolder.mIssue_date.setText(Utility.longToDate(mList.get(i).getPublishtime()));
        }
        viewHolder.mAdd.setTag(i);
        viewHolder.mContent_title.setText(mList.get(i).getTitle());
        viewHolder.mPraise_number.setText(mList.get(i).getPraisecount() + "");
        viewHolder.layout.setTag(mList.get(i).getId());

        viewHolder.mRead_number.setText(mList.get(i).getClickcount() + "");
        viewHolder.mReply_number.setText(mList.get(i).getReplycount() + "");
        //判断是否点赞
        if(false){
            viewHolder.mPraise.setBackgroundResource(R.color.pink);
        }

        parameter = i;

        viewHolder.mPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(parameter);
            }
        });

        return view;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(Integer parameter);
    }
}
