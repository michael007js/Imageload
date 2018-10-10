package com.sss.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.sss.imageload.dao.ImageLoad;
import com.sss.imageload.enums.CacheType;
import com.sss.imageload.imp.GlideImageLoad;
import com.sss.imageload.options.ImageloadOption;

import java.io.File;

/**
 * 图片加载管理者
 * Created by Administrator on 2018/6/27.
 */

public class ImageloadManager {
    //ImageloadManager实例
    private static ImageloadManager imageloadManager;
    //用户实现的图片加载类
    private ImageLoad imageLoad;
    //参数构造器
    private Builder builder;

    /**
     * 获取单例
     *
     * @return
     */
    public static ImageloadManager getInstance() {
        if (imageloadManager == null) {
            imageloadManager = new ImageloadManager();
        }
        return imageloadManager;
    }

    /**
     * 构造参数
     *
     * @param builder
     */
    public void build(Builder builder) {
        this.builder = builder;
    }

    /**
     * 检查用户参数是否设置正确
     */
    private void check() {
        if (builder != null) {
            if (builder.imageLoad != null) {
                imageLoad = builder.imageLoad;
            } else {
                imageLoad = new GlideImageLoad();
            }
        } else {
            throw new UnsupportedOperationException("未配置builder，请在Application中设置builder参数");
        }
    }

    /*************************************************设置图片加载地址↓*************************************************/
    public ImageloadOption load(Bitmap bitmap) {
        check();
        return new ImageloadOption(bitmap);
    }

    public ImageloadOption load(String path) {
        check();
        return new ImageloadOption(path);
    }

    public ImageloadOption load(int res) {
        check();
        return new ImageloadOption(res);
    }

    public ImageloadOption load(File file) {
        check();
        return new ImageloadOption(file);
    }

    public ImageloadOption load(Uri uri) {
        check();
        return new ImageloadOption(uri);
    }

    /*************************************************设置图片加载地址↑*************************************************/



    /**
     * 清除图片内存缓存
     */
    public void clearCache(Context context,CacheType cacheType) {
        check();
        imageLoad.clearCache(context, cacheType);
    }


    /**
     * 获取缓存大小
     */
    public void getCacheSize(Context context) {
        check();
        imageLoad.getCacheSize(context);
    }

    /**
     * 获取缓存目录
     */
    public void getCacheDir(Context context) {
        check();
        imageLoad.getCacheDir(context);
    }

    /**
     * 召唤神龙
     *
     * @param imageloadOption
     */
    public void displayImage(ImageloadOption imageloadOption,Context context) {
        check();
        imageLoad.displayImage(imageloadOption,context);
    }

    /**
     * 下载图片
     *
     * @param option
     */
    public void downLoadImage(Context context,ImageloadOption option) {
        check();
        imageLoad.downLoadImage(context,option);
    }

    /**
     * 测量图片
     *
     * @param option
     */
    public void measureImage(Context context,ImageloadOption option) {
        check();
        imageLoad.measureImage(context,option);
    }
    /**
     * 请求是否已经暂停
     */
    public boolean isPause(Context context) {
        check();
       return imageLoad.isPaused(context);
    }

    /**
     * 暂停请求
     * 在列表快速滑动时使用
     */
    public void pause(Context context) {
        check();
        imageLoad.pause(context);
    }

    /**
     * 恢复请求
     * 当滑动停止时使用
     */
    public void resume(Context context) {
        check();
        imageLoad.resume(context);
    }

    public static class Builder {
        private ImageLoad imageLoad;


        public Builder setImageLoad(ImageLoad imageLoad) {
            this.imageLoad = imageLoad;
            return this;
        }


    }
}
