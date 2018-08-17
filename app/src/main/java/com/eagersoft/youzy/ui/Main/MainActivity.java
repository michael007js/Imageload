package com.eagersoft.youzy.ui.Main;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.eagersoft.youzy.imageload.R;
import com.eagersoft.youzy.ui.Main.Adapter.MainAdapter;
import com.eagersoft.youzy.ui.Main.Bean.ImageBean;
import com.eagersoft.youzy.ui.Main.Presenter.MainPresenter;
import com.eagersoft.youzy.ui.Main.View.IMainView;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.sss.imageload.ImageloadManager;
import com.sss.imageload.dao.OnDownloadImageSuccessOrFailCallBack;
import com.sss.imageload.dao.OnImageloadSuccessOrFailCallBack;
import com.sss.imageload.dao.OnMeasureImageSizeCallBack;
import com.sss.imageload.dao.OnProgressCallBack;
import com.sss.imageload.enums.CacheType;
import com.sss.imageload.enums.ImageType;
import com.sss.imageload.options.ImageTypeOption;
import com.sss.imageload.options.ImageloadOption;
import com.sss.imageload.widget.ImageloadView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView {
    private ListView listView;
    private MainAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        presenter = new MainPresenter(this);
        requestExternalStoragePermission();






        ImageloadManager.getInstance()
                .load("http://img1.gtimg.com/house_wuhan/pics/hv1/0/16/1926/125242230.jpg")//图片地址(支持网络路径,资源路径,文件路径,uri路径)
                .setDuration(100)//淡入淡出时间(ms),默认300ms
                .setThumbnail(true)//渐进式加载
                .setRoundAngle(20f)//圆角,默认5f
                .setPlacesHolderImageInt(R.mipmap.ic_launcher)//占位图（int引用类型）
                .setErrorImageInt(R.mipmap.ic_launcher)//错误图（int引用类型）
                .setGif(false)//设置是否是GIF模式
                .setImageType(ImageType.Toon)//卡通特效
                .setImageTypeOption(//设置特效参数（内部默认了一套个人认为比较好的参数，开发者可以不需要再设置参数，当然，也可以自定义）
                        new ImageTypeOption()
                )//构造特效参数类（内部一堆参数，开发者自己看需求配合ImageType中的哪种特效来设置对应的参数）
                .setOnProgressCallBack(new OnProgressCallBack() {//图片实时加载进度回调
                    @Override
                    public void onProgress(int percentage) {
                        Log.e("SSSSS",percentage+"");
                    }
                })
                .into((ImageloadView) findViewById(R.id.pic));//召唤神龙













        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (ImageloadManager.getInstance().isPause(MainActivity.this)) {
                        ImageloadManager.getInstance().resume(MainActivity.this);
                    }
                } else {
                    if (!ImageloadManager.getInstance().isPause(MainActivity.this)) {
                        ImageloadManager.getInstance().pause(MainActivity.this);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    private void requestExternalStoragePermission() {
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.INTERNET);
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        XXPermissions.with(this)
                .permission(list)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        presenter.showImage();

                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
    }

    @Override
    public void showImages(List<ImageBean> list) {
        adapter = new MainAdapter(list, this);
        listView.setAdapter(adapter);
    }
}

