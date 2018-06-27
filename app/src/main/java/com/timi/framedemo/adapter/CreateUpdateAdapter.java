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
import java.util.Random;

/**
 * 创作 -- 更新故事  适配器
 */
public class CreateUpdateAdapter extends BaseAdapter{
    private Context mContext;
    private List<Integer> mList = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private Integer[] mImgIds = {R.drawable.store_material_logo,R.drawable.home_logo,R.drawable.home_logo2};
    private String[] mSType = {"众创","接龙","独创","空间"};
    private String[] mSTable = {"恐怖","校园","搞笑","男生","女生","玄幻"};

    public CreateUpdateAdapter(Context context, List<Integer> list){
        this.mContext = context;
        this.mList = list;
    }

    class ViewHolder {
        ImageView mImageView;
        TextView mTextLook;
        TextView mTextType;
        TextView mTextLabel;
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
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();

            view = LayoutInflater.from(mContext).inflate(R.layout.home_read_classify_list, null);

            viewHolder.mImageView = (ImageView) view.findViewById(R.id.home_read_classify_carboon);
            viewHolder.mTextLook = (TextView) view.findViewById(R.id.home_read_classify_look);
            viewHolder.mTextType = (TextView) view.findViewById(R.id.home_read_classify_type);
            viewHolder.mTextLabel = (TextView) view.findViewById(R.id.home_read_classify_table);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Random r = new Random();
        viewHolder.mImageView.setImageResource(mImgIds[r.nextInt(2)]);
        viewHolder.mTextLook.setText("热度: " + r.nextInt(100000));
        viewHolder.mTextType.setText(mSType[r.nextInt(4)]);
        viewHolder.mTextLabel.setText(mSTable[r.nextInt(6)]);

        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
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
