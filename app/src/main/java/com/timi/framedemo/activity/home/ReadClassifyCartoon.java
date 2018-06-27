package com.timi.framedemo.activity.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 众创空间 - 阅读 分类
 */
public class ReadClassifyCartoon extends BaseFragment{

    private ListView mListView;
    private List datas;


    @Override
    protected void initData() {
        datas = new ArrayList();
        for (int i = 0; i < 5; i++) {
            datas.add(i);
        }


        mListView.setAdapter(new BaseAdapter() {

            class ViewHolder {
                ImageView mClassifyCartoonLogo;
                TextView mListName;
                TextView mListauthor;
                TextView mListgood;
                TextView mBrowse;
            }

            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public Object getItem(int position) {
                return datas.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
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

                viewHolder.mClassifyCartoonLogo.setImageResource(R.drawable.home_read_classify_logo_1);
                viewHolder.mListName.setText("怦然心动");
                viewHolder.mListauthor.setText("冬瓜");
                viewHolder.mListgood.setText("点赞666");
                viewHolder.mBrowse.setText("浏览6666");

                return convertView;
            }
        });
    }

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView = (ListView) view.findViewById(R.id.listview);


        return view;
    }

}
