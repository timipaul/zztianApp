package com.timi.framedemo.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 从网络获取图片并且缓存
 */
public class GetHttpImg {

    private static Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
    private static Bitmap bitmap;
    private static Handler handler = new Handler();

    /**
     * 下载图片在显示
     *
     *  handler UI线程 handler
     * @param view  需要显示图片的 ImageView
     * @param url 图片的网络地址
     */
    public static void setUserImg(final ImageView view, final  String url) {
        Log.i("slack", url);
        bitmap = null;
        //从缓存中取软引用的Bitmap对象
        SoftReference<Bitmap> bitmapcache_ = imageCache.get(url);
        //取出Bitmap对象，如果由于内存不足Bitmap被回收，将取得空
        if(bitmapcache_ != null){
            bitmap = bitmapcache_.get();
        }

        if(bitmap != null){
            Log.i("slack", "bitmap_ != null");
            changeView(view,bitmap);
        }else{
            new Thread(new Runnable() {

                @Override
                public void run() {
                    InputStream inputStream = null;
                    ByteArrayOutputStream out = null;
                    try {
                        URL localURL = new URL(url);

                        URLConnection connection = localURL.openConnection();
                        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

                        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
                        httpURLConnection.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");

                        if (httpURLConnection.getResponseCode() >= 300) {
                            Log.i("slack","error:"+httpURLConnection.getResponseCode());
                            throw new Exception(
                                    "HTTP Request is not success, Response code is "
                                            + httpURLConnection.getResponseCode());
                        }
                        inputStream = httpURLConnection.getInputStream();

                        out = new ByteArrayOutputStream();
                        byte[] buffer=new byte[1024];
                        int n=0;
                        while ( (n=inputStream.read(buffer)) !=-1) {
                            out.write(buffer,0,n);
                        }
                        //本地缓存图片
                        out.close();
                        //强引用的Bitmap对象
                        bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.toByteArray().length);
                        //软引用的Bitmap对象
                        SoftReference<Bitmap> bitmapcache = new SoftReference<Bitmap>(bitmap);
                        //添加该对象到Map中使其缓存
                        imageCache.put(url,bitmapcache);

                        changeView(view,bitmap);


                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.i("slack","error:"+e.toString());
                    }finally{
                        try {

                            inputStream.close();
                            out.close();


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }


                }


            }).start();
        }


    }

    private static void changeView(final ImageView view,final Bitmap bitmap) {

        handler.post(
                new Runnable() {

                    @Override
                    public void run() {

                        view.setImageBitmap( bitmap );
                    }
                });

    }
}
