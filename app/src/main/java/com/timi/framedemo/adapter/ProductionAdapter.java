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

import java.util.List;

/**
 * 作品显示适配器
 */
public class ProductionAdapter extends BaseAdapter{
    private Context mContext;
    private List<Cartoon> mList;
    private OnItemClickListener onItemClickListener;

    public ProductionAdapter(Context context,List<Cartoon> list){
        this.mContext = context;
        this.mList = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_user_message, null);
            holder.mImage=(ImageView)convertView.findViewById(R.id.user_cartoon_image);
            holder.name=(TextView)convertView.findViewById(R.id.user_cartoon_name);
            holder.type=(TextView)convertView.findViewById(R.id.user_cartoon_type);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.mImage.setId(mList.get(position).getId());
        holder.name.setText(mList.get(position).getBookName());
        holder.type.setText(mList.get(position).getBookType());
        GetHttpImg.setUserImg(holder.mImage, mList.get(position).getCover());

        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v.getId());
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private TextView name ;
        private TextView type;
        private ImageView mImage;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(int tag);
    }
}
