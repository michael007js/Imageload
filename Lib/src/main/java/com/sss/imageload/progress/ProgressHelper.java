package com.sss.imageload.progress;

import android.text.TextUtils;

import com.sss.imageload.dao.OnProgressCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * Glide图片加载实时进度监听帮助类
 * Created by Administrator on 2018/8/16.
 */

public class ProgressHelper {
    private static ProgressHelper progressHelper;
    //用来存放监听的容器
    private Map<String, OnProgressCallBack> listenersMap = new HashMap<>();

    /**
     * 实例化
     *
     * @return
     */
    public static ProgressHelper getInstance() {
        if (progressHelper == null) {
            progressHelper = new ProgressHelper();
        }
        return progressHelper;
    }

    /**
     * 获取已添加的监听
     *
     * @param url
     * @return
     */
    public OnProgressCallBack getOnProgressCallBack(String url) {
        if (TextUtils.isEmpty(url) || listenersMap == null || listenersMap.size() == 0) {
            return null;
        }

        OnProgressCallBack listenerWeakReference = listenersMap.get(url);
        if (listenerWeakReference != null) {
            return listenerWeakReference;
        }
        return null;
    }

    /**
     * 添加监听
     *
     * @param url
     * @param listener
     */
    public void addListener(String url, OnProgressCallBack listener) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            listenersMap.put(url, listener);
        }
    }

    /**
     * 移除监听
     *
     * @param url
     */
    public void removeListener(String url) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap.remove(url);
        }
    }
}
