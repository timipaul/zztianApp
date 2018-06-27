package com.timi.framedemo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tauth.Tencent;
import com.timi.framedemo.AppConstants;
import com.timi.framedemo.R;
import com.timi.framedemo.Utils.CXAESUtil;
import com.timi.framedemo.Utils.GetHttpImg;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.activity.common.MenuGroupFragment;
import com.timi.framedemo.activity.home.BrowseHistory;
import com.timi.framedemo.activity.home.CreateUpdateCartoon;
import com.timi.framedemo.activity.my.IntegralExchangeActivity;
import com.timi.framedemo.activity.my.MyLoginPageActivity;
import com.timi.framedemo.activity.my.MySystemSetActivity;
import com.timi.framedemo.activity.my.SignInDebrisAdapter;
import com.timi.framedemo.activity.my.UserMessageSetActivity;
import com.timi.framedemo.activity.my.WalletOrVip;
import com.timi.framedemo.base.BaseFragment;
import com.timi.framedemo.bean.Amount;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 大分类 我的账号
 */
public class MyAccountFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = MyAccountFragment.class.getSimpleName();

    private TextView tvReadMoney = null;
    private TextView tvInteagral = null;
    private TextView tvSpaceMoney = null;
    public static Tencent mTencent;

    private TextView mSign;         //签到
    private TextView mInformation;  //消息
    private TextView mUserName;     //用户名
    private TextView mOrange;       //会员等级
    private ImageView mUser_Image;   //用户头像

    //跳转
    private TextView mVip;          //vip
    private TextView mWallet;       //我的钱包
    private TextView mStore;        //商城
    private TextView mIntegral;     //积分兑换
    private TextView mAttention;    //我的关注
    private TextView mMyPost;       //我的帖子
    private TextView mMyCircle;     //我的圈子
    private TextView mCollect;      //收藏
    private TextView mWorks;        //参与作品
    private TextView mBrowse_history;      //浏览历史
    private TextView mSet;          //设置
    private TextView my_help;       //帮助和反馈
    /**未登录logo */
    private ImageView mLoginBut;
    private TextView mTextBut;
    private TextView mTextBut2;

    private RelativeLayout mLayout_my_login;
    private FrameLayout mLayout_login_succeed;

    private ListView mListView;
    private boolean loginState;
    private Amount mAmount;
    private Handler handler = new Handler();
    private String userId;           //用户Id
    private String SignText;        //签到提示
    //弹出签到框
    private Dialog bottomDialog;


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.my_account,null);
        tvReadMoney = (TextView)view.findViewById(R.id.myReadMoney);
        tvInteagral = (TextView)view.findViewById(R.id.myIntegral);
        tvSpaceMoney = (TextView)view.findViewById(R.id.mySpaceMoney);
        mUser_Image = (ImageView)view.findViewById(R.id.user_background);

        mSign = (TextView) view.findViewById(R.id.my_sign);
        mInformation = (TextView) view.findViewById(R.id.my_information);
        mUserName = (TextView) view.findViewById(R.id.my_user_name);
        mOrange = (TextView) view.findViewById(R.id.my_orange);
        mVip = (TextView) view.findViewById(R.id.my_VIP);
        mWallet = (TextView) view.findViewById(R.id.my_wallet);
        mStore = (TextView) view.findViewById(R.id.my_store);
        mIntegral = (TextView) view.findViewById(R.id.my_integral);
        mAttention = (TextView) view.findViewById(R.id.my_attention);
        mMyPost = (TextView) view.findViewById(R.id.my_post);
        mMyCircle = (TextView) view.findViewById(R.id.my_circle);
        mCollect = (TextView) view.findViewById(R.id.my_collect);
        mWorks = (TextView) view.findViewById(R.id.participation_works);
        mBrowse_history = (TextView) view.findViewById(R.id.browse_history);
        mSet = (TextView) view.findViewById(R.id.my_set);
        my_help = (TextView) view.findViewById(R.id.my_help);
        mLayout_my_login = (RelativeLayout) view.findViewById(R.id.my_login);
        mLayout_login_succeed = (FrameLayout) view.findViewById(R.id.my_login_succeed);


        //点击跳转登录页
        mLoginBut = (ImageView) view.findViewById(R.id.my_login_button);
        mTextBut = (TextView) view.findViewById(R.id.my_login_text);
        mTextBut2 = (TextView) view.findViewById(R.id.my_login_hint);
        mUser_Image.setOnClickListener(this);

        mSign.setOnClickListener(this);
        mInformation.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mOrange.setOnClickListener(this);
        mVip.setOnClickListener(this);
        mWallet.setOnClickListener(this);
        mStore.setOnClickListener(this);
        mIntegral.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        mMyPost.setOnClickListener(this);
        mMyCircle.setOnClickListener(this);
        mCollect.setOnClickListener(this);
        mWorks.setOnClickListener(this);
        mBrowse_history.setOnClickListener(this);
        mSet.setOnClickListener(this);
        my_help.setOnClickListener(this);
        mLoginBut.setOnClickListener(new skipTologin());
        mTextBut.setOnClickListener(new skipTologin());
        mTextBut2.setOnClickListener(new skipTologin());



        return view;
    }


    @Override
    protected void initData() {

        tvReadMoney.setText("0\n阅读券");
        tvInteagral.setText("0\n积分");
        tvSpaceMoney.setText("0\n自在币");

        System.out.println("获取数据");
        SharedPreferences share_get=null;
        share_get=mContext.getSharedPreferences("data", mContext.MODE_PRIVATE);
        //根据键获取数据，第二个参数为默认值，若没有指定的键，则返回默认值
        final int userId = share_get.getInt("userId",0);
        System.out.println("userId: --- " + userId);
        if(userId != 0){

            mLayout_my_login.setVisibility(View.GONE);
            mLayout_login_succeed.setVisibility(View.VISIBLE);

            mAmount = new Amount();
            new Thread(){
                @Override
                public void run() {
                    try {
                        HttpUtils httpUtils = new HttpUtils();
                        String id = CXAESUtil.encrypt2Java(AppConstants.CXAES,userId+"");
                        RequestBody formBody = new FormBody.Builder()
                                .add("userId", id)
                                .build();
                        String url = "/login/usersInfo";
                        String result = httpUtils.OkhttpPost(url,formBody);
                        JSONArray jsonArray = JSONArray.fromObject(result);
                        JSONObject json = jsonArray.getJSONObject(0);
                        mAmount.setId(json.getInt("id"));
                        mAmount.setHeadimg(json.getString("headimg"));
                        mAmount.setIntegralNum(json.getInt("integralNum"));
                        mAmount.setNickName(json.getString("nickName"));
                        mAmount.setRollNum(json.getInt("rollNum"));
                        mAmount.setUserType(json.getString("userType"));
                        mAmount.setZzbNum(json.getInt("zzbNum"));
                        mAmount.setIfsign(json.getInt("ifsign"));
                        mAmount.setSignCount(json.getInt("signCount"));
                        handler.post(runnableUi);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    Runnable runnableUi = new Runnable(){
        @Override
        public void run() {

            System.out.println("更新数据");
            //更新信息
            tvReadMoney.setText(mAmount.getRollNum() + "\n阅读券");
            tvInteagral.setText(mAmount.getIntegralNum() + "\n积分");
            tvSpaceMoney.setText(mAmount.getZzbNum() + "\n自在币");
            GetHttpImg.setUserImg(mUser_Image, mAmount.getHeadimg());
            mUserName.setText(mAmount.getNickName());
            //判断会员 1是普通用户 2是会员
            if(mAmount.getUserType().equals(2+"")){
                mOrange.setText("VIP");
            }else{
                mOrange.setText("");
            }
            //判断是否签到
            if(mAmount.getIfsign() == 0){
                mSign.setText("签到");
                //签到
                signInView();
            }else{
                mSign.setText("已签到");
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        Bundle data = null;
        switch (v.getId()){
            case R.id.my_set: //跳转到设置软件设置
                intent = new Intent(mContext,MySystemSetActivity.class);
                break;
            case R.id.participation_works: //跳转到参与作品
                intent = new Intent(mContext, CreateUpdateCartoon.class);
                data = new Bundle();
                data.putInt("code",2);
                data.putString("name","参与作品");
                intent.putExtra("data",data);
                break;
            case R.id.user_background:
                //跳转到个人首页
                intent = new Intent(mContext,UserMessageSetActivity.class);
                break;
            case R.id.my_integral:
                //积分兑换
                intent = new Intent(mContext,IntegralExchangeActivity.class);
                data = new Bundle();
                data.putInt("code",1);
                data.putString("name","积分兑换");
                intent.putExtra("data",data);
                break;
            case R.id.my_store:
                //自在商城
                intent = new Intent(mContext,IntegralExchangeActivity.class);
                data = new Bundle();
                data.putInt("code",2);
                data.putString("name","自在商城");
                intent.putExtra("data",data);
                break;
            case R.id.my_collect:
                //我的收藏
                intent = new Intent(mContext, CreateUpdateCartoon.class);
                data = new Bundle();
                data.putInt("code",3);
                data.putString("name","我的收藏");
                intent.putExtra("data",data);
                break;
            case R.id.browse_history:
                //浏览历史
                intent = new Intent(mContext,BrowseHistory.class);
                break;
            case R.id.my_attention:
                //我的关注
                intent = new Intent(mContext, MenuGroupFragment.class);
                data = new Bundle();
                data.putInt("code",1);
                data.putString("title","我的关注");
                data.putString("radioOne","作者");
                data.putString("radioTwo","用户");
                intent.putExtra("data",data);
                break;
            case R.id.my_post:
                //我的帖子
                intent = new Intent(mContext, MenuGroupFragment.class);
                data = new Bundle();
                data.putInt("code",2);
                data.putString("title","我的帖子");
                data.putString("radioOne","世界");
                data.putString("radioTwo","圈子");
                intent.putExtra("data",data);
                break;
            case R.id.my_circle:
                //我的圈子
                intent = new Intent(mContext, MenuGroupFragment.class);
                data = new Bundle();
                data.putInt("code",3);
                data.putString("title","我的圈子");
                data.putString("radioOne","已浏览");
                data.putString("radioTwo","已加入");
                intent.putExtra("data",data);
                break;
            case R.id.my_wallet:
                intent = new Intent(mContext, WalletOrVip.class);
                data = new Bundle();
                data.putInt("code",1);
                data.putString("title","我的钱包");
                intent.putExtra("data",data);
                break;
            case R.id.my_VIP:
                intent = new Intent(mContext, WalletOrVip.class);
                data = new Bundle();
                data.putInt("code",2);
                data.putString("title","我的VIP");
                intent.putExtra("data",data);
                break;
            case R.id.my_sign:
                //签到
                isSignIn();
                break;
        }

        if(intent != null){
            startActivityForResult(intent,1);
        }

    }

    /**跳转到登录页*/
    private class skipTologin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext,MyLoginPageActivity.class);
            startActivityForResult(intent,1);
            //startActivity(intent);
        }
    }


    //返回时触发
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //返回1是qq快捷登录
        //2是手机号码登录
        System.out.println("登录返回触发：" + resultCode);
        if(resultCode == 1){
            //qq登录
            mTencent = Tencent.createInstance(AppConstants.APP_ID, mContext);
            loginState = mTencent.isSessionValid();
        }else if(resultCode == 2){
            //手机登录
            System.out.println("成功返回手机登录信息  " + data.getStringExtra("MESSAGE"));
            Toast.makeText(mContext,data.getStringExtra("MESSAGE"),Toast.LENGTH_SHORT).show();
            loginState = true;
        }else{
            return;
        }


        if(loginState){
            //查询相应的个人信息
            initData();
            mLayout_my_login.setVisibility(View.GONE);
            mLayout_login_succeed.setVisibility(View.VISIBLE);
        }else{
            mLayout_my_login.setVisibility(View.VISIBLE);
            mLayout_login_succeed.setVisibility(View.GONE);
        }
    }


    //签到
    private void signInView() {


        bottomDialog = new Dialog(mContext, R.style.BottomDialog);

        View contentView =  View.inflate(mContext, R.layout.my_sign_in_debris,null);
        mListView = (ListView) contentView.findViewById(R.id.sign_in_list);
        final String[] integral = {"1","5","10","15","20","25","礼包"};

        SignInDebrisAdapter adapter = new SignInDebrisAdapter(mContext,7,integral,mAmount.getSignCount());

        mListView.setAdapter(adapter);

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        adapter.setOnItemClickListener(new SignInDebrisAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int tag) {
                new Thread(){
                    @Override
                    public void run() {
                        HttpUtils httpUtils = new HttpUtils();
                        try {
                            userId = CXAESUtil.encrypt2Java(AppConstants.CXAES, String.valueOf(mAmount.getId()));
                            RequestBody formBody = new FormBody.Builder()
                                    .add("userId", userId)
                                    .build();
                            String url = "/record/userSign";
                            Map result = httpUtils.OkhttpPostList(url,formBody);

                            SignText = (String) result.get("status");
                            System.out.println("返回信息：" + result.get(1));
                            //JSONArray jsonArray = JSONArray.fromObject(result);
                             // SignText
                            handler.post(runnableSignIn);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    Runnable runnableSignIn = new Runnable(){
        @Override
        public void run() {
            //弹出签到成功提示
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage(SignText)
                    .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            initData();
                            //隐藏弹出框
                            bottomDialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    };

    //签到
    private void isSignIn(){
        //判断是否签到，未签到可点击签到
        if(mAmount.getIfsign() == 0){
            signInView();
        }
    }

}
