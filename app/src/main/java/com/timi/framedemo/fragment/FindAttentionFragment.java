package com.timi.framedemo.fragment;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.tencent.tauth.Tencent;
import com.timi.framedemo.AppConstants;
import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.my.MyLoginPageActivity;
import com.timi.framedemo.adapter.FindItemAdapter;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;

/**
 * 发现 - 关注
 */
public class FindAttentionFragment extends BaseFragment implements View.OnClickListener{

    private ListView mListView;             //内容
    private RelativeLayout rl_layout;       //登录内容
    private Button mButton;                 //登录按钮

    public static Tencent mTencent;


    private ArrayList<Object> list;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.find_attention,null);
        rl_layout = (RelativeLayout) view.findViewById(R.id.r_find_attention_login);
        mListView = (ListView) view.findViewById(R.id.find_attention_content_list);
        mButton = (Button) view.findViewById(R.id.attention_login_but);

        mTencent = Tencent.createInstance(AppConstants.APP_ID, mContext);


        mButton.setOnClickListener(this);
        //判断登录 加载数据
        loginShow();
        return view;
    }

    //判断登录 加载数据
    private void loginShow() {


        if(!mTencent.isSessionValid()){
            //登录
            rl_layout.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            //加载数据
            showContent();
        }else {
            rl_layout.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
    }

    //加载数据
    private void showContent() {
        //获取数据
        list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            list.add(i);
        }
        //设置适配器
        FindItemAdapter adapter = new FindItemAdapter(mContext,list);
        //添加适配器
        mListView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(mListView);

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //每次进入都会调用 加载数据
        if(enter){
            loginShow();
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attention_login_but:      //跳转到登录
                Intent intent = new Intent(mContext,MyLoginPageActivity.class);
                startActivityForResult(intent,1);
                break;
        
            default:
                break;
        }
    }

    //返回时触发
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginShow();
    }
}
