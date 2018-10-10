package com.sss.imageload.imp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.DrawableUtils;
import com.sss.imageload.dao.ImageLoad;
import com.sss.imageload.dao.OnDownloadImageSuccessOrFailCallBack;
import com.sss.imageload.enums.CacheType;
import com.sss.imageload.enums.ImageType;
import com.sss.imageload.frescoConfig.FrescoImagePipelineConfig;
import com.sss.imageload.options.ImageloadOption;
import com.sss.imageload.utils.ImageloadUtils;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.File;
import java.lang.ref.WeakReference;

import jp.wasabeef.fresco.processors.BlurPostprocessor;
import jp.wasabeef.fresco.processors.ColorFilterPostprocessor;
import jp.wasabeef.fresco.processors.CombinePostProcessors;
import jp.wasabeef.fresco.processors.GrayscalePostprocessor;
import jp.wasabeef.fresco.processors.MaskPostprocessor;
import jp.wasabeef.fresco.processors.gpu.BrightnessFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.ContrastFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.InvertFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.KuawaharaFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.PixelationFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SepiaFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SketchFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SwirlFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.ToonFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.VignetteFilterPostprocessor;

/**
 * Created by Administrator on 2018/6/28.
 */

public class FrescoImageLoad extends ImageLoad {

    /**
     * 显示图片
     */
    @Override
    public void displayImage(final ImageloadOption option,Context context) {
        //解析图片地址
        Uri uri = null;
        if (option.getUri() != null) {
            uri = option.getUri();
        } else if (option.getPath() != null) {
            uri = Uri.parse(option.getPath());
        } else if (option.getRes() != 0) {
            uri = Uri.parse("res://" + option.getTarget().getContext().getPackageName() + "/" + option.getRes());
        } else if (option.getFile() != null) {
            uri = Uri.fromFile(option.getFile());
        }else if (option.getBitmap()!=null){
             uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), option.getBitmap(), null,null));
        }
        //设置bitmap尺寸
        ResizeOptions resizeOptions = null;
        if (option.getImageWidth() > 0 && option.getImageHeight() > 0) {
            resizeOptions = new ResizeOptions(option.getImageWidth(), option.getImageHeight());
        }
        //设置图片显示效果
        Postprocessor processor = null;
        if (option.getImageType() == ImageType.Mask && option.getImageTypeOption().getMask() != 0) {
            processor = new MaskPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getMask());
        } else if (option.getImageType() == ImageType.NinePatchMask && option.getImageTypeOption().getNinePatchMask() != 0) {
            processor = new MaskPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getNinePatchMask());
        } else if (option.getImageType() == ImageType.ColorFilter) {
            processor = new ColorFilterPostprocessor(Color.argb(option.getImageTypeOption().getColorFilterA(), option.getImageTypeOption().getColorFilterR(),
                    option.getImageTypeOption().getColorFilterG(), option.getImageTypeOption().getColorFilterB()));
        } else if (option.getImageType() == ImageType.Grayscale) {
            processor = new GrayscalePostprocessor();
        } else if (option.getImageType() == ImageType.Blur) {
            processor = new BlurPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getBlur());
        } else if (option.getImageType() == ImageType.Toon) {
            processor = new ToonFilterPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getToonThreshold(), option.getImageTypeOption().getToonQuantizationLevels());
        } else if (option.getImageType() == ImageType.Sepia) {
            processor = new SepiaFilterPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getSepia());
        } else if (option.getImageType() == ImageType.Contrast) {
            processor = new ContrastFilterPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getContrast());
        } else if (option.getImageType() == ImageType.Invert) {
            processor = new InvertFilterPostprocessor(option.getTarget().getContext());
        } else if (option.getImageType() == ImageType.Pixel) {
            processor = new PixelationFilterPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getPixel());
        } else if (option.getImageType() == ImageType.Sketch) {
            processor = new SketchFilterPostprocessor(option.getTarget().getContext());
        } else if (option.getImageType() == ImageType.Swirl) {
            processor = new SwirlFilterPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getSwirlRadius(), option.getImageTypeOption().getSwirlAngle(),
                    new PointF(option.getImageTypeOption().getSwirlCenterX(), option.getImageTypeOption().getSwirlCenterY()));
        } else if (option.getImageType() == ImageType.Brightness) {
            processor = new BrightnessFilterPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getBrightness());
        } else if (option.getImageType() == ImageType.Kuawahara) {
            processor = new KuawaharaFilterPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getKuawahara());
        } else if (option.getImageType() == ImageType.Vignette) {
            processor = new VignetteFilterPostprocessor(option.getTarget().getContext(), new PointF(option.getImageTypeOption().getVignetteCenterX(), option.getImageTypeOption().getVignetteCenterY()),
                    new float[]{option.getImageTypeOption().getVignetteColorX(), option.getImageTypeOption().getVignetteColorY(), option.getImageTypeOption().getVignetteColorZ()}, option.getImageTypeOption().getVignetteStart(), option.getImageTypeOption().getVignetteEnd());
        } else if (option.getImageType() == ImageType.BlurAndGrayscale) {
            processor = new CombinePostProcessors.Builder()
                    .add(new BlurPostprocessor(option.getTarget().getContext(), option.getImageTypeOption().getBlur()))
                    .add(new GrayscalePostprocessor())
                    .build();
        }
        //图片请求器
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(resizeOptions)//设置图片bitmap尺寸
                .setLocalThumbnailPreviewsEnabled(true)//本地缩率图预览
                .setPostprocessor(processor)//设置图片显示效果
                //缩放,在解码前修改内存中的图片大小, 配合Downsampling可以处理所有图片,否则只能处理jpg,
                // 开启Downsampling:在初始化时设置.setDownsampleEnabled(true)
                .setProgressiveRenderingEnabled(option.isThumbnail())//支持图片渐进式加载，仅支持JPEG图片
                .setAutoRotateEnabled(true) //如果图片是侧着,可以自动旋转
                .build();
        //图片控制器监听
        ControllerListener controllerListener = new ControllerListener<ImageInfo>() {
            /**
             * 当提交图片请求时会执行的方法
             * @param id
             * @param callerContext
             */
            @Override
            public void onSubmit(String id, Object callerContext) {

            }

            /**
             * 对所有的图片加载都会被触发,当图片加载成功时会执行的方法
             * @param id
             * @param imageInfo
             * @param animatable
             */
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                if (option.getOnImageloadSuccessOrFailCallBack() != null) {
                    option.getOnImageloadSuccessOrFailCallBack().onSuccess(imageInfo.getWidth(), imageInfo.getHeight());
                }
                //由于fresco的特性，当用户图片框宽或高设置为wrap_content时将为0，所以展示图片的时候判断到图片框的宽或高小于1的时候就按照图片实际传的尺寸来重新设置
                if (imageInfo != null && (option.getTarget().getLayoutParams().width != ViewGroup.LayoutParams.MATCH_PARENT || option.getTarget().getLayoutParams().height != ViewGroup.LayoutParams.MATCH_PARENT)) {
                    if (option.getTarget().getLayoutParams().width < 1 || option.getTarget().getLayoutParams().height < 1) {
                        ViewGroup.LayoutParams layoutParams = option.getTarget().getLayoutParams();
                        if (option.getTarget().getLayoutParams().width != ViewGroup.LayoutParams.MATCH_PARENT) {
                            layoutParams.width = imageInfo.getWidth();
                        }
                        if (option.getTarget().getLayoutParams().height != ViewGroup.LayoutParams.MATCH_PARENT) {
                            layoutParams.height = imageInfo.getHeight();
                        }
                        option.getTarget().setLayoutParams(layoutParams);
                    }
                }
            }

            /**
             * 如果允许呈现渐进式JPEG，同时图片也是渐进式图片，这个方法将会被回调
             * @param id
             * @param imageInfo
             */
            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

            }

            /**
             * 如果图片使用渐进式，这个方法将会被回调
             * @param id
             * @param throwable
             */
            @Override
            public void onIntermediateImageFailed(String id, Throwable throwable) {

            }

            /**
             * 对所有的图片加载都会被触发,图片加载失败时调用的方法
             * @param id
             * @param throwable
             */
            @Override
            public void onFailure(String id, Throwable throwable) {
                if (option.getOnImageloadSuccessOrFailCallBack() != null) {
                    option.getOnImageloadSuccessOrFailCallBack().onFail(option.getTarget(), new Exception(throwable.getMessage()));
                }
            }

            /**
             * 图片被释放调用的方法
             * @param id
             */
            @Override
            public void onRelease(String id) {

            }
        };
        //图片管理连接器
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                    .setLowResImageRequest(request)//低分辨
                .setImageRequest(request)//正常分辨率
//                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                .setOldController(((SimpleDraweeView) option.getTarget()).getController())  //设置旧的Controller，可以减少不必要的浪费
                .setControllerListener(controllerListener)//设置图片监听
                .setAutoPlayAnimations(option.isGif()) //自动播放gif动画
                .build();


        //通用图片管理连接器
        GenericDraweeHierarchyBuilder builder = GenericDraweeHierarchyBuilder.newInstance(option.getTarget().getContext().getResources());
        //图片显示类型
        ScalingUtils.ScaleType scaleType = ScalingUtils.ScaleType.CENTER_CROP;
        if (option.isFitCenter()) {
            scaleType = ScalingUtils.ScaleType.FIT_CENTER;
        } else if (option.isCenterCrop()) {
            scaleType = ScalingUtils.ScaleType.CENTER_CROP;
        }
        //实际图显示类型
        builder.setActualImageScaleType(scaleType);
        //出错图及显示类型
        if (option.getErrorImageInt() != 0) {
            builder.setFailureImage(option.getErrorImageInt(), scaleType);
        } else {
            if (option.getErrorImageDrawable() != null) {
                builder.setFailureImage(option.getErrorImageDrawable(), scaleType);
            }
        }
        //占位图及显示类型
        if (option.getPlacesHolderImageInt() != 0) {
            builder.setPlaceholderImage(option.getPlacesHolderImageInt(), scaleType);
        } else {
            if (option.getPlacesHolderDrawable() != null) {
                builder.setPlaceholderImage(option.getPlacesHolderDrawable(), scaleType);
            }
        }
        //渐隐渐现时间
        if (option.getDuration() > 0) {
            builder.setFadeDuration(option.getDuration());
        }


        //圆或圆角
        if (option.isCircleCrop()) {
            builder.setRoundingParams(RoundingParams.asCircle());
        } else {
            if (option.getRoundAngle() > 0) {
                RoundingParams rp = new RoundingParams();
                //设置哪个角需要变成圆角
                rp.setCornersRadius(option.getRoundAngle());
                //圆角部分填充色
                rp.setOverlayColor(option.getTarget().getContext().getResources().getColor(option.getRoundAngleColor()));
                //边框宽度
//                    rp.setBorderWidth(20f);
                //边框填充色
//                    rp.setBorderColor(context.getResources().getColor(option.getRoundAngleColor()));
                builder.setRoundingParams(rp);
            }
        }

        //设置加载进度监听（自定义一个进度条Drawable，取里面的进度）
        builder.setProgressBarImage (new Drawable() {


            @Override
            public void draw(@NonNull Canvas canvas) {

            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return DrawableUtils.getOpacityFromColor(Color.TRANSPARENT);
            }

            @Override
            protected boolean onLevelChange(int level) {
                if (option.getOnProgressCallBack()!=null){
                    option.getOnProgressCallBack().onProgress(level/100);
                }
                if(level > 0 && level < 10000) {
                    return true;
                }else {
                    return false;
                }

            }
        });
        //召唤神龙
        ((SimpleDraweeView) option.getTarget()).setHierarchy(builder.build());
        ((SimpleDraweeView) option.getTarget()).setController(controller);
    }


    /**
     * 下载图片
     *
     * @param context
     * @param option
     */
    @Override
    public void downLoadImage(final Context context, final ImageloadOption option) {
        if (option.getPath() == null || "".equals(option.getPath()) || !option.getPath().startsWith("http") && !option.getPath().startsWith("https")) {
            if (option.getOnDownloadImageSuccessOrFailCallBack() != null) {
                option.getOnDownloadImageSuccessOrFailCallBack().onDownloadImageFail(new Throwable("要下载的图片地址为空！"));
            }
            return;
        }
        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(ImageRequestBuilder.newBuilderWithSource(Uri.parse(option.getPath())).build(), this);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                File file = ImageloadUtils.saveImageToLocal(context, bitmap, option.getDownloadFileName(), option.isNotifyUpdateGallery());
                if (file.exists()) {
                    if (option.getOnDownloadImageSuccessOrFailCallBack() != null) {
                        option.getOnDownloadImageSuccessOrFailCallBack().onDownloadImageSuccess(file);
                    }
                } else {
                    if (option.getOnDownloadImageSuccessOrFailCallBack() != null) {
                        option.getOnDownloadImageSuccessOrFailCallBack().onDownloadImageFail(new Throwable("图片储存失败，请检查是否有读写权限！"));
                    }
                }
            }


            @Override
            public void onProgressUpdate(DataSource<CloseableReference<CloseableImage>> dataSource) {
                super.onProgressUpdate(dataSource);
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                if (option.getOnDownloadImageSuccessOrFailCallBack() != null) {
                    option.getOnDownloadImageSuccessOrFailCallBack().onDownloadImageFail(dataSource.getFailureCause());
                }
            }
        }, CallerThreadExecutor.getInstance());

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
        return new File(FrescoImagePipelineConfig.FULL_MAIN_CACHE_PATH);
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    @Override
    public long getCacheSize(Context context) {
        Fresco.getImagePipelineFactory().getMainFileCache().trimToMinimum();
        return Fresco.getImagePipelineFactory().getMainFileCache().getSize();
    }

    /**
     * 清理缓存
     *
     * @param context
     * @param cacheType
     */
    @Override
    public void clearCache(Context context, CacheType cacheType) {
        if (cacheType == CacheType.Disk) {
            Fresco.getImagePipeline().clearDiskCaches();
        } else if (cacheType == CacheType.Memory) {
            Fresco.getImagePipeline().clearMemoryCaches();
        } else if (cacheType == CacheType.All) {
            Fresco.getImagePipeline().clearMemoryCaches();
            Fresco.getImagePipeline().clearDiskCaches();
        }
    }

    /**
     * 请求是否已经暂停
     *
     * @return
     */
    @Override
    public boolean isPaused(Context context) {
        return Fresco.getImagePipeline().isPaused();
    }

    /**
     * 暂停请求
     * 在列表快速滑动时使用
     */
    @Override
    public void pause(Context context) {
        Fresco.getImagePipeline().pause();
    }

    /**
     * 恢复请求
     * 当滑动停止时使用
     */
    @Override
    public void resume(Context context) {
        Fresco.getImagePipeline().resume();
    }


}
