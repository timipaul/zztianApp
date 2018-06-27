package com.timi.framedemo.activity.read;

import android.view.View;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.BackHandlerHelper;
import com.timi.framedemo.base.BaseFragment;

/**
 *  阅读 - 详情
 */
public class ReadDetailsFragment extends BaseFragment {

    private TextView mTextParticular;
    private TextView mTextLoadMore;

    private TextView mTextLoad;

    private Integer loadState = 0;


    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.read_particulars,null);

        mTextParticular = (TextView) view.findViewById(R.id.read_story_particular);
        mTextLoadMore = (TextView) view.findViewById(R.id.read_story_particular_more);

        mTextLoad = (TextView) view.findViewById(R.id.read_particular_load);

        String text = "\u3000\u3000你从来都不老师等级分类考试的对方介绍的考拉广东省爱爱了独守空房就爱上对方可几个撒饭店烧烤附近的刷卡了房间斯科拉法的斯科拉就纷纷离开就" +
                "你从来都不老师等级分类考试的对方介绍的考拉广东省爱爱了独守空房就爱上对方可几个撒饭店烧烤附近的刷卡了房间斯科拉法的斯科拉就纷纷离开就" +
                "分类考试的对方介绍的考拉广东省爱爱了独守空房就爱上对方可几个撒饭店烧烤附近的刷卡了房间斯科拉法的斯科拉就纷纷离" +
                "分类考试的对方介绍的考拉广东省爱爱了独守空房就爱上对方可几个撒饭店烧烤附近的刷卡了房间斯科拉法的斯科拉就纷纷离" +
                "分类考试的对方介绍的考拉广东省爱爱了独守空房就爱上对方可几个撒饭店烧烤附近的刷卡了房间斯科拉法的斯科拉就纷纷离" +
                "分类考试的对方介绍的考拉广东省爱爱了独守空房就爱上对方可几个撒饭店烧烤附近的刷卡了房间斯科拉法的斯科拉就纷纷离" +
                "分类考试的对方介绍的考拉广东省爱爱了独守空房就爱上对方可几个撒饭店烧烤附近的刷卡了房间斯科拉法的斯科拉就纷纷离";

        //长度：68
        mTextParticular.setText(text);
        mTextLoadMore.setText(text);

        //长度：63
        //mTextParticular.setText("\u3000\u3000你从来都不老师等级分类考试的对方介绍的考拉广东省爱爱了独守空房就爱上对方可几个撒饭店烧烤附近的刷卡了房间斯科拉法的斯科拉就");

        System.out.println("text的长度：" + mTextParticular.length());

        //判断长度有省略就显示下拉图标
        if(mTextParticular.length() > 63){
            mTextLoad.setVisibility(View.VISIBLE);
        }
        mTextLoad.setOnClickListener(new judgeLoadState());


        return view;
    }

    private class judgeLoadState implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //显示详细介绍
            if(loadState == 0){
                mTextLoadMore.setVisibility(View.VISIBLE);
                mTextParticular.setVisibility(View.GONE);
                mTextLoad.setText("收起");
                loadState = 1;
            }else{
                mTextParticular.setVisibility(View.VISIBLE);
                mTextLoadMore.setVisibility(View.GONE);
                mTextLoad.setText("详细");
                loadState = 0;
            }
        }
    }

}
