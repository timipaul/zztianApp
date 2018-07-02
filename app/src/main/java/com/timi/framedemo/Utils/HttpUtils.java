package com.timi.framedemo.Utils;

import android.util.Log;

import com.timi.framedemo.AppConstants;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装的网络请求
 */
public class HttpUtils {

    OkHttpClient client = new OkHttpClient();
    Map mMap = null;


    public String OkhttpPost(String src, RequestBody formBody) throws IOException {
        System.out.println("请求地址：" + AppConstants.UrlHead+src);

        Request request = new Request.Builder()
                //.addHeader("cookie",sessionId)
                .url(AppConstants.UrlHead+src)
                .post(formBody)
                .build();

        /*Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                System.out.println("请求失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功
                System.out.println("请求成功" + response);
                try {
                    JSONObject jsonObject = JSONObject.fromObject(response.body().string());
                    String sjson = jsonObject.getString("result");
                    //解密
                    result = CXAESUtil.decrypt("zztian999", sjson);
                    //jsonArray = JSONArray.fromObject(decryptString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

        Response response = client.newCall(request).execute();
        String result = null;
        try {
            JSONObject jsonObject = JSONObject.fromObject(response.body().string());
            String status = jsonObject.getString("status");
            System.out.println("请求状态信息" + status);
            String sjson = jsonObject.getString("result");
            //解密
            //result = CXAESUtil.decrypt(AppConstants.CXAES, sjson);
            System.out.println("sjson-----" + sjson);
            result = SymmetricEncoder1.aesDecryptString(sjson,"ZIZAITIAN@666666");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;


    }

    /**
     * @return 1表示提示信息 2表示返回数据
     */
    public Map OkhttpPostList(String src, RequestBody formBody) throws IOException {
        mMap = new HashMap<>();
        Request request = new Request.Builder()
                .url(AppConstants.UrlHead+src)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        String result = null;
        try {
            JSONObject jsonObject = JSONObject.fromObject(response.body().string());
            String status = jsonObject.getString("status");

            System.out.println("请求状态信息" + jsonObject);
            String sjson = jsonObject.getString("result");
            mMap.put("status",status);
            if(sjson != null){
                //解密
                result = CXAESUtil.decrypt(AppConstants.CXAES, sjson);
                mMap.put("result",result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMap;
    }

    public String OkhttpGet(String src) {
        String result = null;
        //1,创建OKHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //2,创建一个Request
        Request request = new Request.Builder().url(AppConstants.UrlHead + src).build();
        //3,创建一个call对象
        Call call = mOkHttpClient.newCall(request);
        //4,将请求添加到调度中
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String mes = response.body().string();
                    System.out.println("mes:" + mes);
                    Headers headers = response.headers();
                    Log.i("info_respons.headers",headers+"");
                    System.out.println("-------------------"  + headers);
                }
            }
        });

        return result;
    }
}


