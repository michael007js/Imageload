package com.sss.imageload.dao;

import android.view.View;

/**
 * Created by Administrator on 2018/6/27.
 */

public interface OnImageloadSuccessOrFailCallBack {

    void onSuccess(int imageWidth,int imageHeight);

    void onFail(View view, Exception e);
}
