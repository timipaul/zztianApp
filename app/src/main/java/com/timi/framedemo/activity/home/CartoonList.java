package com.timi.framedemo.activity.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.GetHttpImg;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.read.ReadDetails;
import com.timi.framedemo.base.BaseFragment;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.wolfspider.autowraplinelayout.AutoWrapLineLayout;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 众创空间 -- 阅读 -- 漫画列表
 */
public class CartoonList extends BaseFragment {

    private List<String> name;
    private List<String> bookId;
    private ArrayList<List> type;
    private List<String> image;
    private AutoWrapLineLayout mAutoWrapLineLayout;
    private LinearLayout ll_layout;
    private ImageView mImageView;
    private TextView mTextStyle;
    private Handler handler=null;


    @Override
    protected View initView() {
        final View view = View.inflate(mContext, R.layout.fragment_common_autowrap_layout, null);
        mAutoWrapLineLayout = (AutoWrapLineLayout) view.findViewById(R.id.awl_layout);
        mAutoWrapLineLayout.setFillMode(AutoWrapLineLayout.MODE_WRAP_CONTENT);
        mAutoWrapLineLayout.removeAllViews();
        handler=new Handler();
        return view;
    }

    @Override
    protected void initData() {
        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("pageNum", "1")
                        .add("pageSize","10")
                        .build();
                try {
                    String url = "/cartoon/cartoonlist";

                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    name = new ArrayList<String>();
                    bookId = new ArrayList<String>();
                    image = new ArrayList<String>();
                    type = new ArrayList<>();

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        List<String> list = new ArrayList<String>();
                        list.add(jsonObject2.getString("bookType"));
                        type.add(list);
                        name.add(jsonObject2.getString("bookName"));
                        image.add(jsonObject2.getString("cover"));
                        bookId.add(jsonObject2.getString("id"));
                    }

                    handler.post(runnableUi);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    Runnable runnableUi = new Runnable(){
        @Override
        public void run() {
            //添加视图
            addView();
        }

    };

    private void addView() {

        for (int i = 0; i < name.size(); i++) {
            //创建视图
            ll_layout = new LinearLayout(mContext);
            mImageView = new ImageView(mContext);
            TextView tvName = new TextView(mContext);
            LinearLayout ll_style = new LinearLayout(mContext);
            ll_style.setOrientation(LinearLayout.HORIZONTAL);
            //添加类型
            List<String> list = Utility.strongToList(type.get(i).toString());
            for (int y = 0; y < list.size(); y++) {
                mTextStyle = new TextView(mContext);
                mTextStyle.setText(list.get(y));
                setTextStyle(mTextStyle);
                ll_style.addView(mTextStyle);
            }
            //设置样式
            setLlayoutStyle(ll_layout);
            setImageStyle(mImageView);
            setTextNameStyle(tvName);

            //添加视图
            mAutoWrapLineLayout.addView(ll_layout);
            ll_layout.addView(mImageView);
            ll_layout.addView(tvName);
            ll_layout.addView(ll_style);
            //添加数据
            mImageView.setTag(bookId.get(i));
            GetHttpImg.setUserImg(mImageView, image.get(i));
            tvName.setText(name.get(i));

            //事件
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ReadDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", (String) v.getTag());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }





    //RelativeLayout 样式
    public void setLlayoutStyle(LinearLayout layout) {
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(5,0,0,0);
        layout.setLayoutParams(param);
    }

    //图片样式
    public void setImageStyle(ImageView iv) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LayoutParams param = new LayoutParams(width / 3 - 10, 500);

        iv.setLayoutParams(param);

    }

    //漫画名
    public void setTextNameStyle(TextView tv) {
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.setMargins(5,0,0,0);
        tv.setTextSize(16);
        tv.setMaxEms(6);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setSingleLine(true);
        tv.setSelected(true);
        tv.setFocusable(true);
        tv.setFocusableInTouchMode(true);
        tv.setLayoutParams(param);
    }

    public void setTextStyle(TextView tv) {
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.setMargins(5,0,5,0);
        tv.setLayoutParams(param);
    }


}