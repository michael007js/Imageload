package com.eagersoft.youzy.ui.Main.Bean;

import android.net.Uri;

import com.sss.imageload.enums.ImageType;


/**
 * Created by Administrator on 2018/7/17.
 */

public class ImageBean {
    public Uri uri;
    public ImageType imageType;
    public String name;
    public boolean circle;
    public boolean isGif;

    public ImageBean(Uri uri,ImageType imageType, String name,boolean circle,boolean isGif) {
        this.uri = uri;
        this.imageType=imageType;
        this.name = name;
        this.circle=circle;
        this.isGif=isGif;
    }
}
