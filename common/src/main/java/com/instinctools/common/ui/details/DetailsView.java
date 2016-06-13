package com.instinctools.common.ui.details;

import android.support.annotation.NonNull;

import com.instinctools.common.ui.base.MvpView;
import com.instinctools.data.giphy.model.Gif;

public interface DetailsView extends MvpView {

    void showGif(@NonNull Gif gif);
}
