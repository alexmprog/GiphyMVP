package com.instinctools.common.mvp.presenter;

import com.instinctools.common.mvp.view.MvpView;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
