package com.instinctools.common.ui.details;

import android.support.annotation.NonNull;

import com.instinctools.common.mvp.view.MvpView;

public interface DetailsView extends MvpView {

    void showImage(@NonNull String imageUrl);

    void showGifImage(@NonNull String imageUrl);
}
