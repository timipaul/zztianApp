package com.timi.framedemo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.timi.framedemo.R;
import com.timi.framedemo.Utils.Utility;
import com.timi.framedemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
    空间商城 -- 故事
 */
public class StoreStoryFragment extends BaseFragment implements View.OnClickListener{
    private static final String TAG = StoreStoryFragment.class.getSimpleName();

    private EditText story_name;
    private TextView store_empty;
    private TextView store_button;
    private LinearLayout ll_story_place;
    private TextView story_label;

    private String[] areas = new String[]{"玄幻","科幻", "梦幻", "奇幻", "武侠", "现代", "都市" };
    private boolean[] areaState=new boolean[]{true, false, false, false, false, false,false };
    private ListView areaCheckListView;

    @Override
    protected View initView() {

        final View view = View.inflate(mContext, R.layout.store_story,null);
        story_name = (EditText) view.findViewById(R.id.story_create_name);
        store_empty = (TextView) view.findViewById(R.id.store_story_empty);
        store_button = (TextView) view.findViewById(R.id.store_story_button);
        ll_story_place = (LinearLayout) view.findViewById(R.id.story_add_label_place);
        story_label = (TextView) view.findViewById(R.id.story_add_label);

        store_empty.setOnClickListener(this);
        story_label.setOnClickListener(new CheckBoxClickListener());
        store_button.setOnClickListener(this);


        /*android下隐藏点击空白处隐藏小键盘  fragment下*/
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(getActivity().getCurrentFocus()!=null && getActivity().getCurrentFocus().getWindowToken()!=null){
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });

        story_name.addTextChangedListener(new TextWatcher() {
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
                    store_button.setEnabled(true);
                    store_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_but_compile_bg_press));
                } else{
                    store_button.setEnabled(false);
                    store_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_tab_compile_bg));
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.store_story_empty: //清空内容
                deleteEditContent();
                break;
            case R.id.store_story_button:    //点击提交
                System.out.println("点击了提交...");
                break;
        }
    }

    //清空edit里的内容  弹出确认框
    private void deleteEditContent() {
        // 创建退出对话框
        AlertDialog isExit = new AlertDialog.Builder(mContext).create();
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
                    story_name.setText("");
                    ll_story_place.removeAllViews();
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
            new AlertDialog.Builder(mContext).setTitle("选择区域").setItems(areas,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Toast.makeText(mContext, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }).show();
        }
    }

    class CheckBoxClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            AlertDialog ad = new AlertDialog.Builder(mContext)
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

                                Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
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
        ll_story_place.removeAllViews();
        //添加标签
        for (int i = 0; i < listMap.size(); i++) {

            TextView text = createtextView();
            Map getmap = (Map)listMap.get(i);
            String mapid = (String)getmap.get("id");
            String mapname = (String)getmap.get("name");
            int ids = Integer.parseInt(mapid);
            text.setId(ids);
            text.setText(mapname);
            ll_story_place.addView(text);
        }
    }

    //创建textview 视图
    private TextView createtextView(){

        TextView text = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utility.dpTopx(mContext,50),Utility.dpTopx(mContext,20));
        params.setMargins(5,Utility.dpTopx(mContext,5),0,0);
        text.setBackgroundResource(R.drawable.annular_label_bg);
        text.setTextColor(getResources().getColor(R.color.blue));
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(params);
        return text;
    }
}
