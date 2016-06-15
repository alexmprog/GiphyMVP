package com.instinctools.common.ui.trending;

import android.support.annotation.NonNull;

import com.instinctools.common.mvp.view.MvpView;
import com.instinctools.data.giphy.model.Gif;

public interface TrendingGifView extends MvpView {

    void showImage(@NonNull String imageUrl);

    void showGifImage(@NonNull String imageUrl);

    void goToGifDetails(@NonNull Gif gif);
}
