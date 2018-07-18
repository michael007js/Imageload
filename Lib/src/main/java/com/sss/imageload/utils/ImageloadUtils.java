package com.sss.imageload.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.TypedValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ImageloadUtils {


    /**
     * 各种乱七八糟的路径转成URI
     *
     * @param url             网络图片路径
     * @param file            本地图片路径
     * @param imageResouseID  资源图片路径
     * @param errorResourceId 错误图片路径
     * @return
     */
    protected static Uri change2Uri(Context context, String url, File file, int imageResouseID, int errorResourceId) {
        if (!isEmpty(url)) {
            if (url.startsWith("http") || url.startsWith("https")) {
                return Uri.parse(url);
            }
        }
        if (file != null) {
            if (file.exists()) {
                return Uri.fromFile(file);
            }
        }
        if (imageResouseID > 0) {
            return resourceId2Uri(context, imageResouseID);
        }
        return resourceId2Uri(context, errorResourceId);
    }


    /**
     * 字符串判空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || "null".equals(str) || "NULL".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 将资源id转换为一个Uri
     *
     * @param context
     * @param resourceId 图片资源ID
     * @return
     */
    public static Uri resourceId2Uri(Context context, int resourceId) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + resourceId);
    }


    /**
     * 获取当前方法的调用轨迹
     *
     * @return
     */
    public static String getMethodPath() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Thread.currentThread().getStackTrace().length; i++) {
            stringBuilder.append("\n" + Thread.currentThread().getStackTrace()[i]);
        }
        return stringBuilder.toString();
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    public static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (File aFileList : fileList) {
            if (aFileList.isDirectory()) {
                size = size + getFolderSize(aFileList);
            } else {
                size = size + aFileList.length();
            }
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(long size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return 0 + "KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    /**
     * 保存Bitmap到本地
     *
     * @param context
     * @param bmp
     * @param fileName
     * @param notifyUpdateGallery @return
     * @return
     */
    public static File saveImageToLocal(Context context, Bitmap bmp, String fileName, boolean notifyUpdateGallery) {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName());
        if (!f.exists()) {
            f.mkdir();
        }
        String fn = (fileName == null || "".equals(fileName)) ? System.currentTimeMillis() + ".jpg" : fileName + ".jpg";
        File file = new File(f + "/" + fn);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            if (bmp == null) {
                fos.close();
                return file;
            }
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            if (notifyUpdateGallery) {
                notifyUpdateGallery(context, f, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 通知更新到图库
     * @param context
     * @param file
     * @param fileName
     */
    public static void notifyUpdateGallery(Context context, File file, String fileName) {
        //把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //保存图片后发送广播通知更新数据库
        Uri uri = Uri.fromFile(file);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

    /**
     * dp转px
     */
    public static float dp2px(float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, Resources.getSystem().getDisplayMetrics());
    }


    /**
     * px转dp
     */
    public static float px2dp(float pxVal) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (pxVal / scale);
    }
}
