package com.sss.imageload.dao;

import android.content.Context;

import com.sss.imageload.enums.CacheType;
import com.sss.imageload.options.ImageloadOption;

import java.io.File;

/**
 * 图片加载抽象类
 * Created by Administrator on 2018/6/27.
 */

public abstract class ImageLoad {

    /**
     * 显示图片
     * @param option
     */
    public abstract void displayImage(ImageloadOption option);


    /**
     * 下载图片
     * @param option
     */
    public abstract void downLoadImage(Context context,ImageloadOption option);

    /**
     * 获取缓存目录
     * @param context
     * @return
     */
    public abstract File getCacheDir(Context context);

    /**
     * 获取缓存大小
     * @param context
     * @return
     */
    public abstract long getCacheSize(Context context);

    /**
     * 清理缓存
     * @param context
     * @param cacheType
     */
    public abstract void clearCache(Context context, CacheType cacheType);


    /**
     * 请求是否已经暂停
     * @return
     */
    public abstract boolean isPaused(Context context);

    /**
     * 暂停请求
     * 在列表快速滑动时使用
     */
    public abstract void pause(Context context);
    /**
     * 恢复请求
     * 当滑动停止时使用
     */
    public abstract void resume(Context context);



}
