package com.timi.framedemo.activity.editor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.ShareDataApplication;
import com.timi.framedemo.Utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 漫画编辑器 - 主界面
 */
public class StoryEditorView extends FragmentActivity implements View.OnClickListener{

    private RelativeLayout editor_content;

    //public List<View> view = new ArrayList<>();

    //主界面顶部视图及高度
    private LinearLayout topView;
    public int topViewHeight;
    //主界面左边菜单视图及宽度
    private LinearLayout leftView;
    public int leftViewWidth;
    private LinkedHashMap<Integer,int[]> mMap = new LinkedHashMap<>();
    //当前视图数据
    private List<Bitmap> mBitmaps;
    private List<Map> mMapList;
    private List<Bitmap> mAllList;
    private ListView mListView;
    private RelativeLayout rl_view;
    private RelativeLayout rl_image;
    //起始图层
    private int count = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }



    private void initView() {
        setContentView(R.layout.story_editor_view);
        editor_content = (RelativeLayout) findViewById(R.id.editor_content);
        topView = (LinearLayout) findViewById(R.id.top_menu);
        leftView = (LinearLayout) findViewById(R.id.left_mune);
        mListView = (ListView) findViewById(R.id.listview);
        rl_view = (RelativeLayout) findViewById(R.id.redact_view);
        rl_image = (RelativeLayout) findViewById(R.id.image_view);

        mBitmaps = new ArrayList<>();
        mAllList = new ArrayList<>();
        mMapList = new ArrayList<>();
        //设置点击
        for (int i = 0; i < topView.getChildCount(); i++) {
            View view = topView.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        }
        for (int i = 0; i < leftView.getChildCount(); i++) {
            View view = leftView.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        }

        topView.post(new Runnable() {
            @Override
            public void run() {
                topViewHeight= topView.getMeasuredHeight();
                SharedPreferences share=null;
                //得到SharePreferences对象，第一个参数：指定文件名，第二个参数：操作模式
                share = StoryEditorView.this.getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor edit=share.edit();
                edit.putInt("topViewHeight", topViewHeight);
                edit.commit();
            }
        });

        leftView.post(new Runnable() {
            @Override
            public void run() {
                leftViewWidth= leftView.getMeasuredWidth();
                SharedPreferences share=null;
                //得到SharePreferences对象，第一个参数：指定文件名，第二个参数：操作模式
                share = StoryEditorView.this.getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor edit=share.edit();
                edit.putInt("leftViewWidth", leftViewWidth);
                edit.commit();
            }
        });
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initData() {
        //获取编辑器视图数据
        ShareDataApplication sd = (ShareDataApplication) this.getApplicationContext();
        if(sd.getDataList() != null){
            mMap = sd.getDataList();
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_save:
                //保存图片到内存中
                saveImageToPhone();
                break;
            case R.id.menu_empty:
                //清空
                showMyDialog();
                break;
            case R.id.view_top:
                //视图往上移
                setViewToTopOrBelow(1);
                break;
            case R.id.view_below:
                setViewToTopOrBelow(-1);
                break;
            case R.id.set_back:
                //调色
                Toast.makeText(this,"功能完善中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_submit:
                //显示图片集
                buttonShowImage();
                break;
            case R.id.menu_top_page:
                //上一页
                redactNewView(-1);
                break;
            case R.id.menu_down_page:
                redactNewView(1);
                //下一页
                break;
        }
    }

    //创建新一页
    private void redactNewView(int view) {
        if(view == -1){
            count = count + view < 0 ? 0 : count + view;
            if(count == 0){
                Toast.makeText(this,"已经是第一页了", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(mBitmaps == null){
            Toast.makeText(this,"请编辑保存", Toast.LENGTH_SHORT).show();
            return;
        }
        //保存视图
        saveImageToPhone();
        mBitmaps.clear();
        mMapList.add(mMap);
        mMap.clear();
        ((ShareDataApplication)this.getApplicationContext()).removeDataList();

        //清空视图 当做新的一页
        editor_content.removeAllViews();

        count += view;

    }

    //提交展示图片
    private void buttonShowImage() {
        //浏览编辑好的图片以及上传
        if(mBitmaps == null){
            Toast.makeText(this,"编辑后请保存...", Toast.LENGTH_SHORT).show();
        }else{
            rl_view.setVisibility(View.GONE);
            rl_image.setVisibility(View.VISIBLE);
            mListView.setAdapter(adapter);

        }
    }

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            System.out.println("要显示图片的数量：" + mAllList.size());
            return mAllList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAllList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image = new ImageView(StoryEditorView.this);
            //image.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.MATCH_PARENT));
            image.setScaleType(ImageView.ScaleType.CENTER);
            for (int i = 0; i < mAllList.size(); i++) {
                image.setImageBitmap(mAllList.get(position));
            }

            return image;
        }
    };


    //视图切换之上下一层
    private void setViewToTopOrBelow(int in) {


        //创建一个字段用于存放最后点击操作的视图ID
        SharedPreferences share_get=null;
        share_get=this.getSharedPreferences("data", MODE_PRIVATE);
        //最后点击的ID
        int viewId = share_get.getInt("finallyView", 0);

        //当前视图所在父类容器中的下标
        View view = findViewById(viewId);
        int index = editor_content.indexOfChild(view);
        if(viewId == 0 || index <= 0){
            return;
        }

        int newIndex = (index + in) == 0?index:index + in;

        //预防下标溢出
        if(editor_content.getChildCount() == newIndex){
            return;
        }
        editor_content.removeView(view);
        editor_content.addView(view,newIndex);
    }


    private void saveImageToPhone() {
        //获取图片数据
        ShareDataApplication sd = (ShareDataApplication) getApplicationContext();
        LinkedHashMap<Integer,int[]> m = sd.getDataList();
        System.out.println("m: ==== " + m.toString());
        if(m == null){
            Toast.makeText(this,"请编辑类容再保存", Toast.LENGTH_SHORT).show();
            return;
        }
        //合成图片
        Bitmap bitImage = combineBitmap(m);
        //保存 需要一个标识  判断是当前视图位置
        System.out.println("图片要存放的位置" + count);
        //mBitmaps.add(bitImage);

        try {
            mAllList.set(count,bitImage);
        } catch (Exception e) {
            mAllList.add(count,bitImage);
        }

        System.out.println("图片数量：" + mAllList.toString());
        Toast.makeText(this,"保存成功", Toast.LENGTH_SHORT).show();
    }


    /**
     * 图片组合
     * @return
     */
    public Bitmap combineBitmap(LinkedHashMap<Integer,int[]> map){
        Bitmap newmap;
        if( map != null){
            //获取编辑器视图的宽高
            int bgWidth = editor_content.getWidth();

            int bgHeight = editor_content.getHeight();
            newmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newmap);
            for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
                int[] data = entry.getValue();
                ImageView iv = (ImageView) findViewById(entry.getKey());
                canvas.drawBitmap(Utility.viewToBitmap(iv), data[0], data[1], null);
            }
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();

        }else{
            return null;
        }
        return newmap;
    }


    private void showMyDialog() {
        LinkedHashMap<Integer,int[]> map = new LinkedHashMap<>();
        ShareDataApplication s = (ShareDataApplication) this.getApplicationContext();
        if(s.getDataList() != null){
            map = s.getDataList();
        }
            System.out.println("map里的数据：" + map.toString());
        if(map.size() == 0){
            Toast.makeText(this,"还没有编辑哦",Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建退出对话框
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        // 设置对话框标题
        isExit.setTitle("確定清除设计吗？");
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
                editor_content.removeAllViews();
                mMap = null;
                break;
            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                break;
            default:
                break;
        }
        }
    };

}
