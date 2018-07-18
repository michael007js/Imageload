package com.eagersoft.youzy.ui.Main.Presenter;

import com.eagersoft.youzy.ui.Main.Bean.ImageBean;
import com.eagersoft.youzy.ui.Main.Model.IMainModel;
import com.eagersoft.youzy.ui.Main.Model.MainModel;
import com.eagersoft.youzy.ui.Main.View.IMainView;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class MainPresenter {
    private MainModel model;
    private IMainView mainView;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
        this.model = new MainModel();
    }

    public void showImage() {
        model.getList(new IMainModel() {
            @Override
            public void onGetData(List<ImageBean> list) {
                if (mainView != null) {
                    mainView.showImages(list);
                }
            }
        });
    }
}
