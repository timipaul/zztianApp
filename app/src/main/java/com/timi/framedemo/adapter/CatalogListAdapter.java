package com.timi.framedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.GetHttpImg;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.bean.CartoonChapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 漫画小说目录适配器
 */
public class CatalogListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<List<CartoonChapter>> mNewList = new ArrayList<>();

    String chapter;
    String topChapter;
    private OnItemClickListener onItemClickListener;

    public CatalogListAdapter(Context context, List<CartoonChapter> list) {
        mContext = context;

        //把list中的数据按第几话归类出来
        //[[{1-1},{1-2}],{2}]
        List<CartoonChapter> car = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CartoonChapter c;
            Integer temp = i+1 == list.size()?null:i+1;
            if(temp != null){
                chapter = (String) list.get(i).getChapterId().subSequence(0, 1);
                topChapter = (String) list.get(temp).getChapterId().subSequence(0, 1);
                if(chapter.equals(topChapter)){
                    //表示有相同的
                    car.add(list.get(i));
                }else{
                    car.add(list.get(i));
                    mNewList.add(car);
                    car = new ArrayList<>();
                }
            }else{
                car.add(list.get(i));
                mNewList.add(car);
            }
        }


    }

    class ViewHolder {

        RelativeLayout mRelaLayout;
        ImageView mImageView;
        TextView mListName;
        TextView mListDate;
        TextView mListPraise;

    }



    @Override
    public int getCount() {
        return mNewList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewList.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_common_horizo_scroll_view, null);
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_view);

            List<CartoonChapter> ll = mNewList.get(i);
            for (int k = 0; k < ll.size(); k++) {
                //横向添加数据
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.read_catalogue, null);
                viewHolder.mRelaLayout = (RelativeLayout)view2.findViewById(R.id.rela_layout);
                viewHolder.mImageView = (ImageView) view2.findViewById(R.id.read_catalog_logo);
                viewHolder.mListName = (TextView) view2.findViewById(R.id.read_catalog_name);
                viewHolder.mListDate =  (TextView) view2.findViewById(R.id.read_catalog_date);
                viewHolder.mListPraise = (TextView) view2.findViewById(R.id.read_catalog_praise);


                viewHolder.mRelaLayout.setLayoutParams(new LinearLayout.LayoutParams(Utility.getWidth(mContext), LinearLayout.LayoutParams.WRAP_CONTENT));
                viewHolder.mRelaLayout.setTag(ll.get(k).getChapterId());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                GetHttpImg.setUserImg(viewHolder.mImageView, ll.get(k).getChapterCover());
                viewHolder.mListName.setText("第" + ll.get(k).getChapterId() + "话 " + ll.get(k).getChapterName());
                viewHolder.mListDate.setText(df.format(ll.get(k).getCreatedate()));
                viewHolder.mListPraise.setText("赞 " + ll.get(k).getPraiseNum());

                viewHolder.mRelaLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v);
                    }
                });

                layout.addView(view2);
            }
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(View view);
    }


}
