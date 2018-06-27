package com.timi.framedemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.activity.home.CreateUpdateCartoon;
import com.timi.framedemo.activity.home.CreationSchemaActivity;
import com.timi.framedemo.base.BaseFragment;

/**
    众创空间 -- 创建
 */
public class HomeCreationFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = HomeCreationFragment.class.getSimpleName();

    private TextView mCreateCartoonBut;
    private TextView mCreateFictionBut;
    private TextView mTextUpdateBut;



    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.home_creation,null);
        mCreateCartoonBut = (TextView) view.findViewById(R.id.home_create_cartoon);
        mCreateFictionBut = (TextView) view.findViewById(R.id.home_create_fiction);
        mTextUpdateBut = (TextView) view.findViewById(R.id.home_create_update);



        mCreateCartoonBut.setOnClickListener(this);
        mCreateFictionBut.setOnClickListener(this);
        mTextUpdateBut.setOnClickListener(this);



        /*点击其它位置隐藏键盘    异常 无效果*/
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(getActivity().getCurrentFocus()!=null && getActivity().getCurrentFocus().getWindowToken()!=null){
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_create_cartoon:      //创建漫画
                toNewCreateView(1);
                break;
            case R.id.home_create_fiction:      //创建剧本
                toNewCreateView(2);
                break;
            case R.id.home_create_update:
                Intent intent = new Intent(mContext, CreateUpdateCartoon.class);
                Bundle data = new Bundle();
                data.putInt("code",1);
                data.putString("name","更新");
                intent.putExtra("data",data);
                startActivity(intent);
                break;


        }
    }

    //跳转到创建漫画或创建小说
    private void toNewCreateView(int code) {
        Intent intent = new Intent(mContext, CreationSchemaActivity.class);
        Bundle data = new Bundle();
        data.putInt("code",code);
        intent.putExtra("data",data);
        startActivity(intent);
    }


}
