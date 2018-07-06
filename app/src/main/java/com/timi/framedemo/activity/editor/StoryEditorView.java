package com.timi.framedemo.activity.editor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
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
import com.timi.framedemo.Utils.DragScaleView;
import com.timi.framedemo.Utils.SharedPreferencesUtils;
import com.timi.framedemo.Utils.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 漫画编辑器 - 主界面
 */
public class StoryEditorView extends FragmentActivity implements View.OnClickListener{

    public int topViewHeight;
    //主界面左边菜单视图及宽度
    private LinearLayout leftView;
    public int leftViewWidth;
    /** 新建视图按钮 */
    private Button mNew_view;

    /** 用于保存多activity数据视图 */
    private LinkedHashMap<Integer,int[]> mMap = new LinkedHashMap<>();
    //当前视图数据/**  */
    private List<Bitmap> mBitmaps;
    private List<Map> mMapList;
    /** 全局图片 编辑器中一页中图片合成的图片，一页一张图 */
    private List<Bitmap> mAllList;
    /** 当前编辑图片 */
    private Bitmap mNowImage;
    /** 适配器保存好后并展示编辑好的图片 */
    private ListView mListView;
    private RelativeLayout rl_view;
    /** 编辑提交后显示的浏览效果视图 */
    private RelativeLayout rl_image;
    /** 显示内容的视图*/
    private RelativeLayout editor_content;
    /** 编辑单个内容的视图 */
    private RelativeLayout editor_view;
    /** 主界面菜单 主界面顶部视图及高度*/
    private LinearLayout top_menu_view;
    /** 图片编辑界面菜单 */
    private RelativeLayout edit_menu_view;
    /** 当前页中的图片位置数据 */
    private LinkedHashMap<Integer,int[]> mNowMap;
    /** 新建准备设计视图的大小 */
    private int view_height;
    private int view_width;
    /** 用于记录素材图片坐标 */
    private int[] site;
    //需要移动的位置
    private int imageX = 0;
    private int imageY = 0;

    /** 内容视图的宽高 */
    private int editor_height;
    private int editor_width;
    //起始图层
    private int count = 0;
    //当前编辑的视图ID
    private int viewId;

    long mLastTime=0;
    long mCurTime=0;

    /** 用于表示当前编辑状态 0表示在全局  1表示在里层*/
    private int state_code = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }



    private void initView() {
        setContentView(R.layout.story_editor_view);
        editor_content = (RelativeLayout) findViewById(R.id.editor_content);
        top_menu_view = (LinearLayout) findViewById(R.id.top_menu);
        edit_menu_view = (RelativeLayout) findViewById(R.id.edit_menu);
        leftView = (LinearLayout) findViewById(R.id.left_mune);
        mListView = (ListView) findViewById(R.id.listview);
        rl_view = (RelativeLayout) findViewById(R.id.redact_view);
        rl_image = (RelativeLayout) findViewById(R.id.image_view);
        editor_view = (RelativeLayout) findViewById(R.id.editor_view);
        mNew_view = (Button) findViewById(R.id.new_view);


        mBitmaps = new ArrayList<>();
        mAllList = new ArrayList<>();
        mMapList = new ArrayList<>();
        mNowMap = new LinkedHashMap();

        //设置点击效果
        setManyClick();
        //获取视图的数据信息
        getViewData();

        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //添加可滑动调节编辑视图
        newCreateView();


    }

    //获取视图的宽高信息
    private void getViewData() {
        //顶部菜单视图
        top_menu_view.post(new Runnable() {
            @Override
            public void run() {
                topViewHeight= top_menu_view.getMeasuredHeight();
                SharedPreferencesUtils.setParam(StoryEditorView.this,"topViewHeight",topViewHeight);
            }
        });

        //左边菜单视图
        leftView.post(new Runnable() {
            @Override
            public void run() {
                leftViewWidth= leftView.getMeasuredWidth();
                SharedPreferencesUtils.setParam(StoryEditorView.this,"leftViewWidth",leftViewWidth);
            }
        });

        //内容视图
        editor_content.post(new Runnable() {
            @Override
            public void run() {
                editor_width = editor_content.getMeasuredWidth();
                editor_height = editor_content.getMeasuredHeight();
            }
        });

    }

    //设置点击效果
    private void setManyClick() {
        //设置点击
        for (int i = 0; i < top_menu_view.getChildCount(); i++) {
            View view = top_menu_view.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        }
        for (int i = 0; i < edit_menu_view.getChildCount(); i++) {
            View view = edit_menu_view.getChildAt(i);
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
        switch (v.getId()){
            case R.id.new_view:
                //新建视图
                newCreateView();
                break;
            case R.id.overturn:
                rotaingImageView(90);
                break;
            case R.id.menu_save:
                //保存图片数据
                saveImageToView();
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
                //视图往下移
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
            case R.id.edit_return:
                //编辑视图里的返回
                cancelHint();
                break;
            case R.id.edit_menu_save:
                //编辑视图里的保存
                saveImageToBackstage();
                //提示确定完成编辑
                //转换成图片返回显示
                break;
            case R.id.edit_empty:
                //编辑视图里的清空
                editor_view.removeAllViews();
                //清空数据
                ((ShareDataApplication)this.getApplicationContext()).removeDataList();
                break;
        }
    }

    private void cancelHint() {
        // 创建退出对话框
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        // 设置对话框标题
        isExit.setTitle("数据将清空，确定要取消吗？");
        // 添加选择按钮并注册监听
        isExit.setButton("确定",cancel);
        isExit.setButton2("取消",cancel);
        // 显示对话框
        isExit.show();
    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener cancel = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    //隐藏编辑视图
                    showOrhideView(0);
                    //提示取消编辑将会清空数据
                    editor_view.removeAllViews();
                    ((ShareDataApplication)StoryEditorView.this.getApplicationContext()).removeDataList();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    //新建一个可编辑视图
    private void newCreateView() {

        //获取时间用作id
        final int Id = Utility.getSecondTimestampTwo(new Date());
        final DragScaleView drag = new DragScaleView(this);
        drag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,Utility.dpTopx(this,200)));
        drag.setClickable(true);
        drag.setId(Id);
        drag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.setToast(StoryEditorView.this,"the button was clicked:" + v.getId());
                mLastTime = mCurTime;
                mCurTime = System.currentTimeMillis();
                view_height = v.getHeight();
                view_width = v.getWidth();

                if(mCurTime - mLastTime<300){//双击事件
                    mCurTime =0;
                    mLastTime = 0;
                    viewId = v.getId();
                    //移除当前调节视图
                    editor_content.removeView(drag);
                    //创建一个视图 用于编辑
                    newEditView(view_height,view_width);
                }
            }
        });

        editor_content.addView(drag);

    }

    //创建一个编辑用的视图
    private void newEditView(int height,int width) {
        final int Id = Utility.getSecondTimestampTwo(new Date());
        //显示编辑视图
        showOrhideView(1);
        editor_view.removeAllViews();
        editor_view.setLayoutParams(new LinearLayout.LayoutParams(width,height));
        editor_view.setBackground(getResources().getDrawable(R.drawable.annular_label_bg));
        editor_view.setId(Id);
        //设置当前编辑视图ID
        SharedPreferencesUtils.setParam(StoryEditorView.this,"presentViewId",editor_view.getId());

        //修改编辑状态
        state_code = 1;

    }

    private void showOrhideView(int code){
        if(code == 1){
            //显示隐藏菜单界面
            edit_menu_view.setVisibility(View.VISIBLE);
            top_menu_view.setVisibility(View.INVISIBLE);
            //显示隐藏视图编辑界面
            editor_content.setVisibility(View.GONE);
            editor_view.setVisibility(View.VISIBLE);
            //显示隐藏新建视图按钮
            mNew_view.setVisibility(View.GONE);
        }else{
            //显示隐藏当前编辑视图
            editor_content.setVisibility(View.VISIBLE);
            editor_view.setVisibility(View.GONE);
            //显示隐藏菜单界面
            top_menu_view.setVisibility(View.VISIBLE);
            edit_menu_view.setVisibility(View.GONE);
            mNew_view.setVisibility(View.VISIBLE);
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
        saveImageToBackstage();
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
            saveImageToView();
            rl_view.setVisibility(View.GONE);
            rl_image.setVisibility(View.VISIBLE);
            mListView.setAdapter(adapter);

        }
    }

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
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
            //image.setBackground(getResources().getDrawable(R.drawable.bg_border));
            image.setScaleType(ImageView.ScaleType.CENTER);
            for (int i = 0; i < mAllList.size(); i++) {
                image.setImageBitmap(bitmapCombine(mAllList.get(position),3,3, Color.GREEN));
            }

            return image;
        }
    };

    /**
     * 获得添加边框了的Bitmap
     * @param bm 原始图片Bitmap
     * @param smallW 一条边框宽度
     * @param smallH 一条边框高度
     * @param color 边框颜色值
     * @return Bitmap 添加边框了的Bitmap
     */
    private Bitmap bitmapCombine(Bitmap bm, int smallW, int smallH,int color) {
        //防止空指针异常
        if(bm==null){
            return null;
        }

        // 原图片的宽高
        final int bigW = bm.getWidth();
        final int bigH = bm.getHeight();

        // 重新定义大小
        int newW = bigW+smallW*2;
        int newH = bigH+smallH*2;

        // 绘原图
        Bitmap newBitmap = Bitmap.createBitmap(newW, newH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawRect(new Rect(0, 0, newW, newH), p);

        // 绘边框
        canvas.drawBitmap(bm, (newW - bigW - 2 * smallW) / 2 + smallW, (newH
                - bigH - 2 * smallH)
                / 2 + smallH, null);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return newBitmap;
    }


    //视图切换之上下一层
    private void setViewToTopOrBelow(int in) {
        //获取最后一次点击的视图ID
        int viewId = (int)SharedPreferencesUtils.getParam(StoryEditorView.this,"finallyView",0);
        RelativeLayout view;

        //判断当前视图是正在编辑的还是外层的
        if(state_code == 0){
            view = editor_content;
        }else if (state_code == 1){
            view = editor_view;
        }else{
            Utility.setToast(this,"切换异常");
            return;
        }

        //当前视图所在父类容器中的下标
        View ID = findViewById(viewId);
        int index = view.indexOfChild(ID);
        if(viewId == 0){
            return;
        }

        //区分开界面编辑的视图
        int newIndex;
        if (state_code == 1|| index <= 0){
            newIndex = (index + in) == 0?index:index + in;
        }else{
            newIndex = index + in;
        }

        //预防下标溢出
        if(view.getChildCount() == newIndex){
            return;
        }
        view.removeView(ID);
        view.addView(ID,newIndex);
    }


    private void saveImageToBackstage() {
        //获取图片数据
        ShareDataApplication sd = (ShareDataApplication) getApplicationContext();
        LinkedHashMap<Integer,int[]> m = sd.getDataList();
        System.out.println("m: ==== " + m.toString());

        if(m.size() == 0){
            Toast.makeText(this,"请编辑类容再保存", Toast.LENGTH_SHORT).show();
            return;
        }

        //合成图片
        mNowImage = combineBitmap(m);
        int id = Utility.getSecondTimestampTwo(new Date());
        ImageView iv = new ImageView(this);
        iv.setId(id);
        iv.setImageBitmap(mNowImage);
        iv.setOnTouchListener(new PicOnTouchListener());
        iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("图片点击了...");
            }
        });
        editor_content.addView(iv);
        //初始化视图位置信息
        mNowMap.put(iv.getId(),new int[2]);
        //清空图片共享位置数据
        ((ShareDataApplication)this.getApplicationContext()).removeDataList();
        //清空当前视图数据
        editor_view.removeAllViews();
        showOrhideView(0);

        //修改编辑状态
        state_code = 0;


    }

    /**
     * 保存每页的图片
     */
    private void saveImageToView(){
        //拿到图片及位置数据
        Bitmap bitImage = combineBitmap(mNowMap);

        try {
            mAllList.set(count,bitImage);
        } catch (Exception e) {
            mAllList.add(count,bitImage);
        }
        Toast.makeText(this,"保存成功", Toast.LENGTH_SHORT).show();

    }

    /**
     * 图片组合
     * @return
     */
    public Bitmap combineBitmap(LinkedHashMap<Integer,int[]> map){
        Bitmap newmap;
        if( map != null){
            if(state_code == 0){
                System.out.println("图片宽高：");
                newmap = Bitmap.createBitmap(editor_width, editor_height, Bitmap.Config.ARGB_8888);
            }else{
                newmap = Bitmap.createBitmap(view_width, view_height, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(newmap);

            for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
                int[] data = entry.getValue();
                if(data != null){
                    ImageView iv = (ImageView) findViewById(entry.getKey());
                    canvas.drawBitmap(Utility.viewToBitmap(iv), data[0], data[1], null);
                }
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

    /**
     * 旋转图片
     * @param angle 旋转角度
     *
     * @return 处理后的Bitmap
     */
    public Bitmap rotaingImageView(int angle)
    {

        int viewId = (int)SharedPreferencesUtils.getParam(StoryEditorView.this,"finallyView",0);
        ImageView im = ((ImageView) findViewById(viewId));
        Bitmap bitmap = ((BitmapDrawable)im.getDrawable()).getBitmap();
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }

        im.setImageBitmap(resizedBitmap);
        return resizedBitmap;
    }

    //OnTouch监听器  监听图片移动
    private class PicOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event){

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:  //起始位置
                    //修改成当前移动图片的宽高
                    view_width = v.getWidth();
                    view_height = v.getHeight();
                    site = new int[2];
                    break;
                case MotionEvent.ACTION_MOVE:   //实时位置
                    imageX = (int)event.getRawX() - v.getWidth() / 2 - leftViewWidth;
                    imageY = (int)event.getRawY() - v.getHeight() /2- topViewHeight;
                    //手势移动图片位置
                    moveViewWithFinger(v,imageX,imageY);
                    site[0] = imageX;
                    site[1] = imageY;
                    break;
                case MotionEvent.ACTION_UP:     //结束位置
                    //保存当前移动图片的位置
                    SharedPreferencesUtils.setParam(StoryEditorView.this,"finallyView",v.getId());
                    mNowMap.put(v.getId(),site);
                    break;
            }

            return false;
        }
    }

    //手势移动图片位置
    private void moveViewWithFinger(View view, int rawX, int rawY) {

        //防止图片移出视图外
        rawX = rawX >= 0 ? rawX : 0;
        rawY = rawY >= 0 ? rawY : 0;

        rawX = view_width + rawX < editor_width ? rawX : editor_width - view_width;
        rawY = view_height + rawY < editor_height ? rawY : editor_height - view_height;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //0.05解决一个像素差bug
        //params.leftMargin =  rawX - (int) (rawX*0.05);
        //params.topMargin =  rawY + (int) (rawY*0.05);
        params.leftMargin =  rawX;
        params.topMargin =  rawY;
        view.setLayoutParams(params);
    }

}
