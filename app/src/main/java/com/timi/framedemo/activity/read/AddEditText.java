package com.timi.framedemo.activity.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.BackHandlerHelper;

/**
 * 内容阅读时编辑添加的章节内容
 */
public class AddEditText extends FragmentActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_add_text_content);

    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_text_return:
                onBackPressed();
                break;
        
            default:
                break;
        }
    }
}
