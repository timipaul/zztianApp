package com.timi.framedemo.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.activity.common.CartoonSeek;

/**
 * 众创空间 - 阅读 - 分类
 */
public class ReadClassifyActivity extends FragmentActivity implements ImageView.OnClickListener{

    private TextView tReturn;
    private ImageView mSeek;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_read_classify);

        tReturn = (TextView) findViewById(R.id.tv_classify_return);
        mSeek = (ImageView) findViewById(R.id.read_classify_seek);

        tReturn.setOnClickListener(this);
        mSeek.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_classify_return:  //返回
                onBackPressed();
                break;
            case R.id.read_classify_seek:  //跳转到搜索页面
                Intent intent = new Intent(this, CartoonSeek.class);
                startActivity(intent);
                break;
        }
    }
}
