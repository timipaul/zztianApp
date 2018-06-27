package com.timi.framedemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.activity.common.MoreReadDataActivity;
import com.timi.framedemo.activity.home.ReadClassifyActivity;
import com.timi.framedemo.base.BaseFragment;

/**
    众创空间 -- 阅读
 */
public class HomeReadFragment extends BaseFragment implements View.OnClickListener{

    private Button mButton_create;
    private Button mButton_ranking;
    private Button mButton_classify;
    private Button mButton_joint;
    private TextView mTextMore;


    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.home_read,null);

        mButton_create = (Button) view.findViewById(R.id.read_create_but);
        mButton_ranking = (Button) view.findViewById(R.id.read_ranking_but);
        mButton_classify = (Button) view.findViewById(R.id.read_classify_but);
        mButton_joint = (Button) view.findViewById(R.id.read_joint_but);
        mTextMore = (TextView) view.findViewById(R.id.read_more_but);

        mButton_create.setOnClickListener(this);
        mButton_ranking.setOnClickListener(this);
        mButton_classify.setOnClickListener(this);
        mButton_joint.setOnClickListener(this);
        mTextMore.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Class clazz = null;
        Intent intent = null;
        Bundle data = new Bundle();
        switch (v.getId()){
            case R.id.read_create_but:      //众创
                clazz = MoreReadDataActivity.class;
                data.putInt("code",1);
                data.putString("name","众创");
                break;
            case R.id.read_joint_but:    //接龙
                clazz = MoreReadDataActivity.class;
                data.putInt("code",2);
                data.putString("name","接龙");
                break;
            case R.id.read_ranking_but:     //排行
                clazz = MoreReadDataActivity.class;
                data.putInt("code",3);
                data.putString("name","排行");
                break;
            case R.id.read_more_but:        //更多
                clazz = MoreReadDataActivity.class;
                data.putInt("code",4);
                data.putString("name","推荐更多");
                break;
            case R.id.read_classify_but:    //分类
                clazz = ReadClassifyActivity.class;
                break;
        }

        if(clazz != null){
            intent = new Intent(mContext, clazz);
            intent.putExtra("data",data);
            startActivity(intent);
        }

    }

}
