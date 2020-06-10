package com.xuhao.myapp.utills;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.xuhao.myapp.QiYuApplication;

import java.util.HashMap;
import java.util.Map;

public class ImageUtiles {

    private static Map<String, Bitmap> imgCache = new HashMap<>();
    private static ImageView imageView;

    public static Bitmap getImg(String url) {
        return imgCache.get(url);
    }

    public static void putImg(String url, Bitmap bitmap) {
        imgCache.put(url, bitmap);
    }

    /**
     * 加载制定地址的图片
     *
     * @param url           图片地址
     * @param listener      加载成功监听
     * @param errorListener 加载失败监听
     */
    public static void loadInternetImg(String url, Response.Listener<Bitmap> listener, Response.ErrorListener errorListener) {
        //非空检查
        if (url == null) {
            return;
        }
        //网络加载
        ImageRequest imageRequest = new ImageRequest(url, listener, 0, 0, Bitmap.Config.RGB_565, errorListener);
        QiYuApplication.queue.add(imageRequest);
    }


}
