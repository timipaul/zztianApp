package com.timi.framedemo.activity.compile;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.RecyAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 *  商城 - 素材 - 滚动logo
 */
public class MaterialImageCarousel extends BaseFragment implements RecyAdapter.OnItemClickListener {

    private ImageView img;

    private RecyclerView recyclerview;


    private Integer[] mImgIds = {R.drawable.store_1, R.drawable.store_2, R.drawable.store_3, R.drawable.store_4, R.drawable.store_5,
            R.drawable.home_logo, R.drawable.store_logo, R.drawable.store_material_logo};
    private List<Integer> datas;

    private RecyAdapter recyAdapter;

    private Handler mHandler = new Handler();

    private LinearLayoutManager layoutManager;

    private int oldItem=0;

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.store_image_carousel,null);

        img = (ImageView)view.findViewById(R.id.img);

        recyclerview = (RecyclerView)view.findViewById(R.id.recyclerview);

        ButterKnife.bind(view);

        initData();

        initRecy();

        img.setImageResource(datas.get(0));

        recyAdapter.setOnItemClickListener(this);

        return view;
    }

    Runnable scrollRunnable=new Runnable() {
        @Override
        public void run() {
            recyclerview.scrollBy(3,0);

            //      int firstItem = layoutManager.findFirstVisibleItemPosition();
            int firstItem=layoutManager.findFirstVisibleItemPosition();
            if(firstItem!=oldItem&&firstItem>0){
                oldItem=firstItem;
                img.setImageResource(datas.get(oldItem%datas.size()));
            }

            mHandler.postDelayed(scrollRunnable,50);
        }
    };

    private void initRecy() {
        recyAdapter=new RecyAdapter(mContext,datas);

        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(recyAdapter);
    }



    protected void initData() {
        datas=new ArrayList<>();
        for (int i = 0; i <mImgIds.length ; i++) {
            datas.add(mImgIds[i]);
        }
    }

    @Override
    public void onItemClick(View view, int tag) {
        Toast.makeText(mContext,"第"+tag+"张图片被点击了",Toast.LENGTH_SHORT).show();
    }


    public void onResume() {
        super.onResume();
        mHandler.postDelayed(scrollRunnable,50);
    }


    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(scrollRunnable);
    }

}
