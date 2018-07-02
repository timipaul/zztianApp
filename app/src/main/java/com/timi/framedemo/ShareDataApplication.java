package com.timi.framedemo;

import android.support.multidex.MultiDexApplication;

import java.util.LinkedHashMap;

/**
 *  编辑器数据共享类
 */
public class ShareDataApplication extends MultiDexApplication {

    private LinkedHashMap dataList;

    public void setDataList(LinkedHashMap<Integer, int[]> dataList) {
        this.dataList = dataList;
    }

    public LinkedHashMap<Integer, int[]> getDataList() {
        return dataList;
    }

    public void removeDataList(){this.dataList = new LinkedHashMap();}
}
