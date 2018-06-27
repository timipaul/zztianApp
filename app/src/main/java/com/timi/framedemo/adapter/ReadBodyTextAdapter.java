package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.timi.framedemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ReadBodyTextAdapter extends BaseAdapter{

    private Context mContext;
    private List<Integer> mList = new ArrayList<>();

    private onItemListener mOnItemListener;

    public ReadBodyTextAdapter(Context context, List<Integer> list) {
        mContext = context;
        mList = list;
    }


    class ViewHolder {

        TextView mListStory;
        TextView mListNumber;
        TextView mListAuthor;
        TextView mListText;
        TextView mListAdd;
        TextView mListLike;
        TextView mListCartoon;

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
            view = LayoutInflater.from(mContext).inflate(R.layout.read_text_content, null);
            viewHolder.mListStory = (TextView) view.findViewById(R.id.read_content_story);
            viewHolder.mListNumber =  (TextView) view.findViewById(R.id.read_section_number);
            viewHolder.mListAuthor = (TextView) view.findViewById(R.id.read_section_author);
            viewHolder.mListText = (TextView) view.findViewById(R.id.read_content_text);
            viewHolder.mListAdd = (TextView) view.findViewById(R.id.read_content_add);
            viewHolder.mListLike = (TextView) view.findViewById(R.id.read_content_like);
            viewHolder.mListCartoon = (TextView) view.findViewById(R.id.read_content_to_cartoon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }



        Random random = new Random();

        String s = "我是主要类容,我会有许许多多的文字信息....时代峰峻是打开见风使舵见风使舵拉就拉反攻倒算换个 申达股份电视剧看过阿萨德返回我是主要类容," +
                "我会有许许多多的文字信息....时代峰峻是打开见风使舵见风使舵拉就拉反攻倒算换个 申达股份电视剧看过阿萨德返回";
        viewHolder.mListStory.setText("本段其它剧情" + random.nextInt(10) + "个");
        viewHolder.mListNumber.setText("第" + (i+1) + "段");
        viewHolder.mListAuthor.setText("婷迷冬瓜");
        viewHolder.mListText.setText(s);
        viewHolder.mListAdd.setText("另写此段");
        viewHolder.mListLike.setText("点赞");
        viewHolder.mListCartoon.setText("本段漫画版" + random.nextInt(10) + "个");


        viewHolder.mListLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"点赞：+1" + i + "被点击了",Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext,"点赞：+1",Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.mListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"跳转到编辑页面....",Toast.LENGTH_SHORT).show();
                mOnItemListener.onAddEditClick(1);
            }

        });


        return view;

    }

    //监听点击接口
    public interface onItemListener {
        void onAddEditClick(int i);
    }

    public void setOnItemDeleteClickListener(onItemListener mOnItemListener) {
        this.mOnItemListener = mOnItemListener;
    }


}
