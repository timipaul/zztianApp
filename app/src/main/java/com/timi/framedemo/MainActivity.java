package com.timi.framedemo;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.Tencent;
import com.timi.framedemo.Utils.BackHandlerHelper;
import com.timi.framedemo.Utils.StatusBarUtil;
import com.timi.framedemo.Utils.SymmetricEncoder1;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.fragment.FindFragment;
import com.timi.framedemo.fragment.HomeFragment;
import com.timi.framedemo.fragment.MyAccountFragment;
import com.timi.framedemo.qqutile.BaseUIListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;
    public static Tencent mTencent;
    private UserInfo mInfo;

    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;





    private void initView() {
        setContentView(R.layout.activity_main);
        mRg_main = (RadioGroup) findViewById(R.id.rg_main);

        //修改顶部样式
        StatusBarUtil.statusBarTintColor(this,getResources().getColor(R.color.pink));


        String kk = null;
        try {
            kk = SymmetricEncoder1.aesEncryptString("颠鸾倒凤","ZIZAITIAN@666666");
            String jj = SymmetricEncoder1.aesDecryptString(kk,"ZIZAITIAN@666666");

            System.out.println(jj);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        String kk = null;
        try {
            kk = SymmetricEncoder1.aesEncryptHexStr("xiaoming9999","ABCDEFGHIJKLMNOP");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------------" + kk);

        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
        //自动登录
        selfMotionLogin();

    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new HomeFragment());//众创空间Fragment
        //mBaseFragment.add(new StoreFragment());//编辑器Fragment
        mBaseFragment.add(new FindFragment());//发现Fragment
        mBaseFragment.add(new MyAccountFragment());//我的账号Fragment
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showMyDialog(); //点击BACK弹出对话框
        }
        return false;
    }

    private void showMyDialog() {
        // 创建退出对话框
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        // 设置对话框标题
        isExit.setTitle("確定要退吗？");
        // 设置对话框消息
        //isExit.setMessage("确定要退出吗");
        // 添加选择按钮并注册监听
        isExit.setButton("确定",listener);
        isExit.setButton2("取消",listener);
        // 显示对话框
        isExit.show();
    }
    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    onBackPressed();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };


    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_common_frame);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_return:
                //通用返回
                onBackPressed();
                break;
        }
    }


    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_common_frame://众创空间
                    position = 0;
                    break;
                /*case R.id.rb_thirdparty://编辑器
                    position = 1;
                    break;*/
                case R.id.rb_custom://发现
                    position = 1;
                    break;
                case R.id.rb_other://我的账号
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
                    ft.add(R.id.fl_content,to).commit();
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


    /** 自动登录 **/
    private void selfMotionLogin() {

        /*
            登录判断
            从缓存中读取数据
            找到不为空的数据就进行登录
         */

        SharedPreferences share_get=null;
        share_get=this.getSharedPreferences("data", MODE_PRIVATE);
        //根据键获取数据，第二个参数为默认值，若没有指定的键，则返回默认值
        String qq=share_get.getString("qqLoginNumber", null);
        String phone = share_get.getString("phoneLoginNumber",null);

        if(qq != null && qq.length() > 0){
            mTencent = Tencent.createInstance(AppConstants.APP_ID, MainActivity.this);
            JSONObject jsonObject = null;
            boolean isValid = mTencent.checkSessionValid(AppConstants.APP_ID);
            if(!isValid) {

                return;
            } else {
                jsonObject = mTencent.loadSession(AppConstants.APP_ID);
                mTencent.initSessionCache(jsonObject);
            }
            mInfo = new UserInfo(this, mTencent.getQQToken());
            onClickUserInfo();
        }else if(phone != null && phone.length() > 0){
            //手机号码登录

        }else{
            System.out.println("没有登录");
        }




    }

    private void onClickUserInfo() {
        if (ready(this)) {
            mInfo.getUserInfo(new BaseUIListener(this,"get_simple_userinfo"));
            Util.showProgressDialog(this, null, null);
        }
    }

    public static boolean ready(Context context) {
        if (mTencent == null) {
            return false;
        }
        boolean ready = mTencent.isSessionValid() && mTencent.getQQToken().getOpenId() != null;
        if (!ready) {
            Toast.makeText(context, "login and get openId first, please!", Toast.LENGTH_SHORT).show();
        }
        return ready;
    }


    /*  测试的Activity
    *   ReadRankingActivity
    * */
}
