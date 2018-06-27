package com.timi.framedemo.activity.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.MyGridView;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.adapter.HorizontalListViewAdapter;
import com.timi.framedemo.adapter.MyWalletAdapter;

/**
 * 视图
 *      我的钱包
 *      我的vpi
 */
public class WalletOrVip extends Activity{

    private TextView mReturn;           //返回
    private TextView mTitle;            //标题
    private TextView mExplain;          //说明
    private ImageView mUser_logo;       //用户logo
    private TextView mUser_name;        //用户名
    private TextView mCurrency;         //vip时间和货币数量
    private TextView mVip_agreement;    //vip协议
    private TextView mHint;             //提示
    private ListView mListView;         //显示list
    private MyGridView mGridView;
    private int mCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_vip_or_wallet);

        initView();
    }

    private void initView() {
        mReturn = (TextView) findViewById(R.id.my_return);
        mTitle = (TextView) findViewById(R.id.my_title);
        mExplain = (TextView) findViewById(R.id.explain);
        mUser_logo = (ImageView) findViewById(R.id.user_logo);
        mUser_name = (TextView) findViewById(R.id.user_name);
        mCurrency = (TextView) findViewById(R.id.currency);
        mHint = (TextView) findViewById(R.id.my_hint);
        mVip_agreement = (TextView) findViewById(R.id.vip_agreement);
        mListView = (ListView) findViewById(R.id.currency_list);
        mGridView = (MyGridView) findViewById(R.id.my_grid_view);

        //获取传入信息
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        mCode = data.getInt("code");
        String title = data.getString("title");

        //设置顶部标题
        mTitle.setText(title);

        //判断是VIP或钱包 替换list数据
        String[] currency = null;
        String[] coupon = null;
        String[] price = null;
        String[] text = null;
        int[] image = null;
        //我的钱包
        if(mCode == 1){
            currency = new String[]{"600自在币","3000自在币","5000自在币","9800自在币","19800自在币","38800自在币"};
            coupon = new String[]{"1阅读卷","7阅读卷","15阅读卷","38阅读卷","98阅读卷","238阅读卷"};
            price = new String[]{"$6","$30","$50","$98","$198","$388"};
            text = new String[]{"购买订阅章节","打赏作者","购买素材"};
            image = new int[]{R.drawable.integral_1,R.drawable.integral_1,R.drawable.integral_1};
            mExplain.setText("购买说明");
            mCurrency.setText("vip到期时间");
            mHint.setText("自在币用处");
         //我的vip
        }else if(mCode == 2){
            mExplain.setText("充值说明");
            mCurrency.setText("自在币6843个");
            mHint.setText("vip特权");
            mVip_agreement.setVisibility(View.VISIBLE);
            currency = new String[]{"自动续费","12个月","3个月","一个月"};
            coupon = new String[]{"首月10/月,续费9元/月","月费10/月","月费30/月","月费30/月"};
            price = new String[]{"$30","$120","$80","$30"};
            text = new String[]{"新人礼","等级标识","VIP漫画","消费折扣","过滤广告","昵称修改"};
            image = new int[]{R.drawable.integral_1,R.drawable.integral_1,R.drawable.integral_1,
                    R.drawable.integral_1,R.drawable.integral_1,R.drawable.integral_1};
        }

        MyWalletAdapter adapter = new MyWalletAdapter(this,currency,coupon,price);
        mListView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(mListView);

        HorizontalListViewAdapter horizoAdapter = new HorizontalListViewAdapter(this,text,image);
        mGridView.setAdapter(horizoAdapter);
    }
}
