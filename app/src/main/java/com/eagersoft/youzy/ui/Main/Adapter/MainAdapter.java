package com.eagersoft.youzy.ui.Main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eagersoft.youzy.imageload.R;
import com.eagersoft.youzy.ui.Main.Bean.ImageBean;
import com.sss.imageload.ImageloadManager;
import com.sss.imageload.options.ImageTypeOption;
import com.sss.imageload.options.ImageloadOption;
import com.sss.imageload.widget.ImageloadView;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class MainAdapter extends BaseAdapter {
    private List<ImageBean> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MainAdapter(List<ImageBean> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainAdapterHolder mainAdapterHolder;
        if (convertView==null){
            mainAdapterHolder=new MainAdapterHolder();
            if (layoutInflater==null){
                layoutInflater=LayoutInflater.from(context);
            }
            convertView= layoutInflater.inflate(R.layout.item_image,null);
            mainAdapterHolder.image=convertView.findViewById(R.id.image);
            mainAdapterHolder.name=convertView.findViewById(R.id.name);
            convertView.setTag(mainAdapterHolder);
        }else {
            mainAdapterHolder= (MainAdapterHolder) convertView.getTag();
        }
        ImageloadOption imageloadOption=  ImageloadManager.getInstance()//图片加载管理类
                .load(list.get(position).uri)//图片地址
                .setDuration(100)//淡入淡出时间
                .setThumbnail(true)//渐进式加载
                .setRoundAngle(20)//圆角
                .setPlacesHolderImageInt(R.mipmap.ic_launcher)//占位图
                .setErrorImageInt(R.mipmap.ic_launcher)//错误图
                .setGif(list.get(position).isGif)//设置是否是GIF模式
                .setImageTypeOption(new ImageTypeOption())//设置特效参数（内部默认了一套个人认为比较好的参数，开发者可以自定义）
                .setImageType(list.get(position).imageType);//设置特效
        if (list.get(position).circle){//圆（与圆角不共存）
            imageloadOption.circleCrop();
        }else {
            imageloadOption.fitCenter();
        }

        imageloadOption.into(mainAdapterHolder.image);//设置
        mainAdapterHolder.name.setText((position+1)+"."+list.get(position).name);
        return convertView;
    }

    public static class MainAdapterHolder {
        public ImageloadView image;
        public TextView name;
    }
}
