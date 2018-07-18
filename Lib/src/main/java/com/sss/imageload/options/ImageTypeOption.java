package com.sss.imageload.options;

import com.sss.imageload.enums.ImageType;

/**
 * 图片显示效果配置类
 * 对应枚举类详见#{@link ImageType}
 * Created by Administrator on 2018/7/1.
 */

public class ImageTypeOption {
    private int Mask;//遮盖图res资源
    private int NinePatchMask;//.9遮盖图res资源
    private int ColorFilterA = 0;//色彩过透明色调
    private int ColorFilterR = 100;//色彩过滤红色调
    private int ColorFilterG = 100;//色彩过滤绿色调
    private int ColorFilterB = 100;//色彩过滤蓝色调
    private int Blur = 25;//高斯模糊
    private int Kuawahara = 25;//抽象派
    private float Contrast = 2.0f;//对比度
    private float pixel = 30f;//像素化滤波器过滤程度
    private float SwirlRadius = 0.5f;//旋转半径
    private float SwirlAngle = 1.0f;//旋转角度
    private float SwirlCenterX = 0.5f;//旋转中心点X坐标
    private float SwirlCenterY = 0.5f;//旋转中心点Y坐标
    private float Brightness = 0.5f;//亮度
    private float Sepia = 1.0f;//乌墨色过滤程度
    private float ToonThreshold = .2f;//卡通色过滤阀值
    private float ToonQuantizationLevels = 10.0f;//卡通色过滤量化程度
    private float VignetteStart = 0.0f;//装饰开始区域
    private float VignetteEnd = 0.75f;//装饰结束区域
    private float VignetteCenterX = 0.5f;//装饰中心点X坐标
    private float VignetteCenterY = 0.5f;//装饰中心点Y坐标
    private float VignetteColorX = 0.0f;//装饰渐晕色1
    private float VignetteColorY = 0.0f;//装饰渐晕色2
    private float VignetteColorZ = 0.0f;//装饰渐晕色3

    public int getMask() {
        return Mask;
    }

    public ImageTypeOption setMask(int mask) {
        Mask = mask;
        return this;
    }

    public int getNinePatchMask() {
        return NinePatchMask;
    }

    public ImageTypeOption setNinePatchMask(int ninePatchMask) {
        NinePatchMask = ninePatchMask;
        return this;
    }

    public int getColorFilterA() {
        return ColorFilterA;
    }

    public ImageTypeOption setColorFilterA(int colorFilterA) {
        ColorFilterA = colorFilterA;
        return this;
    }

    public int getColorFilterR() {
        return ColorFilterR;
    }

    public ImageTypeOption setColorFilterR(int colorFilterR) {
        ColorFilterR = colorFilterR;
        return this;
    }

    public int getColorFilterG() {
        return ColorFilterG;
    }

    public ImageTypeOption setColorFilterG(int colorFilterG) {
        ColorFilterG = colorFilterG;
        return this;
    }

    public int getColorFilterB() {
        return ColorFilterB;
    }

    public ImageTypeOption setColorFilterB(int colorFilterB) {
        ColorFilterB = colorFilterB;
        return this;
    }

    public int getBlur() {
        return Blur;
    }

    public ImageTypeOption setBlur(int blur) {
        Blur = blur;
        return this;
    }

    public int getKuawahara() {
        return Kuawahara;
    }

    public ImageTypeOption setKuawahara(int kuawahara) {
        Kuawahara = kuawahara;
        return this;
    }

    public float getContrast() {
        return Contrast;
    }

    public ImageTypeOption setContrast(float contrast) {
        Contrast = contrast;
        return this;
    }

    public float getPixel() {
        return pixel;
    }

    public ImageTypeOption setPixel(float pixel) {
        this.pixel = pixel;
        return this;
    }

    public float getSwirlRadius() {
        return SwirlRadius;
    }

    public ImageTypeOption setSwirlRadius(float swirlRadius) {
        SwirlRadius = swirlRadius;
        return this;
    }

    public float getSwirlAngle() {
        return SwirlAngle;
    }

    public ImageTypeOption setSwirlAngle(float swirlAngle) {
        SwirlAngle = swirlAngle;
        return this;
    }

    public float getSwirlCenterX() {
        return SwirlCenterX;
    }

    public ImageTypeOption setSwirlCenterX(float swirlCenterX) {
        SwirlCenterX = swirlCenterX;
        return this;
    }

    public float getSwirlCenterY() {
        return SwirlCenterY;
    }

    public ImageTypeOption setSwirlCenterY(float swirlCenterY) {
        SwirlCenterY = swirlCenterY;
        return this;
    }

    public float getBrightness() {
        return Brightness;
    }

    public ImageTypeOption setBrightness(float brightness) {
        Brightness = brightness;
        return this;
    }

    public float getSepia() {
        return Sepia;
    }

    public ImageTypeOption setSepia(float sepia) {
        Sepia = sepia;
        return this;
    }

    public float getToonThreshold() {
        return ToonThreshold;
    }

    public ImageTypeOption setToonThreshold(float toonThreshold) {
        ToonThreshold = toonThreshold;
        return this;
    }

    public float getToonQuantizationLevels() {
        return ToonQuantizationLevels;
    }

    public ImageTypeOption setToonQuantizationLevels(float toonQuantizationLevels) {
        ToonQuantizationLevels = toonQuantizationLevels;
        return this;
    }

    public float getVignetteStart() {
        return VignetteStart;
    }

    public ImageTypeOption setVignetteStart(float vignetteStart) {
        VignetteStart = vignetteStart;
        return this;
    }

    public float getVignetteEnd() {
        return VignetteEnd;
    }

    public ImageTypeOption setVignetteEnd(float vignetteEnd) {
        VignetteEnd = vignetteEnd;
        return this;
    }

    public float getVignetteCenterX() {
        return VignetteCenterX;
    }

    public ImageTypeOption setVignetteCenterX(float vignetteCenterX) {
        VignetteCenterX = vignetteCenterX;
        return this;
    }

    public float getVignetteCenterY() {
        return VignetteCenterY;
    }

    public ImageTypeOption setVignetteCenterY(float vignetteCenterY) {
        VignetteCenterY = vignetteCenterY;
        return this;
    }

    public float getVignetteColorX() {
        return VignetteColorX;
    }

    public ImageTypeOption setVignetteColorX(float vignetteColorX) {
        VignetteColorX = vignetteColorX;
        return this;
    }

    public float getVignetteColorY() {
        return VignetteColorY;
    }

    public ImageTypeOption setVignetteColorY(float vignetteColorY) {
        VignetteColorY = vignetteColorY;
        return this;
    }

    public float getVignetteColorZ() {
        return VignetteColorZ;
    }

    public ImageTypeOption setVignetteColorZ(float vignetteColorZ) {
        VignetteColorZ = vignetteColorZ;
        return this;
    }
}
