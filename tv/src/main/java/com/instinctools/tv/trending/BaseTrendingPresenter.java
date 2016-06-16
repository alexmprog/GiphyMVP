package com.instinctools.tv.trending;

import com.instinctools.common.mvp.presenter.BasePresenter;

public class BaseTrendingPresenter extends BasePresenter<BaseTrendingView> {

    @Override
    public void attachView(BaseTrendingView mvpView) {
        super.attachView(mvpView);
        getMvpView().showTrendingView();
    }
}
