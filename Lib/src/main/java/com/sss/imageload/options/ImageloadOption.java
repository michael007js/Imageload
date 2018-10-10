package com.sss.imageload.options;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.sss.imageload.ImageloadManager;
import com.sss.imageload.R;
import com.sss.imageload.dao.OnDownloadImageSuccessOrFailCallBack;
import com.sss.imageload.dao.OnImageloadSuccessOrFailCallBack;
import com.sss.imageload.dao.OnMeasureImageSizeCallBack;
import com.sss.imageload.dao.OnProgressCallBack;
import com.sss.imageload.enums.ImageType;
import com.sss.imageload.widget.ImageloadView;

import java.io.File;

/**
 * 图片加载参数配置类
 * Created by Administrator on 2018/6/27.
 */

public class ImageloadOption {
    private OnProgressCallBack onProgressCallBack;//图片实时加载进度回调(只在加载网络图片的时候有效)
    private OnMeasureImageSizeCallBack onMeasureImageSizeCallBack;//测量图片尺寸回调
    private OnImageloadSuccessOrFailCallBack onImageloadSuccessOrFailCallBack;//图片加载回调
    private OnDownloadImageSuccessOrFailCallBack onDownloadImageSuccessOrFailCallBack;//图片下载回调
    private ImageType imageType;//图片显示效果
    private ImageTypeOption imageTypeOption = new ImageTypeOption();//提供一个默认的图片显示效果配置类，用户可以自定义
    private float roundAngle = 5f;//圆角
    private int roundAngleColor = R.color.white;//圆角颜色，fresco专用且必须使用引用，如果使用非引用的颜色的话Fresco会报错！如果使用fresco框架，且设置圆角的话则必须设置此参数
    private int duration = 0;//淡入淡出时间
    private boolean isGif;//是否为GIF
    private boolean thumbnail = true;//渐进式加载
    private int imageWidth;//宽度(bitmap)
    private int imageHeight;//图片高度(bitmap)
    private int placesHolderImageInt;//占位图(int类)
    private Drawable placesHolderDrawable;//出错图(drawable类)
    private int errorImageInt;//出错图(int类)
    private Drawable errorImageDrawable;//出错图(drawable类)
    private boolean fitCenter;//自适应控件, 不剪裁 ,在不超过控件的前提下,等比 缩放 到 最大 ,居中显示
    private boolean circleCrop;//裁剪为圆
    private boolean centerCrop;//以填满整个控件为目标,等比缩放,超过控件时将被 裁剪 ( 宽高都要填满 ,所以只要图片宽高比与控件宽高比不同时,一定会被剪裁)
    private Bitmap bitmap;//位图
    private String path;//网络路径
    private int res;//资源路径
    private File file;//文件路径
    private Uri uri;//uri路径
    private String downloadFileName;//图片下载完成后要储存的名称
    private boolean notifyUpdateGallery;//图片下载完成后是否要通知图库更新
    private ImageloadView view;//要显示的图片View,至于为什么是这个类型的View而不是ImageView，请参考该类中的说明


    /*************************************************设置参数↓*************************************************/

    public ImageloadOption(String path) {
        this.path = path;
    }

    public ImageloadOption(int res) {
        this.res = res;
    }

    public ImageloadOption(File file) {
        this.file = file;
    }

    public ImageloadOption(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public int getRes() {
        return res;
    }

    public File getFile() {
        return file;
    }

    public Uri getUri() {
        return uri;
    }

    public ImageloadOption setOnProgressCallBack(OnProgressCallBack onProgressCallBack) {
        this.onProgressCallBack = onProgressCallBack;
        return this;
    }

    public OnProgressCallBack getOnProgressCallBack() {
        return onProgressCallBack;
    }

    public OnMeasureImageSizeCallBack getOnMeasureImageSizeCallBack() {
        return onMeasureImageSizeCallBack;
    }

    public ImageloadOption setOnMeasureImageSizeCallBack(OnMeasureImageSizeCallBack onMeasureImageSizeCallBack) {
        this.onMeasureImageSizeCallBack = onMeasureImageSizeCallBack;
        return this;
    }

    public OnDownloadImageSuccessOrFailCallBack getOnDownloadImageSuccessOrFailCallBack() {
        return onDownloadImageSuccessOrFailCallBack;
    }

    public ImageloadOption setOnDownloadImageSuccessOrFailCallBack(OnDownloadImageSuccessOrFailCallBack onDownloadImageSuccessOrFailCallBack) {
        this.onDownloadImageSuccessOrFailCallBack = onDownloadImageSuccessOrFailCallBack;
        return this;
    }

    public ImageloadOption setOnImageloadSuccessOrFailCallBack(OnImageloadSuccessOrFailCallBack onImageloadSuccessOrFailCallBack) {
        this.onImageloadSuccessOrFailCallBack = onImageloadSuccessOrFailCallBack;
        return this;
    }

    public OnImageloadSuccessOrFailCallBack getOnImageloadSuccessOrFailCallBack() {
        return onImageloadSuccessOrFailCallBack;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public ImageloadOption setImageType(ImageType imageType) {
        this.imageType = imageType;
        return this;
    }

    public ImageTypeOption getImageTypeOption() {
        return imageTypeOption;
    }

    public ImageloadOption setImageTypeOption(ImageTypeOption imageTypeOption) {
        this.imageTypeOption = imageTypeOption;
        return this;
    }

    public float getRoundAngle() {
        return roundAngle;
    }

    public ImageloadOption setRoundAngle(float roundAngle) {
        this.roundAngle = roundAngle;
        return this;
    }

    public ImageloadOption circleCrop() {
        circleCrop = true;
        fitCenter = false;
        centerCrop = false;
        return this;
    }

    public ImageloadOption fitCenter() {
        circleCrop = false;
        fitCenter = true;
        centerCrop = false;
        return this;
    }

    public ImageloadOption centerCrop() {
        circleCrop = false;
        fitCenter = false;
        centerCrop = true;
        return this;
    }

    public boolean isFitCenter() {
        return fitCenter;
    }


    public boolean isCircleCrop() {
        return circleCrop;
    }

    public boolean isCenterCrop() {
        return centerCrop;
    }

    public boolean isThumbnail() {
        return thumbnail;
    }

    public ImageloadOption setThumbnail(boolean thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public ImageloadOption size(int imageWidth, int imageHeight) {
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        return this;
    }

    public int getPlacesHolderImageInt() {
        return placesHolderImageInt;
    }

    public ImageloadOption setPlacesHolderImageInt(int placesHolderImageInt) {
        this.placesHolderImageInt = placesHolderImageInt;
        return this;
    }

    public Drawable getPlacesHolderDrawable() {
        return placesHolderDrawable;
    }

    public ImageloadOption setPlacesHolderDrawable(Drawable placesHolderDrawable) {
        this.placesHolderDrawable = placesHolderDrawable;
        return this;
    }

    public int getErrorImageInt() {
        return errorImageInt;
    }

    public ImageloadOption setErrorImageInt(int errorImageInt) {
        this.errorImageInt = errorImageInt;
        return this;
    }

    public Drawable getErrorImageDrawable() {
        return errorImageDrawable;
    }

    public ImageloadOption setErrorImageDrawable(Drawable errorImageDrawable) {
        this.errorImageDrawable = errorImageDrawable;
        return this;
    }

    public ImageloadView getTarget() {
        return view;
    }

    public int getDuration() {
        return duration;
    }

    public ImageloadOption setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public ImageloadOption setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
        return this;
    }

    public boolean isNotifyUpdateGallery() {
        return notifyUpdateGallery;
    }

    public ImageloadOption setNotifyUpdateGallery(boolean notifyUpdateGallery) {
        this.notifyUpdateGallery = notifyUpdateGallery;
        return this;
    }


    public int getRoundAngleColor() {
        return roundAngleColor;
    }

    public ImageloadOption setRoundAngleColor(int roundAngleColor) {
        this.roundAngleColor = roundAngleColor;
        return this;
    }

    public boolean isGif() {
        return isGif;
    }

    public ImageloadOption setGif(boolean gif) {
        isGif = gif;
        return this;
    }
/*************************************************设置参数↑*************************************************/
    /**
     * 下载图片
     */
    public void downLoadImage(Context context) {
        ImageloadManager.getInstance().downLoadImage(context, this);
    }

    /**
     * 测量图片
     *
     * @param context
     */
    public void measureImage(Context context) {
        ImageloadManager.getInstance().measureImage(context, this);
    }

    /**
     * 召唤神龙
     *
     * @param view
     */
    public void into(ImageloadView view,Context context) {
        this.view = view;
        ImageloadManager.getInstance().displayImage(this,context);

    }


}