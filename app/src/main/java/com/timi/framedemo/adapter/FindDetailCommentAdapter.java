package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.bean.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现 - 世界 - 帖子 - 评论
 */
public class FindDetailCommentAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Object> mList = new ArrayList<>();
    /** 判读来源，1表示显示帖子中的回帖 **/
    private Integer mCode;

    private Integer[] logins = {R.drawable.store_1,R.drawable.store_2,R.drawable.store_3,R.drawable.store_4};
    private String[] name = {"用户1","用户2","用户3","用户4"};

    private Integer parameter;

    private OnItemClickListener onItemClickListener;

    public FindDetailCommentAdapter(Context context, ArrayList<Object> list, Integer code) {
        mContext = context;
        mList = list;
        mCode = code;
    }

    class ViewHolder {

        ImageView mUser_image;          //发帖人头像
        TextView mUser_name;            //发帖人名称
        TextView mContent_title;        //内容
        TextView mArrange_data;         //排序和发布时间
        TextView mPraise;               //赞
        TextView mPraise_number;       //赞数量
        TextView mtext;                 //分割线，用与解决list显示bug
        ListView mListView;             //评论

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
        final ViewHolder viewHolder;
        if (view == null) {

            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_common_comment, null);

            viewHolder.mUser_image =  (ImageView) view.findViewById(R.id.user_image);
            viewHolder.mUser_name =  (TextView) view.findViewById(R.id.user_name);
            viewHolder.mArrange_data =  (TextView) view.findViewById(R.id.presentation);
            viewHolder.mContent_title =  (TextView) view.findViewById(R.id.content_title);
            viewHolder.mPraise =  (TextView) view.findViewById(R.id.praise);
            viewHolder.mPraise_number =  (TextView) view.findViewById(R.id.praise_number);
            viewHolder.mtext = (TextView) view.findViewById(R.id.text);
            viewHolder.mListView = (ListView) view.findViewById(R.id.comment_list);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.mUser_image.setImageResource(logins[Utility.getRandom(4)]);
        viewHolder.mUser_name.setText(name[Utility.getRandom(4)]);
        viewHolder.mArrange_data.setText(Utility.getRandom(100) + "楼 " + Utility.getRandom(12) + "小时前");
        viewHolder.mContent_title.setText("黑夜 给了你一双黑色的眼睛,学会好好珍惜,然后呢。。。没有然后了");
        viewHolder.mPraise.setBackgroundResource(R.color.pink);
        viewHolder.mPraise_number.setText("555");
        viewHolder.mContent_title.setBackgroundResource(R.color.blue);


        //判断回复信息  有回复信息才加载
        System.out.println("来源：");
        if(mCode == 1){
            List<Comment> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Comment comment = new Comment();
                comment.setName("评论者" + i);
                comment.setContent("你都说了些什么" + i);
                list.add(comment);

            }

            //显示评论信息
            CommentAdapter adapter = new CommentAdapter(mContext,list);
            viewHolder.mListView.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(viewHolder.mListView);

            //内容设置点击效果 从帖子详情跳转到回帖详情
            viewHolder.mContent_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(parameter);
                }
            });

        }

        parameter = position;

        /*viewHolder.mContent_title.post(new Runnable() {
            @Override
            public void run() {
                int txtPart = viewHolder.mContent_title.getLineCount();
                int txtHeight = viewHolder.mContent_title.getMeasuredHeight();
                System.out.println("高度：" + txtHeight);
                //viewHolder.mContent_title.setHeight(txtPart * Utility.dpTopx(mContext,23));

            }
        });*/




        //用于解决最后的显示不全bug  用来填充
        if(position == mList.size() - 1){
            viewHolder.mtext.setVisibility(View.VISIBLE);
        }

        return view;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(Integer parameter);
    }
}
