package com.timi.framedemo.text;

import android.view.View;
import android.widget.TextView;

import com.timi.framedemo.base.BaseFragment;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class fragment02 extends BaseFragment {

    @Override
    protected View initView() {
        System.out.println("fragment02");
        TextView tv = new TextView(mContext);
        tv.setText("我是2222");
        return tv;
    }
}
