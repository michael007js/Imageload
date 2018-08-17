package com.sss.imageload.dao;

/**
 * 图片实时加载进度回调
 * 只在加载网络图片的时候有效
 * Created by Administrator on 2018/8/16.
 */

public interface OnProgressCallBack {
    void onProgress( int percentage);
}
