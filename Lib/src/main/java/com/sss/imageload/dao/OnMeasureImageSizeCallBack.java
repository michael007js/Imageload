package com.sss.imageload.dao;

/**
 * 测量图片尺寸
 * Created by Administrator on 2018/8/16.
 */

public interface OnMeasureImageSizeCallBack {

    void onMeasureImageSize(int imageWidth, int imageHeight);

    void onMeasureImageFail(Throwable throwable);
}
