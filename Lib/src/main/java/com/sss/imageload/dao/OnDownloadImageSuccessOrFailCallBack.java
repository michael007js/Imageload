package com.sss.imageload.dao;

import java.io.File;

/**
 * 图片下载回调
 * Created by Administrator on 2018/6/30.
 */

public interface OnDownloadImageSuccessOrFailCallBack {

    void onDownloadImageSuccess(File file);

    void onDownloadImageFail(Throwable failureCause);
}
