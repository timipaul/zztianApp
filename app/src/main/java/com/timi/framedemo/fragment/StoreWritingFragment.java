package com.timi.framedemo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑器 - 写作
 */
public class StoreWritingFragment extends FragmentActivity implements View.OnClickListener{

    private TextView mTextBut;
    private TextView mEmpty;
    private TextView mLabel;
    private EditText mEditText;
    private LinearLayout ll_item_layout;

    private String[] areas = new String[]{"玄幻","科幻", "梦幻", "奇幻", "武侠", "现代", "都市" };
    private boolean[] areaState=new boolean[]{true, false, false, false, false, false,false };
    private ListView areaCheckListView;
    private Map<String,Object> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compile_writing);
        initView();
    }

    protected void initView() {
        //final View view = View.inflate(mContext, R.layout.compile_writing,null);
        mTextBut = (TextView) findViewById(R.id.compile_writing_button);
        mEmpty = (TextView) findViewById(R.id.compile_writing_empty);
        mLabel = (TextView) findViewById(R.id.writing_add_label);
        mEditText = (EditText) findViewById(R.id.compile_writing_edit);
        ll_item_layout = (LinearLayout) findViewById(R.id.writing_add_label_place);


        mEmpty.setOnClickListener(this);
        mLabel.setOnClickListener(new CheckBoxClickListener());
        mTextBut.setOnClickListener(this);

        mEditText.addTextChangedListener(new TextWatcher() {
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

                if(str1.length() > 0){//enable=false
                    mTextBut.setEnabled(true);
                    mTextBut.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_but_compile_bg_press));
                } else{
                    mTextBut.setEnabled(false);
                    mTextBut.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_tab_compile_bg));
                }
            }
        });

    }

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
            case R.id.compile_writing_empty: //清空内容
                deleteEditContent();
                break;
            case R.id.compile_writing_button:    //点击提交
                System.out.println("点击了提交...");
                break;
        }
    }

    //清空edit里的内容  弹出确认框
    private void deleteEditContent() {
        // 创建退出对话框
        AlertDialog isExit = new AlertDialog.Builder(StoreWritingFragment.this).create();
        // 设置对话框标题
        isExit.setTitle("内容清空后无法恢复,确定要清空吗？");
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
                    mEditText.setText("");
                    ll_item_layout.removeAllViews();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };


    class AlertClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(StoreWritingFragment.this).setTitle("选择区域").setItems(areas,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Toast.makeText(StoreWritingFragment.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }).show();
        }
    }

    class CheckBoxClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            AlertDialog ad = new AlertDialog.Builder(StoreWritingFragment.this)
                    .setTitle("选择区域")
                    .setMultiChoiceItems(areas,areaState,new DialogInterface.OnMultiChoiceClickListener(){
                        public void onClick(DialogInterface dialog,int whichButton, boolean isChecked){
                            //点击某个区域
                        }
                    }).setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog,int whichButton){
                            String s = "您选择了:";
                            List<Map<String,Object>> listMap = new ArrayList<>();
                            Map<String,Object> map;

                            for (int i = 0; i < areas.length; i++){
                                if (areaCheckListView.getCheckedItemPositions().get(i)){
                                    map = new HashMap<>();
                                    map.put("id",i+"");
                                    map.put("name",areaCheckListView.getAdapter().getItem(i));
                                    listMap.add(map);

                                    s += i + ":"+ areaCheckListView.getAdapter().getItem(i)+ "  ";
                                }else{
                                    areaCheckListView.getCheckedItemPositions().get(i,false);
                                }
                            }
                            System.out.println("map:  " + listMap.toString());
                            if (areaCheckListView.getCheckedItemPositions().size() > 0){

                                addTypeTable(listMap);

                                Toast.makeText(StoreWritingFragment.this, s, Toast.LENGTH_LONG).show();
                            }else{
                                //没有选择
                            }
                            dialog.dismiss();
                        }
                    }).setNegativeButton("取消", null).create();
            areaCheckListView = ad.getListView();
            ad.show();
        }
    }

    private void addTypeTable(List listMap){
        //清空数据
        ll_item_layout.removeAllViews();
        //添加标签
        for (int i = 0; i < listMap.size(); i++) {

            TextView text = createtextView();
            Map getmap = (Map)listMap.get(i);
            String mapid = (String)getmap.get("id");
            String mapname = (String)getmap.get("name");
            int ids = Integer.parseInt(mapid);
            text.setId(ids);
            text.setText(mapname);
            ll_item_layout.addView(text);
        }
    }

    //创建textview 视图
    private TextView createtextView(){

        TextView text = new TextView(this);
        LayoutParams params = new LayoutParams(Utility.dpTopx(this,50),Utility.dpTopx(this,20));
        params.setMargins(5,Utility.dpTopx(this,5),0,0);
        text.setBackgroundResource(R.drawable.annular_label_bg);
        text.setTextColor(getResources().getColor(R.color.blue));
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(params);
        return text;
    }
}
