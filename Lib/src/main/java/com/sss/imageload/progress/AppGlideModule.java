package com.sss.imageload.progress;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.sss.imageload.progress.okhttp.OkHttpUrlLoader;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/10/10.
 */

public class AppGlideModule  implements GlideModule {
    public static final int GLIDE_DISK_SIZE = 1024 * 1024 * 1000;

    // 图片缓存子目录
    public static final String GLIDE_DISK = Environment.getExternalStorageDirectory().getPath() + "/ImageCache";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskLruCacheFactory(GLIDE_DISK, GLIDE_DISK_SIZE));
        builder.setLogLevel(Log.DEBUG);
    }
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }
}
