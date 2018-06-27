package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 *  漫画阅读界面适配器
 */
public class ReadBodyCartoonAdapter extends BaseAdapter{



    private Context mContext;
    private List<Integer> mList = new ArrayList<>();
    private ListView list;


    private Integer[] mImgIds = {R.drawable.store_material_logo,R.drawable.home_logo, R.drawable.store_story_logo};

    private List dates;

    public ReadBodyCartoonAdapter(Context context, List<Integer> list){
        mContext = context;
        mList = list;
    }

    class ViewHolder {

        TextView mListStory;
        ListView mImageCartoon;
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
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if(view == null){

            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.read_cartoon_content, null);
            list = (ListView) view.findViewById(R.id.read_cartoon_images);
            dates = new ArrayList();
            for (int y = 0; y < mImgIds.length; y++) {

                dates.add(mImgIds[i]);
            }
            final CartoonImageAdapter adapter = new CartoonImageAdapter(mContext, dates);
            list.setAdapter(adapter);
            viewHolder.mImageCartoon = list;
            Utility.setListViewHeightBasedOnChildren(list);


            viewHolder.mListStory = (TextView) view.findViewById(R.id.read_cartoon_story);
            viewHolder.mListAdd = (TextView) view.findViewById(R.id.read_cartoon_add);
            viewHolder.mListLike = (TextView) view.findViewById(R.id.read_cartoon_like);
            viewHolder.mListCartoon = (TextView) view.findViewById(R.id.read_cartoon_to_cartoon);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }



}
