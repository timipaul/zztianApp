package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.timi.framedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ReadCommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<Integer> mList = new ArrayList<>();

    public ReadCommentAdapter(Context context, List<Integer> list) {
        mContext = context;
        mList = list;
    }


    class ViewHolder {
        ImageView mImageLogo;
        ImageView mImageSubjoinLogo;
        TextView mTextName;
        TextView mTextAttention;
        TextView mTextContent;
        TextView mTextDate;
        TextView mTextReply;
        TextView mTextGood;



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
    public View getView(int i, View view, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if (view == null) {

            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.read_comment, null);

            viewHolder.mImageLogo = (ImageView) view.findViewById(R.id.read_comment_logo);
            viewHolder.mTextName = (TextView) view.findViewById(R.id.read_comment_name);
            viewHolder.mTextAttention = (TextView) view.findViewById(R.id.read_comment_attention);
            viewHolder.mTextContent = (TextView) view.findViewById(R.id.read_comment_content);
            viewHolder.mImageSubjoinLogo = (ImageView) view.findViewById(R.id.read_comment_subjoin_logo);
            viewHolder.mTextDate = (TextView) view.findViewById(R.id.read_comment_date);
            viewHolder.mTextReply = (TextView) view.findViewById(R.id.read_comment_reply);
            viewHolder.mTextGood = (TextView) view.findViewById(R.id.read_comment_good);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据
        viewHolder.mImageLogo.setImageResource(R.drawable.store_1);
        viewHolder.mImageSubjoinLogo.setImageResource(R.drawable.timi_1);
        viewHolder.mTextName.setText("婷迷冬瓜");
        viewHolder.mTextAttention.setText("+关注");
        viewHolder.mTextContent.setText("评论加关注点赞啊,在附上美图一张");
        viewHolder.mTextDate.setText(i + "分钟前");
        viewHolder.mTextReply.setText("回复 9999");
        viewHolder.mTextGood.setText("点赞 9999");

        return view;
    }
}
