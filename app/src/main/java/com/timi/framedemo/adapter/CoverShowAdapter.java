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
import com.timi.framedemo.bean.Collect;

import java.util.ArrayList;
import java.util.List;

/**
 * 封面显示适配器
 */
public class CoverShowAdapter extends BaseAdapter{

    private Context mContext;
    private List<Collect> mList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public CoverShowAdapter(Context context, List<Collect> list) {
        mContext = context;
        mList = list;
    }

    class ViewHolder {
        ImageView mImage;
        TextView mName;
        TextView mType;

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

            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_common_content_item, null);

            viewHolder.mImage = (ImageView) view.findViewById(R.id.image);
            viewHolder.mName = (TextView) view.findViewById(R.id.name);
            viewHolder.mType = (TextView) view.findViewById(R.id.type);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GetHttpImg.setUserImg(viewHolder.mImage, mList.get(i).getChapterCover());
        viewHolder.mName.setText(mList.get(i).getBookName());
        viewHolder.mType.setText(mList.get(i).getChapterName());
        viewHolder.mImage.setId(mList.get(i).getId());
        viewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v.getId());
            }
        });
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(int tag);
    }
}
