package com.eagersoft.youzy.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.request.target.ViewTarget;
import com.eagersoft.youzy.imageload.R;
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
        ViewTarget.setTagId(R.id.glide_tag);
        Fresco.initialize(this, FrescoImagePipelineConfig.getDefaultImagePipelineConfig(this));
        ImageloadManager.Builder builder=new ImageloadManager.Builder()
                .setImageLoad(new GlideImageLoad());
        ImageloadManager.getInstance().build(builder);
    }
}
