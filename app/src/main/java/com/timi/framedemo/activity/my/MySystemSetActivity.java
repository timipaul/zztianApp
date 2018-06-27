package com.timi.framedemo.activity.my;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tauth.Tencent;
import com.timi.framedemo.AppConstants;
import com.timi.framedemo.R;
import com.timi.framedemo.Utils.StatusBarUtil;

/**
 * 我的 - 设置列表
 */
public class MySystemSetActivity extends AppCompatActivity implements View.OnClickListener{

    private Button exitRegister;

    public static Tencent mTencent;

    private TextView txt;

    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_system_set);

        txt = (TextView) findViewById(R.id.txt);
        initView();

        actionBar = getSupportActionBar();
        //显示应用程序图标
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setLogo(R.drawable.ic_return);
        actionBar.setDisplayUseLogoEnabled(true);
        //将应用程序图标设置为可点击的按钮，并在图标上添加向左箭头
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("设置");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue));





    }

    private void initView() {

        mTencent = Tencent.createInstance(AppConstants.APP_ID, MySystemSetActivity.this);

        exitRegister = (Button) findViewById(R.id.my_exitRegister);

        exitRegister.setOnClickListener(this);

        StatusBarUtil.statusBarTintColor(this,getResources().getColor(R.color.blue));

        //显示或隐藏注销按钮
        showOrHideLogin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_exitRegister:  //注销登录
                userExitRegister();
                break;
        }
    }

    //用户注销
    private void userExitRegister() {
        mTencent.logout(this);
        Toast.makeText(MySystemSetActivity.this,"注销成功",Toast.LENGTH_SHORT).show();


        SharedPreferences share_get=null;
        share_get = this.getSharedPreferences("data", MODE_PRIVATE);
        //根据键获取数据，第二个参数为默认值，若没有指定的键，则返回默认值
        SharedPreferences.Editor editor = share_get.edit();
        editor.remove("userId");
        //editor.ctiaoyForResult(intent,1);
        editor.commit();
        //隐藏注销按钮
        showOrHideLogin();
    }

    //显示隐藏注销
    private void showOrHideLogin(){

        SharedPreferences share_get=null;
        share_get=this.getSharedPreferences("data", MODE_PRIVATE);
        //根据键获取数据，第二个参数为默认值，若没有指定的键，则返回默认值
        int userId = share_get.getInt("userId", 0);
        if(userId != 0){
            exitRegister.setVisibility(View.VISIBLE);
        }else{
            exitRegister.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    // 菜单项被单击后的回调方法
    public boolean onOptionsItemSelected(MenuItem mi)
    {
        if(mi.isCheckable())
        {
            // 勾选该菜单项
            mi.setChecked(true);  // ②
        }
        //判断单击的是哪个菜单项，并有针对性地作出响应
        switch (mi.getItemId())
        {
            case android.R.id.home:
                //调取返回
                onBackPressed();
                break;
            case R.id.font_10:
                txt.setTextSize(10 * 2);
                break;
            case R.id.font_12:
                txt.setTextSize(12 * 2);
                break;
            case R.id.font_14:
                txt.setTextSize(14 * 2);
                break;
            case R.id.font_16:
                txt.setTextSize(16 * 2);
                break;
            case R.id.font_18:
                txt.setTextSize(18 * 2);
                break;
            case R.id.red_font:
                txt.setTextColor(Color.RED);
                mi.setChecked(true);
                break;
            case R.id.green_font:
                txt.setTextColor(Color.GREEN);
                mi.setChecked(true);
                break;
            case R.id.blue_font:
                txt.setTextColor(Color.BLUE);
                mi.setChecked(true);
                break;
            case R.id.plain_item:
                Toast toast = Toast.makeText(MySystemSetActivity.this
                        , "您单击了普通菜单项" , Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        return true;
    }


}
