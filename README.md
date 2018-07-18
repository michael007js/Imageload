 ** **Imageload** ** 

少啰嗦，先看效果

![闭嘴看J8](https://github.com/michael007js/Imageload/blob/master/images/03.gif "闭嘴看J8")


 **项目介绍** 
一个双击666的图片加载框架，支持fresco、glide、Picasso等等之类的主流框架，内部默认封装了Glide、Fresco，具有非常高的可扩展性，开发者可非常方便的来回切换想要的框架。框架默认除了支持普通图片的各种加载外，还提供了一套特效加载，特效枚举类ImageType，提供了默认的特效参数配置类ImageTypeOption，可以自己设置，也可以new 一个开发者自定义的特效参数配置类

开发者不需要关心框架内部怎么实现，管你里面是啥玩意，跟我有半毛钱的关系吗？，只需要知道继承ImageLoad这个抽象类实现内部的抽象方法即可，这也是开发者唯一需要做的事情

其次，建议开发者使用框架内部提供的ImageloadView控件作为图片框
[原因如下](https://github.com/michael007js/Imageload/blob/master/Lib/src/main/java/com/sss/imageload/widget/ImageloadView.java)

 **使用说明** 
 
 
    导入第三方依赖，看使用哪种框架就导入哪种依赖，这里提供两种用的比较多的依赖
    implementation 'com.github.bumptech.glide:glide:4.5.0'  //glide
    implementation 'com.facebook.fresco:fresco:1.9.0'    // fresco
    implementation 'com.facebook.fresco:animated-gif:1.9.0'    // fresco     支持 GIF 动图，需要添加
    implementation 'jp.wasabeef:fresco-processors:2.1.0' //fresco图像引擎//https://github.com/wasabeef/fresco-processors
    implementation 'jp.wasabeef:glide-transformations:3.3.0'    //glide图像引擎//https://github.com/wasabeef/glide-transformations
    implementation 'jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.1'    //GPU Filters
    
    导入本库依赖
    implementation 'com.michael007js:Lib:1.0.0'
    
    
如果你想重新扩展本库的话可以直接导入Module,然后像下面这样子可以为所以为了

1. 继承ImageLoad抽象类，实现内部的抽象方法，当然，如果有兴趣，可以继续扩展内部的方法

public abstract class ImageLoad {

    //显示图片
    public abstract void displayImage(ImageloadOption option);

    //下载图片
    public abstract void downLoadImage(ImageloadOption option);

    //获取缓存目录
    public abstract File getCacheDir(Context context);

    //获取缓存大小
    public abstract long getCacheSize(Context context);

    //清理缓存
    public abstract void clearCache(Context context, CacheType cacheType);

    //暂停网络请求
    public abstract void pause() 

    //恢复网络请求
    public abstract void resume() 
    
}

2. 在Application中配置一下即可使用

 **画重点，要考！使用fresco的开发者必须初始化一下，内部默认提供了一个狂拽酷炫吊炸天的fresco配置类FrescoImagePipelineConfig,[传送门](https://github.com/michael007js/Imageload/blob/master/Lib/src/main/java/com/sss/imageload/frescoConfig/FrescoImagePipelineConfig.java)如果看着有点不爽可以自行重定义或直接在原配上修改** 
    
    Fresco.initialize(this, FrescoImagePipelineConfig.getDefaultImagePipelineConfig(this));

        ImageloadManager.Builder builder=new ImageloadManager.Builder()
                .setContext(this)
                .setImageLoad(new FrescoImageLoad(this));
        ImageloadManager.getInstance().build(builder); 

开发者只需要在此将imageload的实现类FrescoImageLoad替换成想要的框架即可



3.然后，像下面酱紫就欧了

   ImageloadManager.getInstance()

                .load(list.get(uri)//图片地址

                .setDuration(100)//淡入淡出时间

                .setThumbnail(true)//渐进式加载

                .setRoundAngle(20)//圆角

                .setPlacesHolderImageInt(R.mipmap.ic_launcher)//占位图

                .setErrorImageInt(R.mipmap.ic_launcher)//错误图

                .setGif(list.get(true)//设置是否是GIF模式

                .setImageTypeOption(new ImageTypeOption().XXXXXXXXXXXXXXXX之类的玩意)//设置特效参数（内部默认了一套个人认为比较好的参数，开发者可以自定义）

                .setImageType(ImageType.Grayscale)//设置特效

                .into(ImageLoadView);//召唤神龙


当然，远不止这些配置，还有一堆的参数配置可以自定义       


懒得讲了，上J8

![XXX](https://github.com/michael007js/Imageload/blob/master/images/01.png "QQ截图20180717143419.png")

 over

 By SSS




