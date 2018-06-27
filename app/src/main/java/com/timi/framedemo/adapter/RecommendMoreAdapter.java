package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.GetHttpImg;
import com.timi.framedemo.bean.Cartoon;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐 更多等适配器
 */
public class RecommendMoreAdapter extends BaseAdapter{


    private Context mContext;
    private List<Cartoon> mList = new ArrayList<>();

    public RecommendMoreAdapter(Context context, List<Cartoon> list){
        this.mContext = context;
        this.mList = list;
    }

    class ViewHolder {
        ImageView mClassifyCartoonLogo;
        TextView mListName;
        TextView mListauthor;
        TextView mListgood;
        TextView mBrowse;
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
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_read_classify_cartoon_item, null);

            viewHolder.mClassifyCartoonLogo = (ImageView) convertView.findViewById(R.id.classify_cartoon_logo);
            viewHolder.mListName = (TextView) convertView.findViewById(R.id.classify_cartoon_name);
            viewHolder.mListauthor = (TextView) convertView.findViewById(R.id.classify_cartoon_author);
            viewHolder.mListgood = (TextView) convertView.findViewById(R.id.classify_cartoon_clickGood);
            viewHolder.mBrowse = (TextView) convertView.findViewById(R.id.calssify_cartoon_browse);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GetHttpImg.setUserImg(viewHolder.mClassifyCartoonLogo, mList.get(i).getCover());
        viewHolder.mListName.setText(mList.get(i).getBookName());
        viewHolder.mListauthor.setText(mList.get(i).getBookType());
        viewHolder.mListgood.setText("点赞666");
        viewHolder.mBrowse.setText("浏览6666");

        return convertView;
    }

}
