package com.timi.framedemo.activity.read;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.BackHandlerHelper;
import com.timi.framedemo.Utils.GetHttpImg;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.bean.Cartoon;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 漫画详情介绍页
 */
public class ReadDetails extends FragmentActivity implements View.OnClickListener{
    private RadioGroup mRg_main;
    private TextView mTv_readReturn;
    private List<BaseFragment> mBaseFragment;
    private String mBookId;     //接收的id
    private TextView mbookName;
    private ImageView mBookLogo;
    private TextView mTextStyle;
    private LinearLayout mLayout_type;
    private TextView readStart;
    private Cartoon car = new Cartoon();


    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        mBookId = bundle.getString("id");
        Log.i("获取到的请求id值为",mBookId + "");

        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    private void initView() {
        setContentView(R.layout.home_read_details);
        mRg_main = (RadioGroup) findViewById(R.id.rg_Home_read_details);
        mTv_readReturn = (TextView)findViewById(R.id.read_returnFragment);

        readStart = (TextView) findViewById(R.id.start_read);
        mbookName = (TextView) findViewById(R.id.read_cartoon_name);
        mBookLogo = (ImageView) findViewById(R.id.read_details_logo);
        mLayout_type = (LinearLayout) findViewById(R.id.layout_read_details_type);
        mLayout_type.removeAllViews();

        handler = new Handler();

        readStart.setOnClickListener(this);
        initData();

    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.home_read_details);

    }


    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.home_read_details://详情
                    position = 0;
                    break;
                case R.id.home_read_catalog://目录
                    position = 1;
                    break;
                case R.id.home_read_comment://评论
                    position = 2;
                    break;
                default:
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFrament(mContent,to);

        }
    }

    /**
     *
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to 马上要切换到的Fragment，一会要显示
     */

    private void switchFrament(Fragment from,Fragment to) {
        if(from != to){
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //添加to
                if(to != null){
                    ft.add(R.id.fl_home_read_details,to).commit();
                }
            }else{
                //to已经被添加
                // from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }

    }

    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new ReadDetailsFragment());//详情Fragment
        mBaseFragment.add(new ReadCatalogFragment());//目录Fragment
        mBaseFragment.add(new ReadCommentFragment());//评论Fragment
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_read:
                //跳转到阅读方法
                skipViewToRead();
                break;
            case R.id.read_returnFragment:
                //返回
                onBackPressed();
                break;
            default:
                break;
        }
    }

    //跳转到阅读界面
    private void skipViewToRead() {
        System.out.println("阅读...");
        Intent intent = new Intent(this,ReadContentControl.class);
        startActivity(intent);
    }

    protected void initData() {
        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("id", mBookId)
                        .build();
                try {
                    String url = "/cartoon/particulars";

                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONObject json = JSONObject.fromObject(result);

                    car.setBookName(json.getString("bookName"));
                    car.setCover(json.getString("cover"));
                    car.setBookType(json.getString("bookType"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(runnableUi);
            }
        }.start();
    }


    Runnable runnableUi = new Runnable(){
        @Override
        public void run() {
            mbookName.setText(car.getBookName());
            GetHttpImg.setUserImg(mBookLogo, car.getCover());
            mTextStyle = new TextView(ReadDetails.this);
            mTextStyle.setText(car.getBookType().replace(","," "));
            setTextStyle(mTextStyle);
            mLayout_type.addView(mTextStyle);

        }
    };


    public void setTextStyle(TextView tv) {
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.setMargins(5,0,5,0);
        tv.setLayoutParams(param);
    }

}

