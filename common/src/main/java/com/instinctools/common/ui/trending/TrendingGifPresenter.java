package com.instinctools.common.ui.trending;

import android.text.TextUtils;

import com.instinctools.common.mvp.presenter.BaseModelPresenter;
import com.instinctools.data.giphy.model.Gif;

public class TrendingGifPresenter extends BaseModelPresenter<Gif, TrendingGifView> {

    @Override
    protected void updateView() {
        String gifUrl = model.getGifUrl();
        if (!TextUtils.isEmpty(gifUrl)) {
            getMvpView().showGifImage(gifUrl);
        } else {
            getMvpView().showImage(model.getImageUrl());
        }
    }

    public void onGifClicked() {
        getMvpView().goToGifDetails(model);
    }
}
