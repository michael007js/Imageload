package com.eagersoft.youzy.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sss.imageload.ImageloadManager;
import com.sss.imageload.frescoConfig.FrescoImagePipelineConfig;
import com.sss.imageload.imp.GlideImageLoad;

/**
 * Created by Administrator on 2018/6/27.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this, FrescoImagePipelineConfig.getDefaultImagePipelineConfig(this));
        ImageloadManager.Builder builder=new ImageloadManager.Builder()
                .setImageLoad(new GlideImageLoad());
        ImageloadManager.getInstance().build(builder);
    }
}
