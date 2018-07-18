package com.sss.imageload.enums;

import com.sss.imageload.options.ImageTypeOption;

/**
 * 图片显示效果枚举类
 * 对应参数设置详见#{@link ImageTypeOption}
 * Created by Administrator on 2018/7/1.
 */

public enum ImageType {
    Mask,//遮盖
    NinePatchMask,//.9遮盖
    ColorFilter,//色彩过滤器
    Grayscale,//灰度
    Blur,//高斯模糊
    Toon,//卡通图案
    Sepia,//乌墨色
    Contrast,//对比度
    Invert,//图片颠倒
    Pixel,//像素化滤波器
    Sketch,//手绘素描图
    Swirl,//旋转过滤器
    Brightness,//亮度过滤器
    Kuawahara,//抽象派，6的飞起，但这个效果比较费系统资源
    Vignette,//装饰过滤器
    BlurAndGrayscale//高斯模糊与灰度并存
}
