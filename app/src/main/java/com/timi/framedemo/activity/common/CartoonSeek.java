package com.timi.framedemo.activity.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.GetHttpImg;
import com.timi.framedemo.Utils.HttpUtils;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.activity.read.ReadDetails;
import com.timi.framedemo.bean.Cartoon;
import com.timi.framedemo.bean.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 漫画 作者搜索
 */
public class CartoonSeek extends FragmentActivity implements View.OnClickListener{

    private ImageView mSeekIcon;
    private TextView mConceal;
    private EditText mContent;
    private LinearLayout ll_resule;
    private RelativeLayout rl_recommend;
    private List<String> datas;
    private GridView mWrapLayout;
    private Handler handler=null;

    private LinearLayout mLayout_users;
    private LinearLayout mLayout_fiction;
    private LinearLayout mLayout_cartoon;
    private LinearLayout mLayout_community;

    private List<Cartoon> mList;
    private List<User> userList;
    private List<Cartoon> fictionList;
    private List<Cartoon> cartoonList;
    private List<Cartoon> communityList;

    private String userId;

    private int userSum;
    private int fictionSum;
    private int cartoonSum;
    private int communitySum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_common_search);

        initView();
        initData();

    }

    protected void initView() {
        handler=new Handler();
        mSeekIcon = (ImageView) findViewById(R.id.seek_change_recommend);
        mConceal = (TextView) findViewById(R.id.home_seek_conceal);
        mContent = (EditText) findViewById(R.id.home_seek_content);
        rl_recommend = (RelativeLayout) findViewById(R.id.hot_seek_layout);
        ll_resule = (LinearLayout) findViewById(R.id.seek_filtrate);
        mWrapLayout = (GridView) findViewById(R.id.wl_seek_recommend);
        mLayout_users = (LinearLayout) findViewById(R.id.result_user);
        mLayout_fiction = (LinearLayout) findViewById(R.id.result_fiction);
        mLayout_cartoon = (LinearLayout) findViewById(R.id.result_cartoon);
        mLayout_community = (LinearLayout) findViewById(R.id.result_community);

        //ll_resule.removeAllViews();

        mSeekIcon.setOnClickListener(this);
        mConceal.setOnClickListener(this);

        //监听焦点
        mContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("hasFocus1: " + hasFocus);
                if (!hasFocus) {
                    System.out.println("hasFocus2: " + hasFocus);
                }
            }
        });

        //监听内容
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str1 = "";
                //去掉空格
                if(s.toString().contains(" ")){
                    String[] str = s.toString().split(" ");
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                }else{
                    str1 = s.toString();
                }

                //切换搜索内容
                if(str1.length() > 0){
                    ll_resule.setVisibility(View.VISIBLE);
                    rl_recommend.setVisibility(View.INVISIBLE);
                    //访问后台数据
                    //获取查询用户
                    butNewDate(str1);
                    //获取查询小说
                    getFictionData(str1);
                    //获取查询漫画
                    getCartoonData(str1);
                    //相关帖子
                    getInvitationData(str1);

                } else{
                    ll_resule.setVisibility(View.GONE);
                    rl_recommend.setVisibility(View.VISIBLE);

                }
            }


        });
    }


    private void initData() {

        //加载热门搜索
        recommendCartoon();

        //查询用户Id
        SharedPreferences share_get=null;
        share_get= this.getSharedPreferences("data", MODE_PRIVATE);
        //根据键获取数据，第二个参数为默认值，若没有指定的键，则返回默认值
        userId = share_get.getString("userId", "");


    }

    //热门推荐
    private void recommendCartoon(){
        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .build();
                try {
                    mList = new ArrayList<>();
                    String url = "/cartoon/hotSelect";
                    String result = httpUtils.OkhttpPost(url,formBody);
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Cartoon car = new Cartoon();
                        car.setId(json.getInt("id"));
                        car.setBookName(json.getString("bookName"));
                        mList.add(car);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(runnableUi);
            }
        }.start();
    }



    //更新热门推荐
    Runnable runnableUi = new Runnable(){
        @Override
        public void run() {
            //更新视图
            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return mList.size();
                }

                @Override
                public Object getItem(int position) {
                    return mList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View view, ViewGroup parent) {
                    view = LayoutInflater.from(CartoonSeek.this).inflate(R.layout.fragment_common_search_item, null);
                    TextView item = (TextView) view.findViewById(R.id.text_item);
                    item.setId(mList.get(position).getId());
                    item.setText(mList.get(position).getBookName());

                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CartoonSeek.this, ReadDetails.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", String.valueOf(v.getId()));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    return view;
                }
            };

            mWrapLayout.setAdapter(adapter);
        }

    };


    //查询用户相关数据
    private void butNewDate(final String data) {
        new Thread(){
            @Override
            public synchronized void run() {
                userList = new ArrayList<>();
                System.out.println("请求用户相关数据");
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("fuzzy",data)
                        .add("userId",userId)
                        .build();
                try {
                    String url = "/cartoon/queryUser";
                    Map map = httpUtils.OkhttpPostList(url,formBody);
                    String result = (String) map.get("result");
                    userSum = Integer.valueOf(map.get("status").toString());
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    System.out.println("用户数据：" + jsonArray);

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        User user = new User();
                        user.setId((Integer) json.get("id"));
                        user.setNickName(json.getString("nickName"));
                        user.setHeadimg(json.getString("headimg"));
                        user.setIfConcern(json.getString("ifConcern"));
                        userList.add(user);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(runableUser);
            }
        }.start();
    }

    //更新搜索用户
    Runnable runableUser = new Runnable() {
        @Override
        public void run() {
            View view = LayoutInflater.from(CartoonSeek.this).inflate(R.layout.fragment_common_search_result_item, null);
            ListView lv = (ListView) view.findViewById(R.id.search_list);
            TextView message = (TextView) view.findViewById(R.id.title_content);
            TextView more = (TextView) view.findViewById(R.id.more);
            mLayout_users.removeAllViews();
            message.setText("相关用户 - " + userSum + "篇");
            System.out.println(userSum);

            if(userSum <= 3){
                more.setVisibility(View.GONE);
            }
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("跳转更多....");
                }
            });
            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return userList.size();
                }

                @Override
                public Object getItem(int position) {
                    return userList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View v, ViewGroup parent) {

                    v = LayoutInflater.from(CartoonSeek.this).inflate(R.layout.comment_user_item, null);
                    ImageView im = (ImageView) v.findViewById(R.id.user_image);
                    TextView name = (TextView) v.findViewById(R.id.user_name);
                    TextView content = (TextView) v.findViewById(R.id.presentation);
                    TextView att =  (TextView) v.findViewById(R.id.add_attention);

                    GetHttpImg.setUserImg(im, userList.get(position).getHeadimg());
                    name.setText(userList.get(position).getNickName());
                    content.setText(userList.get(position).getCover());

                    //判断是否关注
                    att.setText(userList.get(position).getIfConcern().equals("0")?"关注":"已关注");
                    return v;
                }
            };
            lv.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(lv);
            mLayout_users.addView(view);
        }
    };

    //小说相关信息
    private void getFictionData(final String data) {
        new Thread(){
            @Override
            public synchronized void run() {
                fictionList = new ArrayList<>();
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("fuzzy",data)
                        .add("userId",userId)
                        .build();
                try {
                    String url = "/cartoon/queryBooks";
                    Map map = httpUtils.OkhttpPostList(url,formBody);
                    String result = (String) map.get("result");
                    fictionSum = Integer.valueOf(map.get("status").toString());
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    System.out.println("小说数据：" + jsonArray);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Cartoon car = new Cartoon();
                        car.setId((Integer) json.get("id"));
                        car.setBookName(json.getString("bookName"));
                        car.setCover(json.getString("cover"));
                        car.setAuthor(json.getString("author"));
                        car.setClickNum(json.getInt("clickNum"));
                        car.setCollectNum(json.getInt("collectNum"));
                        fictionList.add(car);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(runableFiction);
            }
        }.start();
    }

    //加载搜索小说
    Runnable runableFiction = new Runnable() {
        @Override
        public void run() {
            View view = LayoutInflater.from(CartoonSeek.this).inflate(R.layout.fragment_common_search_result_item, null);
            ListView lv = (ListView) view.findViewById(R.id.search_list);
            TextView message = (TextView) view.findViewById(R.id.title_content);
            TextView more = (TextView) view.findViewById(R.id.more);

            mLayout_fiction.removeAllViews();
            message.setText("相关小说 - " + fictionSum + "篇");
            if(fictionSum <= 3){
                more.setVisibility(View.GONE);
            }
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("跳转更多....");
                }
            });
            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return fictionList.size();
                }
                @Override
                public Object getItem(int position) {
                    return fictionList.get(position);
                }
                @Override
                public long getItemId(int position) {
                    return position;
                }
                @Override
                public View getView(int position, View v, ViewGroup parent) {

                    v = LayoutInflater.from(CartoonSeek.this).inflate(R.layout.home_read_classify_cartoon_item, null);
                    ImageView logo = (ImageView) v.findViewById(R.id.classify_cartoon_logo);
                    TextView name = (TextView) v.findViewById(R.id.classify_cartoon_name);
                    TextView author = (TextView) v.findViewById(R.id.classify_cartoon_author);
                    TextView click = (TextView) v.findViewById(R.id.classify_cartoon_clickGood);
                    TextView browse = (TextView) v.findViewById(R.id.calssify_cartoon_browse);


                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utility.dpTopx(CartoonSeek.this,80),
                            Utility.dpTopx(CartoonSeek.this,100));
                    logo.setLayoutParams(params);
                    GetHttpImg.setUserImg(logo, fictionList.get(position).getCover());
                    name.setText(fictionList.get(position).getBookName());
                    author.setText(fictionList.get(position).getAuthor());
                    click.setText(fictionList.get(position).getClickNum()+"点赞");
                    browse.setText(fictionList.get(position).getCollectNum()+"收藏");

                    return v;
                }
            };

            lv.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(lv);
            mLayout_fiction.addView(view);
        }
    };

    //获取查询漫画
    private void getCartoonData(final String data) {
        new Thread(){
            @Override
            public synchronized void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("fuzzy",data)
                        .add("userId",userId)
                        .build();
                try {
                    cartoonList = new ArrayList<>();

                    String url = "/cartoon/queryCartoon";
                    Map map = httpUtils.OkhttpPostList(url,formBody);
                    String result = (String) map.get("result");
                    cartoonSum = Integer.valueOf(map.get("status").toString());
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    System.out.println("漫画数据：" + jsonArray);

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Cartoon car = new Cartoon();
                        car.setId((Integer) json.get("id"));
                        car.setBookName(json.getString("bookName"));
                        car.setCover(json.getString("cover"));
                        car.setAuthor(json.getString("author"));
                        car.setClickNum(json.getInt("clickNum"));
                        car.setCollectNum(json.getInt("collectNum"));
                        cartoonList.add(car);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(runableCartoon);
            }
        }.start();
    }


    //加载搜索漫画
     Runnable runableCartoon = new Runnable() {
        @Override
        public void run() {
            View view = LayoutInflater.from(CartoonSeek.this).inflate(R.layout.fragment_common_search_result_item, null);
            ListView lv = (ListView) view.findViewById(R.id.search_list);
            TextView message = (TextView) view.findViewById(R.id.title_content);
            TextView more = (TextView) view.findViewById(R.id.more);

            mLayout_cartoon.removeAllViews();
            message.setText("相关漫画 - " + cartoonSum + "篇");
            if(cartoonSum <= 3){
                more.setVisibility(View.GONE);
            }
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("跳转更多....");
                }
            });
            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return cartoonList.size();
                }
                @Override
                public Object getItem(int position) {
                    return cartoonList.get(position);
                }
                @Override
                public long getItemId(int position) {
                    return position;
                }
                @Override
                public View getView(int position, View v, ViewGroup parent) {

                    v = LayoutInflater.from(CartoonSeek.this).inflate(R.layout.home_read_classify_cartoon_item, null);
                    ImageView logo = (ImageView) v.findViewById(R.id.classify_cartoon_logo);
                    TextView name = (TextView) v.findViewById(R.id.classify_cartoon_name);
                    TextView author = (TextView) v.findViewById(R.id.classify_cartoon_author);
                    TextView click = (TextView) v.findViewById(R.id.classify_cartoon_clickGood);
                    TextView browse = (TextView) v.findViewById(R.id.calssify_cartoon_browse);


                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utility.dpTopx(CartoonSeek.this,80),
                            Utility.dpTopx(CartoonSeek.this,100));
                    logo.setLayoutParams(params);
                    GetHttpImg.setUserImg(logo, fictionList.get(position).getCover());
                    name.setText(cartoonList.get(position).getBookName());
                    author.setText(cartoonList.get(position).getAuthor());
                    click.setText(cartoonList.get(position).getClickNum()+"点赞");
                    browse.setText(cartoonList.get(position).getCollectNum()+"收藏");

                    return v;
                }
            };

            lv.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(lv);
            mLayout_cartoon.addView(view);
        }
    };


    //相关帖子
    private void getInvitationData(final String data) {
        new Thread(){
            @Override
            public synchronized void run() {
                HttpUtils httpUtils = new HttpUtils();
                RequestBody formBody = new FormBody.Builder()
                        .add("fuzzy",data)
                        .add("userId",userId)
                        .build();
                try {
                    communityList = new ArrayList<>();

                    String url = "/cartoon/queryTopic";
                    Map map = httpUtils.OkhttpPostList(url,formBody);
                    String result = (String) map.get("result");
                    communitySum = Integer.valueOf(map.get("status").toString());
                    JSONArray jsonArray = JSONArray.fromObject(result);
                    System.out.println("帖子数据：" + jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(runableTopic);
            }
        }.start();
    }

    //加载搜索帖子
    Runnable runableTopic = new Runnable() {
        @Override
        public void run() {
            System.out.println("漫画结果：");
            View view = LayoutInflater.from(CartoonSeek.this).inflate(R.layout.fragment_common_search_result_item, null);
            TextView message = (TextView) view.findViewById(R.id.title_content);
            TextView more = (TextView) view.findViewById(R.id.more);

            mLayout_community.removeAllViews();
            message.setText("相关帖子 - " + communitySum + "篇");
            if(communitySum == 0){
                more.setVisibility(View.GONE);
            }
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("跳转更多....");
                }
            });

            mLayout_community.addView(view);
        }
    };

    //点击空白处隐藏输入框
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seek_change_recommend:    //旋转图标
                rotateImage();
                break;
            case R.id.home_seek_conceal:    //取消 返回上一层
                onBackPressed();
                break;
        }

    }

    //旋转动画
    private void rotateImage() {
        //创建旋转动画
        Animation animation = new RotateAnimation(0, 180,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setRepeatCount(2);//动画的重复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        mSeekIcon.startAnimation(animation);//开始动画
    }

    private class contentClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            System.out.println("结果：" + v.getTag());
        }
    }
}
