package com.timi.framedemo.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.timi.framedemo.R;

import java.util.List;

/**
 * 图片循环...
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Integer> datas;
    private OnItemClickListener onItemClickListener;

    public RecyAdapter(Context context, List<Integer> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_item_horizon, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int newPos=position%datas.size();

        holder.img.setImageResource(datas.get(newPos));

        holder.itemView.setTag(position);


    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onClick(View view) {
        if(onItemClickListener!=null){

            onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;

    }
    public interface OnItemClickListener{
        void onItemClick(View view, int tag);
    }

}

