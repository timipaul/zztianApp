package com.timi.framedemo.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.timi.framedemo.R;
import com.timi.framedemo.activity.editor.StoryEditorView;
import com.timi.framedemo.fragment.StoreWritingFragment;

/**
 * 阅读 - 创作
 * 漫画创建或小说创建
 */
public class CreationSchemaActivity extends FragmentActivity implements View.OnClickListener{

    private Button mCreate_cancel;
    private Button mCreate_but;
    private TextView mCreate_title;
    private EditText mEditName;
    private EditText mEditLabel;
    private RadioGroup mRg_tyle;

    /** 0众创  1接龙  2独创 */
    private int tyle_code;
    /** 1创建漫画  2创建小说  */
    private int pattern;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_creation_schema);

        initView();
    }

    private void initView() {
        mCreate_cancel = (Button) findViewById(R.id.create_cartoon_cancel);     //取消
        mCreate_but = (Button) findViewById(R.id.create_cartoon_but);          //确定
        mCreate_title = (TextView) findViewById(R.id.create_cartoon_title);    //标题
        mEditName = (EditText) findViewById(R.id.create_cartoon_name);         //名称
        mEditLabel = (EditText) findViewById(R.id.create_cartoon_label);        //标签
        mRg_tyle = (RadioGroup) findViewById(R.id.rg_create_cartoon_type);        //类型


        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        //1表示创建漫画 2表示创建小说
        pattern = data.getInt("code");
        mCreate_cancel.setOnClickListener(this);

        displaywindow();

        //监听类型单选框
        mRg_tyle.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //监听输入名称
        mEditName.addTextChangedListener(new TextWatcherName());
        //监听输入标签
        mEditLabel.addTextChangedListener(new TextWatcherLabel());
        //创建提交
        mCreate_but.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_cartoon_but:   //提交
                createCartoonBut();
                break;
            case R.id.create_cartoon_cancel: //取消
                //showMyDialog();
                onBackPressed();
                break;
        }
    }

    /**
     * 弹窗--弹出创建剧本界面
     */
    public void displaywindow() {
        if(pattern == 1){
            mCreate_title.setText("创建漫画");
            mEditName.setHint("输入漫画名称");
            mEditLabel.setHint("输入漫画标签");
        }else if(pattern == 2){
            mCreate_title.setText("创建剧本");
            mEditName.setHint("输入剧本名称");
            mEditLabel.setHint("输入剧本标签");
        }

    }

    public void createCartoonBut (){
        Intent intent = null;
        if(pattern == 1){
            intent = new Intent(this, StoryEditorView.class);
        }else if(pattern == 2){
            intent = new Intent(this,StoreWritingFragment.class);
        }

        //提交保存数据跳转
        //intent.putExtra("pattern",pattern);
        intent.putExtra("tyle_code",tyle_code);
        intent.putExtra("name",mEditName.getText());
        intent.putExtra("table",mEditLabel.getText());
        intent.putExtra("user","用户账号");
        startActivity(intent);

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_create_cartoon_many://众创
                    tyle_code = 0;
                    break;
                case R.id.rb_create_cartoon_joint://接龙
                    tyle_code = 1;
                    break;
                case R.id.rb_create_cartoon_alone://独创
                    tyle_code = 2;
                    break;
                default:
                    tyle_code = 0;
                    break;
            }
        }
    }

    /** 监听名称输入 */
    private class TextWatcherName implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //校验都输入了才能点击确定
            String label = mEditLabel.getText().toString();
            if(s.length() > 0 && label.length() > 0){
                mCreate_but.setEnabled(true);
            }else {
                mCreate_but.setEnabled(false);
            }

        }
    }

    /** 监听标签输入 */
    private class TextWatcherLabel implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String name = mEditName.getText().toString();
            //校验都输入了才能点击确定
            if(s.length() > 0 && name.length() > 0){
                mCreate_but.setEnabled(true);
            }else {
                mCreate_but.setEnabled(false);
            }
        }
    }

    /*private void showMyDialog() {
        // 创建退出对话框
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        // 设置对话框标题
        isExit.setTitle("确定要退出创作吗？");
        // 添加选择按钮并注册监听
        isExit.setButton("确定",listener);
        isExit.setButton2("取消",listener);
        // 显示对话框
        isExit.show();
    }

    *//**
     * 监听对话框里面的button点击事件
     *//*
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };*/
}
