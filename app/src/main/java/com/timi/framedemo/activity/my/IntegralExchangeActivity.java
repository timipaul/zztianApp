package com.timi.framedemo.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.MyGridView;
import com.timi.framedemo.adapter.IntegralExchangeAdapter;
import com.timi.framedemo.adapter.MyMaterialsAdapter;

/**
 * 积分兑换 或 自在商城
 */
public class IntegralExchangeActivity extends FragmentActivity implements View.OnClickListener{

    private GridView horizonView;
    private MyGridView mGridView;
    private TextView mTitle;
    /**签到*/
    private TextView mSing_in;
    /**充值*/
    private TextView recharge;
    private RelativeLayout mLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_integral_exchange);

        initView();
    }

    private void initView() {

        horizonView = (GridView) findViewById(R.id.integral_layout);
        mGridView = (MyGridView) findViewById(R.id.l_materials_content);
        mTitle = (TextView) findViewById(R.id.my_title);
        mSing_in = (TextView) findViewById(R.id.sing_in);
        recharge = (TextView) findViewById(R.id.recharge);
        mLayout = (RelativeLayout) findViewById(R.id.layout_integral);

        int[] image = {R.drawable.integral_1,R.drawable.integral_1,
                R.drawable.integral_1,R.drawable.integral_1,R.drawable.integral_1};
        String[] number = new String[]{"5张","10张","50张","100张","300张",};
        String[] integral = new String[]{"500分","1000分","4800分","9000分","25000分"};

        int[] image2 = {R.drawable.home_catroon_1,R.drawable.home_catroon_2,R.drawable.home_catroon_3,
                R.drawable.home_catroon_3,R.drawable.home_catroon_2,R.drawable.home_catroon_1};
        String[] integral2 = new String[]{"500分","1000分","4800分","9000分","25000分","8000"};
        String[] names = new String[]{"女角色一套","男角色一套","山水场景一套","背景一套","特效一套","动物一套"};


        //获取传入信息
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        int code = data.getInt("code");
        String title = data.getString("name");
        System.out.println("获取的参数" + code + " 标题 " + title);


        //阅点兑换适配器
        IntegralExchangeAdapter adapter = new IntegralExchangeAdapter(this,image,number,integral);
        horizonView.setAdapter(adapter);


        //素材购买适配器
        MyMaterialsAdapter materialsAdapter = new MyMaterialsAdapter(this,image2,names,integral2);
        mGridView.setAdapter(materialsAdapter);

        //设置标题
        mTitle.setText(title);
        if(code == 1){
            //积分兑换 显示签到和积分兑换规则
            mSing_in.setVisibility(View.VISIBLE);
            mLayout.setVisibility(View.VISIBLE);
            recharge.setVisibility(View.INVISIBLE);
        }else if(code == 2){
            //显示充值
            mSing_in.setVisibility(View.INVISIBLE);
            mLayout.setVisibility(View.GONE);
            recharge.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_return:
                //调用返回
                onBackPressed();
                break;

        }
    }
}
