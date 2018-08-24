 ** **Imageload** ** 

No picture u say a j8!

少啰嗦，先看效果

![闭嘴看图](https://github.com/michael007js/Imageload/blob/master/images/03.gif "闭嘴看图")


 **项目介绍** 
一个双击666的图片加载框架，支持fresco、glide、Picasso等等之类的主流框架，内部默认封装了Glide[传送门](https://github.com/michael007js/Imageload/blob/master/Lib/src/main/java/com/sss/imageload/imp/GlideImageLoad.java)、Fresco[传送门](https://github.com/michael007js/Imageload/blob/master/Lib/src/main/java/com/sss/imageload/imp/FrescoImageLoad.java)，具有非常高的可扩展性，开发者可非常方便的来回切换想要的框架。（个人推荐使用强大的Fresco）框架默认除了支持普通图片的各种加载外，还提供了一套特效加载，特效枚举类ImageType，提供了默认的特效参数配置类ImageTypeOption，可以自己设置，也可以new 一个开发者自定义的特效参数配置类

开发者不需要关心框架内部怎么实现，管你里面是啥玩意，跟我有半毛钱的关系吗？只需要知道继承ImageLoad这个抽象类实现内部的抽象方法即可，这也是开发者唯一需要做的事情

其次，建议开发者使用框架内部提供的ImageloadView控件作为图片框
[原因如下](https://github.com/michael007js/Imageload/blob/master/Lib/src/main/java/com/sss/imageload/widget/ImageloadView.java)

 **使用说明** 
    
  导入本库依赖
  
    implementation 'com.michael007js:Lib:1.0.2'

 
    导入第三方依赖，看使用哪种框架就导入哪种依赖，这里提供两种用的比较多的依赖（如果你需要使用到图像处理，则必须导入下面的图像引擎，否则将报错！！！）
    implementation 'com.github.bumptech.glide:glide:4.5.0'  //glide
    implementation 'com.facebook.fresco:fresco:1.9.0'    // fresco
    implementation 'com.facebook.fresco:animated-gif:1.9.0'    // fresco     支持 GIF 动图，需要添加
    implementation 'jp.wasabeef:fresco-processors:2.1.0' //fresco图像引擎//https://github.com/wasabeef/fresco-processors
    implementation 'jp.wasabeef:glide-transformations:3.3.0'    //glide图像引擎//https://github.com/wasabeef/glide-transformations
    implementation 'jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.1'    //GPU Filters
    
    
    
如果你想偷懒，想直接使用本库提供的API的时候，直接从第二步开始，如果想DIY本库的话老老实实往下看，然后就可以以为所欲为了

1. 继承ImageLoad抽象类，实现内部的抽象方法，当然，如果有兴趣，可以继续扩展内部的方法

public abstract class ImageLoad {

    //显示图片
    public abstract void displayImage(ImageloadOption option);

    //下载图片
    public abstract void downLoadImage(ImageloadOption option);
    
    //测量图片尺寸
    public abstract void measureImage(Context context, ImageloadOption option);

    //获取缓存目录
    public abstract File getCacheDir(Context context);

    //获取缓存大小
    public abstract long getCacheSize(Context context);

    //清理缓存
    public abstract void clearCache(Context context, CacheType cacheType);

    //暂停请求
    public abstract void pause() 

    //恢复请求
    public abstract void resume() 
    
}

2. 在Application中配置一下即可使用

 **画重点，要考！使用fresco的开发者必须初始化一下，内部默认提供了一个狂拽酷炫吊炸天的fresco配置类FrescoImagePipelineConfig,[传送门](https://github.com/michael007js/Imageload/blob/master/Lib/src/main/java/com/sss/imageload/frescoConfig/FrescoImagePipelineConfig.java)如果看着有点不爽可以自行重定义或直接在原配上修改** 
    
    Fresco.initialize(this, FrescoImagePipelineConfig.getDefaultImagePipelineConfig(this));
    ImageloadManager.getInstance().build(new ImageloadManager.Builder().setImageLoad(new FrescoImageLoad())); 

开发者只需要在此将imageload的实现类FrescoImageLoad替换成想要的框架即可



3.API集合

完整的常用API调用如下（开发者根据自己的需求决定调用）

        ImageloadManager.getInstance()
                .load("http://img1.gtimg.com/house_wuhan/pics/hv1/0/16/1926/125242230.jpg")//图片地址(支持网络路径,资源路径,文件路径,uri路径)
                .setDuration(100)//淡入淡出时间(ms),默认300ms
                .setThumbnail(true)//渐进式加载
                .setRoundAngle(20f)//圆角,默认5f
                .centerCrop()//不解释，centerCrop,fitCenter,circleCrop不共存
                .fitCenter()//不解释，centerCrop,fitCenter,circleCrop不共存
                .circleCrop()//圆，与上面两种模式不共存
                .setPlacesHolderImageInt(R.mipmap.ic_launcher)//占位图（int引用类型）
                .setPlacesHolderDrawable(getResources().getDrawable(R.drawable.ic_launcher))//占位图（Drawable类型）
                .setErrorImageInt(R.mipmap.ic_launcher)//错误图（int引用类型）
                .setErrorImageDrawable(getResources().getDrawable(R.drawable.ic_launcher))//错误图（Drawable类型）
                .setGif(false)//设置是否是GIF模式
                .setImageType(ImageType.Toon)//卡通特效
                .setImageTypeOption(//设置特效参数（内部默认了一套个人认为比较好的参数，开发者可以不需要再设置参数，当然，也可以自定义）
                        new ImageTypeOption()
                )//构造特效参数类（内部一堆参数，开发者自己看需求配合ImageType中的哪种特效来设置对应的参数）
                .setOnImageloadSuccessOrFailCallBack(new OnImageloadSuccessOrFailCallBack() {//图片加载成功或失败回调
                    @Override
                    public void onSuccess(int imageWidth, int imageHeight) {
                        //图片加载成功，回调返回图片的宽高
                    }

                    @Override
                    public void onFail(View view, Exception e) {
                        //图片加载失败，回调返回被加载的图片框，失败原因
                    }
                })
                .into((ImageloadView) findViewById(R.id.pic));//召唤神龙





如果你想监听图片的加载进度你可以这么干

**使用Glide的开发者请注意，由于Glide的特性，想要实现这个功能请在AndroidManifest中Application标签下定义一个meta-data标签，内部指定GlideModule,如下**

         <meta-data
            android:name="com.sss.imageload.progress.GlideCache"
            android:value="GlideModule" />
            
本库已经提供了一Glide专用进度监听模块（这里参考了郭霖的相关代码，在此表示感谢），完整路径为com.sss.imageload.progress，使用Fresco的开发者就不要在意这些细节了，然后，加载图片的时候设置一个回调就欧了

        
         .setOnProgressCallBack(new OnProgressCallBack() {//图片实时加载进度回调
            @Override
            public void onProgress(int percentage) {
                //回调返回图片实时加载进度（Fresco全系列支持，而Glide框架的暂时只支持从网络加载，不过后面哪次迭代的时候如果闲的蛋疼可以考虑加上的）
            }
        })


如果你想只测量图片宽高而不需要加载图片的话你可以这样玩

        ImageloadManager.getInstance()
                .load("http://img1.gtimg.com/house_wuhan/pics/hv1/0/16/1926/125242230.jpg")//图片地址(支持网络路径,资源路径,文件路径,uri路径)
                .setOnMeasureImageSizeCallBack(new OnMeasureImageSizeCallBack() {//图片尺寸测量回调
                    @Override
                    public void onMeasureImageSize(int imageWidth, int imageHeight) {
                        //测量成功后回调返回图片的宽高
                    }

                    @Override
                    public void onMeasureImageFail(Throwable throwable) {
                        //测量失败后回调返回失败原因
                    }
                })
                .measureImage(this);//开始测量






图片下载一直是个蛋疼的功能，普通方式要写一堆玩意儿，但在这里，可以非常方便的一行代码链式调用就可以实现了

**使用Glide的开发者请注意，由于Glide特性，下载请不要在主线程中下载，本库内部也已经做了判断，侦测到主线程调用将抛异常**

        ImageloadManager.getInstance()
                .load("http://img1.gtimg.com/house_wuhan/pics/hv1/0/16/1926/125242230.jpg")//图片地址(支持网络路径,资源路径,文件路径,uri路径)
                .setDownloadFileName(System.currentTimeMillis() + "")//设置储存的文件名
                .setNotifyUpdateGallery(true)//下载完成后是否通知图库更新以便插入到相册
                .setOnDownloadImageSuccessOrFailCallBack(new OnDownloadImageSuccessOrFailCallBack() {//下载回调借口
                    @Override
                    public void onDownloadImageSuccess(File file) {
                        /*
                        下载成功后的回调返回储存到本地的File对象，默认储存到缓存文件夹下，
                        可以通过File来copy或remove到你想要的位置或者delete也可以，抱歉，有File真的可以为所欲为
                        */
                    }

                    @Override
                    public void onDownloadImageFail(Throwable failureCause) {
                        //下载失败后回调返回失败原因
                    }
                })
                .downLoadImage(this);//开始下载



配合RecycleView或ListView滑动事件可以达到节省系统资源的目的，这里以ListView为例子，RecycleView也是差不多一个意思

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {//列表不在滑动
                    if (ImageloadManager.getInstance().isPause(MainActivity.this)) {//图片库是否已经暂停加载
                        ImageloadManager.getInstance().resume(MainActivity.this);//图片库恢复加载图片
                    }
                } else {
                    if (!ImageloadManager.getInstance().isPause(MainActivity.this)) {//图片库是否已经暂停加载
                        ImageloadManager.getInstance().pause(MainActivity.this);//图片库暂停加载图片
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        
        
       
清除缓存CacheType有三种模式：CacheType.Memory内存缓存，Memory.Disk磁盘缓存，CacheType.All所有，

**使用Glide的开发者请注意，由于Glide特性，请不要在主线程中调用Memory.Disk模式，也不要在子线程中调用CacheType.Memory与CacheType.All模式，
至于为什么这样子，进去看看这个方法你就会知道了
[原因在此，翻到clearCache方法就能解决你的疑问](https://github.com/michael007js/Imageload/blob/master/Lib/src/main/java/com/sss/imageload/imp/GlideImageLoad.java)**

        ImageloadManager.getInstance().clearCache(this, CacheType.All);


**混淆添加**

Glide:

-keep public class * implements com.bumptech.glide.module.GlideModule

-keep public class * extends com.bumptech.glide.module.AppGlideModule

-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {

**[] $VALUES;

  public *;
}

Fresco:

-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

-keep,allowobfuscation @interface com.facebook.soloader.DoNotOptimize

-keep @com.facebook.common.internal.DoNotStrip class *

-keepclassmembers class * {

    @com.facebook.common.internal.DoNotStrip *;
}
-keep @com.facebook.soloader.DoNotOptimize class *

-keepclassmembers class * {

    @com.facebook.soloader.DoNotOptimize *;
}

-keepclassmembers class * {

    native <methods>;
}

-dontwarn com.facebook.infer.**


**历史版本**

V1.0.2：加入图片加载进度接口

V1.0.1：修复使用中的bugs,加入独立的图片尺寸测量接口

V1.0.0：初始版本上线

 over

 By SSS




