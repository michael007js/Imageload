package com.eagersoft.youzy.ui.Main.Model;

import android.net.Uri;

import com.eagersoft.youzy.ui.Main.Bean.ImageBean;
import com.sss.imageload.enums.ImageType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class MainModel {


    public void getList(IMainModel iMainModel) {
        String url = "http://img1.gtimg.com/house_wuhan/pics/hv1/181/15/1926/125242156.jpg";
       String gifUrl="http://img.mp.itc.cn/upload/20170312/497e463688644e8d8b8538d9961929d9.gif";
        if (iMainModel != null) {
            List<ImageBean> list = new ArrayList<>();
            list.add(new ImageBean(Uri.parse(gifUrl),null,"gif",false,true));
            list.add(new ImageBean(Uri.parse(url), null, "normal", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Blur, "Blur", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.BlurAndGrayscale, "BlurAndGrayscale", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Brightness, "Brightness", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.ColorFilter, "ColorFilter", true,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Contrast, "Contrast", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Grayscale, "Grayscale", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Invert, "Invert", false,false));
//            list.add(new ImageBean(Uri.parse(url), ImageType.Kuawahara, "Kuawahara", false,false));这个效果比较费系统资源
            list.add(new ImageBean(Uri.parse(url), ImageType.Mask, "Mask", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.NinePatchMask, "NinePatchMask", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Pixel, "Pixel", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Sepia, "Sepia", true,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Sketch, "Sketch", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Swirl, "Swirl", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Toon, "Toon", false,false));
            list.add(new ImageBean(Uri.parse(url), ImageType.Vignette, "Vignette", true,false));
            iMainModel.onGetData(list);
        }
    }

}
