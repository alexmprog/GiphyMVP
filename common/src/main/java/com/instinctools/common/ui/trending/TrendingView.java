package com.instinctools.common.ui.trending;

import android.support.annotation.NonNull;

import com.instinctools.common.mvp.view.MvpView;
import com.instinctools.data.giphy.model.Gif;

import java.util.List;

public interface TrendingView extends MvpView {

    void showProgress();

    void hideProgress();

    void showData(@NonNull List<Gif> gifs);

    void showError();

    void showEmpty();

    void showMessageLayout(boolean show);

    void goToGif(@NonNull Gif gif);
}
