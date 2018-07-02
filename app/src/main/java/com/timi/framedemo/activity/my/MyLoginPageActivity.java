package com.timi.framedemo.activity.my;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.timi.framedemo.AppConstants;
import com.timi.framedemo.R;
import com.timi.framedemo.Util;
import com.timi.framedemo.Utils.CXAESUtil;
import com.timi.framedemo.Utils.SharedPreferencesUtils;
import com.timi.framedemo.Utils.Utility;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录页面
 * 有第三方qq登录
 */
public class MyLoginPageActivity extends FragmentActivity implements View.OnClickListener{

    /**登录方式切换*/
    private TextView mLogin_switchover;
    /**顶部提示*/
    private TextView mTitle;
    /**手机号码登录视图*/
    private LinearLayout layout_phone;
    /**账号密码登录视图*/
    private LinearLayout layout_account;
    private TextView mlogin_return;
    /**手机号码*/
    private EditText mEd_phone_number;
    /**手机验证码*/
    private EditText mEd_verification_code;
    /**账号*/
    private EditText mEd_account;
    /**密码*/
    private EditText mEd_account_pwd;
    /**手机登录提交按钮*/
    private Button mButton_phone;
    /**账号密码登录提交按钮*/
    private Button mButton_account;
    /** qq第三方登录 */
    private Button mNewLoginButton;
    /** 获取验证码 */
    private Button mAuth_code;

    public static Tencent mTencent;

    public static String mAppid;
    private EditText mEtAppid = null;
    private UserInfo mInfo;
    private static boolean isServerSideLogin = false;

    private static final String TAG = MyLoginPageActivity.class.getName();
    /** 登录方式 0表示电话登录 1表示账号登录 */
    private int registerState = 0;
    private String sessionId;
    private Handler handler=null;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_login);

        mLogin_switchover = (TextView) findViewById(R.id.login_switchover);
        mTitle = (TextView) findViewById(R.id.my_login_title);
        layout_phone = (LinearLayout) findViewById(R.id.ll_login_phone);
        layout_account = (LinearLayout) findViewById(R.id.ll_login_account);
        mlogin_return = (TextView) findViewById(R.id.my_login_return);
        mEd_phone_number = (EditText) findViewById(R.id.phone_number);
        mEd_verification_code = (EditText) findViewById(R.id.login_verification_code);
        mEd_account = (EditText) findViewById(R.id.my_login_account);
        mEd_account_pwd = (EditText) findViewById(R.id.login_password);
        mButton_phone = (Button) findViewById(R.id.login_phone_submit);
        mButton_account = (Button) findViewById(R.id.login_account_submit);
        mNewLoginButton = (Button) findViewById(R.id.new_login_btn);
        mAuth_code = (Button) findViewById(R.id.get_auth_code);



        mLogin_switchover.setOnClickListener(this);
        mlogin_return.setOnClickListener(this);
        mButton_phone.setOnClickListener(this);
        mButton_account.setOnClickListener(this);
        mNewLoginButton.setOnClickListener(this);
        mAuth_code.setOnClickListener(this);

        handler=new Handler();

        initViews();

        mEd_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer phoneLength = mEd_verification_code.getText().length();

                //判断短信验证码
                if(s.length() > 0){
                    System.out.println("进来了 招行" );
                    mAuth_code.setEnabled(true);
                }else{
                    mAuth_code.setEnabled(false);
                }
                //判断电话号码长度不为空和验证码不为空时显示提交按钮
                if(phoneLength > 0 && s.length() > 0){
                    mButton_phone.setEnabled(true);

                }else{

                }
            }
        });

        mEd_verification_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer phoneLength = mEd_phone_number.getText().length();
                //判断电话号码长度不为空和验证码不为空时显示提交按钮
                if(phoneLength > 0 && s.length() > 0){
                    mButton_phone.setEnabled(true);
                }else{
                    mButton_phone.setEnabled(false);
                }
            }
        });

        mEd_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer phoneLength = mEd_account_pwd.getText().length();
                //判断电话号码长度不为空和验证码不为空时显示提交按钮
                if(phoneLength > 0 && s.length() > 0){
                    mButton_account.setEnabled(true);
                }else{
                    mButton_account.setEnabled(false);
                }
            }
        });

        mEd_account_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer phoneLength = mEd_account.getText().length();
                //判断电话号码长度不为空和验证码不为空时显示提交按钮
                if(phoneLength > 0 && s.length() > 0){
                    mButton_account.setEnabled(true);
                }else{
                    mButton_account.setEnabled(false);
                }
            }
        });
    }

    private void initViews() {

        if (TextUtils.isEmpty(mAppid)) {
            mTencent = Tencent.createInstance(AppConstants.APP_ID, MyLoginPageActivity.this);

        } else {
            if (mTencent == null) {
                mTencent = Tencent.createInstance(mAppid, this);
            }
        }

        if (mTencent != null && mTencent.isSessionValid()) {
            if (isServerSideLogin) {
                mNewLoginButton.setTextColor(Color.BLUE);
                mNewLoginButton.setText("登录");

            } else {
                mNewLoginButton.setTextColor(Color.RED);
                mNewLoginButton.setText("退出帐号");

            }
        } else {
            mNewLoginButton.setTextColor(Color.BLUE);
            mNewLoginButton.setText("登录");

        }
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Animation shake = AnimationUtils.loadAnimation(context,
                R.anim.shake);
        switch (v.getId()) {
            case R.id.login_switchover:  //切换登录方式
                changeLoginState();
                break;
            case R.id.my_login_return:  //返回
                onBackPressed();
                break;
            case R.id.login_phone_submit: //电话登录点击
                phoneLogin();
                break;
            case R.id.login_account_submit: //账号密码登录被点击
                System.out.println("账号密码登录被点击....");
                break;
            case R.id.new_login_btn:    //qq第三方登录
                onClickLogin();
                v.startAnimation(shake);
                return;
            case R.id.get_auth_code:    //获取验证码
                getAuthCode();
            default:
                break;
        }
    }

    //显示隐藏登录方式
    private void changeLoginState() {
        if(registerState == 0){
            mTitle.setText("手机号快捷登录");
            mLogin_switchover.setText("账号密码登录");
            layout_phone.setVisibility(View.VISIBLE);
            layout_account.setVisibility(View.GONE);
        }else if(registerState == 1){
            mTitle.setText("账号密码登录");
            mLogin_switchover.setText("手机号快捷登录");
            layout_phone.setVisibility(View.GONE);
            layout_account.setVisibility(View.VISIBLE);
        }

        registerState = ++registerState > 1 ? 0 : 1;
    }

    OkHttpClient client = new OkHttpClient();
    //获取验证码
    private void getAuthCode(){
        //验证电话号码是否正确
        final String phone = mEd_phone_number.getText().toString();
        boolean result = Utility.isMobileNO(phone);
        if(!result){
            Toast.makeText(this,"号码不正确",Toast.LENGTH_SHORT).show();
        }else{

            //倒计时60秒
            new CountDownTimer(1000*60,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mAuth_code.setEnabled(false);
                    mAuth_code.setText(millisUntilFinished/1000 + "秒");
                }

                @Override
                public void onFinish() {
                    mAuth_code.setEnabled(true);
                    mAuth_code.setText("重新获取验证码");
                }
            }.start();

            //获取手机号码发送给后台
            new Thread(){
                @Override
                public void run() {
                    RequestBody formBody = new FormBody.Builder()
                            .add("phone", phone)
                            .build();
                    try {
                        String url = "http://192.168.0.165:8888/login/sendMsg";
                        Request request = new Request.Builder()
                                .url(url)
                                .post(formBody)
                                .build();
                        Response response = client.newCall(request).execute();
                        String result = null;
                        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(response.body().string());
                        String status = jsonObject.getString("status");
                        System.out.println("请求状态信息" + status);
                        //String sjson = jsonObject.getString("result");
                        //解密
                        //result = CXAESUtil.decrypt(AppConstants.CXAES, sjson);
                        //System.out.println("短信请求结果" + result);
                        Headers headers = response.headers();
                        List<String> cookies = headers.values("Set-Cookie");
                        String session = cookies.get(0);
                        sessionId = session.substring(0, session.indexOf(";"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }


    private void phoneLogin(){
        final String phone = mEd_phone_number.getText().toString();
        final String code = mEd_verification_code.getText().toString();
        //验证登录或注册
        new Thread(){
            @Override
            public void run() {
                RequestBody formBody = new FormBody.Builder()
                        .add("phone", phone)
                        .add("checkCode", code)
                        .build();
                try {
                    String url = "http://192.168.0.165:8888/login/loginApp";
                    System.out.println("注册时的sessionId" + sessionId);
                    Request request = new Request.Builder()
                            .addHeader("cookie",sessionId)
                            .url(url)
                            .post(formBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    String result;

                    net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(response.body().string());
                    System.out.println("jsonObject:" + jsonObject);
                    int code = jsonObject.getInt("code");
                    String status = jsonObject.getString("result");
                    //解密
                    result = CXAESUtil.decrypt(AppConstants.CXAES, status);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    net.sf.json.JSONObject json = jsonArray.getJSONObject(0);
                    int userId = json.getInt("id");
                    if(code == 100){
                        //登录注册成功 返回
                        //保存登录信息到手机，方便下次自动登录
                        //saveData(phone,userId);

                        SharedPreferencesUtils.setParam(MyLoginPageActivity.this,"phoneLoginNumber",phone);
                        SharedPreferencesUtils.setParam(MyLoginPageActivity.this,"userId",userId);
                        //设置返回信息
                        Intent data = new Intent();
                        data.putExtra("MESSAGE",code);
                        setResult(2,data);
                        handler.post(runnable);
                    }else if(code == 200){

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //返回刷新
            onBackPressed();
        }
    };

    public void saveData(String phone,int userId){
        SharedPreferences share=null;
        //得到SharePreferences对象，第一个参数：指定文件名，第二个参数：操作模式
        share=this.getSharedPreferences("data", MODE_MULTI_PROCESS);
        //得到SharedPreferen.Edit对象
        SharedPreferences.Editor edit=share.edit();
        //用edit存储数据
        edit.putString("phoneLoginNumber", phone);
        edit.putInt("userId", userId);
        //提交数据，存储完成
        edit.commit();
    }



    private void onClickLogin() {
        System.out.println("登录状态：" + mTencent.isSessionValid());
        if (!mTencent.isSessionValid()) {

            mTencent.login(this, "all", loginListener);
            isServerSideLogin = false;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);
            updateUserInfo();
            updateLoginButton();
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
            updateLoginButton();
        }
    };

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread(){

                        @Override
                        public void run() {
                            JSONObject json = (JSONObject)response;
                            if(json.has("figureurl")){
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                } catch (JSONException e) {

                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }

                    }.start();
                }

                @Override
                public void onCancel() {
                    System.out.println("这里是？？？");
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        } else {

        }
    }

    private void updateLoginButton() {
        if (mTencent != null && mTencent.isSessionValid()) {
            if (isServerSideLogin) {
                mNewLoginButton.setTextColor(Color.BLUE);
                mNewLoginButton.setText("登录");

            } else {
                mNewLoginButton.setTextColor(Color.RED);
                mNewLoginButton.setText("退出帐号");

            }
        } else {
            mNewLoginButton.setTextColor(Color.BLUE);
            mNewLoginButton.setText("登录");

        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(MyLoginPageActivity.this, "返回为空", "登录失败");
                return;
            }

            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(MyLoginPageActivity.this, "返回为空", "登录失败");
                return;
            }
            //Util.showResultDialog(MyLoginPageActivity.this, response.toString(), "登录成功");
            System.out.println("" + response.toString());
            setResult(1,null);
            onBackPressed();
            try {
                System.out.println("登录成功：" + jsonResponse.getString("openid"));

                //存储登录数据
                SharedPreferences share=null;
                //得到SharePreferences对象，第一个参数：指定文件名，第二个参数：操作模式
                share=getSharedPreferences("data", MODE_PRIVATE);
                //得到SharedPreferen.Edit对象
                SharedPreferences.Editor edit=share.edit();
                //用edit存储数据
                edit.putString("loginUser", jsonResponse.getString("openid"));
                edit.putInt("userId", 23);
                //提交数据，存储完成
                edit.commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // 有奖分享处理
            //handlePrizeShare();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(MyLoginPageActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(MyLoginPageActivity.this, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        //mUserInfo.setVisibility(android.view.View.VISIBLE);
                        //mUserInfo.setText(response.getString("nickname"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else if(msg.what == 1){
                Bitmap bitmap = (Bitmap)msg.obj;
                //mUserLogo.setImageBitmap(bitmap);
                //mUserLogo.setVisibility(android.view.View.VISIBLE);
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
