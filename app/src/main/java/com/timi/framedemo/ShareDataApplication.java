package com.timi.framedemo;

import android.support.multidex.MultiDexApplication;

import java.util.LinkedHashMap;

/**
 *
 */
public class ShareDataApplication extends MultiDexApplication {

    private LinkedHashMap<Integer,int[]> dataList;

    public void setDataList(LinkedHashMap<Integer, int[]> dataList) {
        this.dataList = dataList;
    }

    public LinkedHashMap<Integer, int[]> getDataList() {
        return dataList;
    }
}
