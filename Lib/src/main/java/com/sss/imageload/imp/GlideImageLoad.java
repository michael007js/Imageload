package com.sss.imageload.imp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;
import com.sss.imageload.dao.ImageLoad;
import com.sss.imageload.dao.OnDownloadImageSuccessOrFailCallBack;
import com.sss.imageload.enums.CacheType;
import com.sss.imageload.enums.ImageType;
import com.sss.imageload.options.ImageloadOption;
import com.sss.imageload.progress.ProgressHelper;
import com.sss.imageload.utils.ImageloadUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

/**
 * Glide实现类
 * Created by Administrator on 2018/6/27.
 */
@SuppressWarnings("ALL")
public class GlideImageLoad extends ImageLoad {

    /**
     * 显示图片
     */
    @Override
    public void displayImage(final ImageloadOption option) {
        //请求设置
        RequestOptions requestOptions = new RequestOptions();
        RequestBuilder requestBuilder = null;
        RequestManager requestManager = Glide.with(option.getTarget().getContext());
        if (option.isGif()) requestManager.asGif();
        //图片地址
        if (option.getUri() != null) {
            requestBuilder = requestManager.load(option.getUri());
                if (option.getUri().getPath()!=null){
                    if ("http://".equals(option.getUri().getPath())||"https://".equals(option.getUri().getPath())){
                        ProgressHelper.getInstance().addListener(option.getPath(), option.getOnProgressCallBack());
                    }
                }
        } else if (option.getPath() != null) {
            requestBuilder = requestManager.load(option.getPath());
            ProgressHelper.getInstance().addListener(option.getPath(), option.getOnProgressCallBack());
        } else if (option.getRes() != 0) {
            requestBuilder = requestManager.load(option.getRes());
        } else if (option.getFile() != null) {
            requestBuilder = requestManager.load(option.getFile());
        }
        //设置请求监听
        requestBuilder.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (option.getOnImageloadSuccessOrFailCallBack() != null) {
                    option.getOnImageloadSuccessOrFailCallBack().onFail(option.getTarget(), e);
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        });
        //设置图片显示类型
        BitmapTransformation bitmapTransformation = new CenterCrop();
        if (option.isFitCenter()) {
            bitmapTransformation = new FitCenter();
        } else if (option.isCenterCrop()) {
            bitmapTransformation = new CenterCrop();
        } else if (option.isCircleCrop()) {
            bitmapTransformation = new CircleCrop();
        }

        //圆角
        RoundedCorners roundedCorners = new RoundedCorners(((int) option.getRoundAngle() > 0 ? (int) option.getRoundAngle() : 0));
        //设置图片显示效果
        if (option.getImageType() == ImageType.Mask && option.getImageTypeOption().getMask() != 0) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation, new MaskTransformation(option.getImageTypeOption().getMask()), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.NinePatchMask && option.getImageTypeOption().getNinePatchMask() != 0) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation, new MaskTransformation(option.getImageTypeOption().getNinePatchMask()), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.ColorFilter) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new ColorFilterTransformation(Color.argb(option.getImageTypeOption().getColorFilterA(),
                            option.getImageTypeOption().getColorFilterR(),
                            option.getImageTypeOption().getColorFilterG(), option.getImageTypeOption().getColorFilterB())), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Grayscale) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new GrayscaleTransformation(), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Blur) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new BlurTransformation(option.getImageTypeOption().getBlur()), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Toon) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new ToonFilterTransformation(option.getImageTypeOption().getToonThreshold(), option.getImageTypeOption().getToonQuantizationLevels()), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Sepia) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new SepiaFilterTransformation(option.getImageTypeOption().getSepia()), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Contrast) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new ContrastFilterTransformation(option.getImageTypeOption().getContrast()), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Invert) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new InvertFilterTransformation(), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Pixel) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new PixelationFilterTransformation(option.getImageTypeOption().getPixel()), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Sketch) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new SketchFilterTransformation(), roundedCorners);
            requestOptions.transform(multiTransformation);
        } else if (option.getImageType() == ImageType.Swirl) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new SwirlFilterTransformation(option.getImageTypeOption().getSwirlRadius(), option.getImageTypeOption().getSwirlAngle(),
                            new PointF(option.getImageTypeOption().getSwirlCenterX(), option.getImageTypeOption().getSwirlCenterY())), roundedCorners);
            requestOptions.transform(multiTransformation).dontAnimate();
        } else if (option.getImageType() == ImageType.Brightness) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new BrightnessFilterTransformation(option.getImageTypeOption().getBrightness()), roundedCorners);
            requestOptions.transform(multiTransformation).dontAnimate();
        } else if (option.getImageType() == ImageType.Kuawahara) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new KuwaharaFilterTransformation(option.getImageTypeOption().getKuawahara()), roundedCorners);
            requestOptions.transform(multiTransformation).dontAnimate();
        } else if (option.getImageType() == ImageType.Vignette) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new VignetteFilterTransformation(new PointF(option.getImageTypeOption().getVignetteCenterX(), option.getImageTypeOption().getVignetteCenterY()),
                            new float[]{option.getImageTypeOption().getVignetteColorX(), option.getImageTypeOption().getVignetteColorY(),
                                    option.getImageTypeOption().getVignetteColorZ()},
                            option.getImageTypeOption().getVignetteStart(), option.getImageTypeOption().getVignetteEnd()), roundedCorners);
            requestOptions.transform(multiTransformation).dontAnimate();
        } else if (option.getImageType() == ImageType.BlurAndGrayscale) {
            MultiTransformation multiTransformation = new MultiTransformation<>(
                    bitmapTransformation,
                    new GrayscaleTransformation(),
                    new BlurTransformation(option.getImageTypeOption().getBlur())
                    , roundedCorners);

            requestOptions.transform(multiTransformation);
        } else {
            //设置图片显示类型和显示效果
            requestOptions.transform(new MultiTransformation<>(bitmapTransformation, roundedCorners));
        }

        //设置图片尺寸
        if (option.getImageWidth() > 0 && option.getImageHeight() > 0) {
            requestOptions.override((int) ImageloadUtils.dp2px(option.getImageWidth()), (int) ImageloadUtils.dp2px(option.getImageHeight()));
        }
        //跳过内存缓存
        requestOptions.skipMemoryCache(true);

        //渐进式加载
        if (option.isThumbnail()) {
            requestBuilder.thumbnail(0.1f);
        }
        //出错图
        if (option.getErrorImageInt() != 0) {
            requestOptions.error(option.getErrorImageInt());
        } else {
            if (option.getErrorImageDrawable() != null) {
                requestOptions.error(option.getErrorImageDrawable());
            }
        }

        //占位图
        if (option.getPlacesHolderImageInt() != 0) {
            requestOptions.placeholder(option.getPlacesHolderImageInt());
        } else {
            if (option.getPlacesHolderDrawable() != null) {
                requestOptions.error(option.getPlacesHolderDrawable());
            }
        }

        //渐隐渐现
        if (option.getDuration() > 0) {
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade(new DrawableCrossFadeFactory.Builder(option.getDuration()).setCrossFadeEnabled(true)));
        }
        //召唤神龙
        requestBuilder.apply(requestOptions);
        requestBuilder.into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (option.getOnImageloadSuccessOrFailCallBack() != null) {
                    option.getOnImageloadSuccessOrFailCallBack().onSuccess(((BitmapDrawable) resource).getBitmap().getWidth(), ((BitmapDrawable) resource).getBitmap().getHeight());
                }
            }
        });
        requestBuilder.into((ImageView) option.getTarget());
    }

    /**
     * 下载图片
     *
     * @param option
     */
    @Override
    public void downLoadImage(Context context, ImageloadOption option) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new UnsupportedOperationException("请勿在主线程中下载!");
        }
        if (option.getPath() == null || "".equals(option.getPath()) || !option.getPath().startsWith("http") && !option.getPath().startsWith("https")) {
            if (option.getOnDownloadImageSuccessOrFailCallBack() != null) {
                option.getOnDownloadImageSuccessOrFailCallBack().onDownloadImageFail(new Throwable("要下载的图片地址为空！"));
            }
            return;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        FutureTarget<File> future = Glide.with(context)
                .load(option.getPath())
                .downloadOnly(option.getImageWidth() > 0 ? option.getImageWidth() : outMetrics.widthPixels, option.getImageHeight() > 0 ? option.getImageHeight() : outMetrics.heightPixels);
        try {
            File file = future.get();
            if (option.isNotifyUpdateGallery()) {
                ImageloadUtils.notifyUpdateGallery(context, file, (option.getDownloadFileName() == null || "".equals(option.getDownloadFileName()) ? System.currentTimeMillis() + "" : option.getDownloadFileName()));
            }
            if (option.getOnDownloadImageSuccessOrFailCallBack() != null) {
                option.getOnDownloadImageSuccessOrFailCallBack().onDownloadImageSuccess(file);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (option.getOnDownloadImageSuccessOrFailCallBack() != null) {
                option.getOnDownloadImageSuccessOrFailCallBack().onDownloadImageFail(e.getCause());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            if (option.getOnDownloadImageSuccessOrFailCallBack() != null) {
                option.getOnDownloadImageSuccessOrFailCallBack().onDownloadImageFail(e.getCause());
            }
        }
    }

    /**
     * 测量图片
     *
     * @param context
     * @param option
     */
    @Override
    public void measureImage(Context context, final ImageloadOption option) {
        option.setDownloadFileName(System.currentTimeMillis() + "");
        option.setOnDownloadImageSuccessOrFailCallBack(new OnDownloadImageSuccessOrFailCallBack() {
            @Override
            public void onDownloadImageSuccess(File file) {
                WeakReference<Bitmap> bitmapWeakReference = new WeakReference<>(BitmapFactory.decodeFile(file.getAbsolutePath()));
                if (bitmapWeakReference.get() != null) {
                    if (option.getOnMeasureImageSizeCallBack() != null) {
                        option.getOnMeasureImageSizeCallBack().onMeasureImageSize(bitmapWeakReference.get().getWidth(), bitmapWeakReference.get().getHeight());
                    }
                } else {
                    if (option.getOnMeasureImageSizeCallBack() != null) {
                        option.getOnMeasureImageSizeCallBack().onMeasureImageFail(new RuntimeException("GC is running,I has dead!"));
                    }
                }
                bitmapWeakReference.clear();
            }

            @Override
            public void onDownloadImageFail(Throwable failureCause) {
                if (option.getOnMeasureImageSizeCallBack() != null) {
                    option.getOnMeasureImageSizeCallBack().onMeasureImageFail(failureCause);
                }
            }
        });


        downLoadImage(context, option);
    }

    /**
     * 获取缓存目录
     *
     * @param context
     * @return
     */
    @Override
    public File getCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    @Override
    public long getCacheSize(Context context) {
        return Glide.getPhotoCacheDir(context) == null ? 0 : ImageloadUtils.getFolderSize(Glide.getPhotoCacheDir(context));
    }

    /**
     * 清理缓存
     *
     * @param context
     * @param cacheType
     */
    @Override
    public void clearCache(final Context context, CacheType cacheType) {
        if (cacheType == CacheType.Disk) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Glide.get(context).clearDiskCache();
                }
            }.start();
        } else if (cacheType == CacheType.Memory) {
            Glide.get(context).clearMemory();
        } else if (cacheType == CacheType.All) {
            Glide.get(context).clearMemory();
           new Thread(){
               @Override
               public void run() {
                   super.run();
                   Glide.get(context).clearDiskCache();
               }
           }.start();

        }
    }

    /**
     * 请求是否已经暂停
     *
     * @return
     */
    @Override
    public boolean isPaused(Context context) {
        return Glide.with(context).isPaused();
    }


    /**
     * 暂停请求
     * 在列表快速滑动时使用
     */
    @Override
    public void pause(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复请求
     * 当滑动停止时使用
     */
    @Override
    public void resume(Context context) {
        Glide.with(context).resumeRequests();
    }

}
