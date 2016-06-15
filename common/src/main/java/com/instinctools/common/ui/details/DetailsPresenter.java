package com.instinctools.common.ui.details;

import android.text.TextUtils;

import com.instinctools.common.mvp.presenter.BasePresenter;
import com.instinctools.data.giphy.model.Gif;

public class DetailsPresenter extends BasePresenter<DetailsView> {

    public void checkData(Gif gif) {
        String gifUrl = gif.getGifUrl();
        if (!TextUtils.isEmpty(gifUrl)) {
            getMvpView().showGifImage(gifUrl);
        } else {
            getMvpView().showImage(gif.getImageUrl());
        }
    }
}
