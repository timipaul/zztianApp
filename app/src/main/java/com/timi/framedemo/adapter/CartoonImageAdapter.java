package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.timi.framedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  漫画图片循环加载  嵌套在ReadBodyCartoonAdapter  这个适配器中
 */
public class CartoonImageAdapter extends BaseAdapter{

    private Context mContext;
    private List<Integer> mList = new ArrayList<>();
    private Integer[] mImgIds = {R.drawable.store_material_logo,R.drawable.home_logo, R.drawable.store_story_logo};

    public CartoonImageAdapter(Context context, List<Integer> list) {
        mContext = context;
        mList = list;
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
        ViewHolder viewHolder;
        if (view == null) {

            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_common_image, null);

            viewHolder.mListImage = (ImageView) view.findViewById(R.id.common_list_image);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mListImage.setImageResource(mImgIds[i]);

        return view;
    }

    class ViewHolder {
        ImageView mListImage;

    }

}
