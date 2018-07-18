package com.eagersoft.youzy.ui.Main;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

