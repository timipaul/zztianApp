package com.timi.framedemo.activity.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.HorizontalListView;
import com.timi.framedemo.adapter.HorizontalListViewAdapter;
import com.timi.framedemo.adapter.ProductionAdapter;

/**
 * 我的 - 用户个人信息
 */
public class UserMessageSetActivity extends Activity implements View.OnClickListener{

    private HorizontalListView horizontal_list_circle;
    private HorizontalListView horizontal_list_production;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_user_message);

        initView();

    }

    private void initView() {
        horizontal_list_circle = (HorizontalListView) findViewById(R.id.horizontal_list_circle);
        horizontal_list_production = (HorizontalListView) findViewById(R.id.horizontal_list_production);
        mButton = (Button) findViewById(R.id.update_data);


        mButton.setOnClickListener(this);

        String[] titles = {"怀师", "南怀瑾军校", "闭关", "南怀瑾", "南公庄严照", "怀师法相", "怀师法相", "怀师法相", "怀师法相", "怀师法相"};
        final int[] ids = {R.drawable.store_1, R.drawable.store_2,
                R.drawable.store_3, R.drawable.store_4,
                R.drawable.store_5, R.drawable.store_6,R.drawable.store_5, R.drawable.store_6,R.drawable.store_5, R.drawable.store_6};

        HorizontalListViewAdapter circleAdapter = new HorizontalListViewAdapter(this, titles,ids);

        horizontal_list_circle.setAdapter(circleAdapter);

        horizontal_list_circle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("圈子被点击了.." + position);
            }
        });


        int[] image = {R.drawable.home_catroon_1,R.drawable.home_catroon_3,R.drawable.home_catroon_2,R.drawable.home_catroon_1,
                R.drawable.home_catroon_3,R.drawable.home_catroon_1,R.drawable.home_catroon_2,R.drawable.home_catroon_2};
        String[] name = {"妖神记","女神纪元","白鹤三绝","斗罗大陆","斗破苍穹","冰火魔厨","遮天","绝世唐门"};
        String[] type = {"玄幻 武侠","古风 战斗","奇幻 武侠","战斗","武侠 玄幻","女生","男生","现代 都市"};

        ProductionAdapter productionAdapter = new ProductionAdapter(this, image,name,type);
        horizontal_list_production.setAdapter(productionAdapter);

        horizontal_list_production.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("作品被点击了.." + position);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_data:
                //跳转个人信息修改页
                Intent intent = new Intent(this,UpdateUserDataActivity.class);
                startActivity(intent);
                break;
        
            default:
                break;
        }
    }
}
