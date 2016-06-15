package com.instinctools.common.mvp.viewstate;

import com.instinctools.common.mvp.view.MvpView;

public interface MvpViewState<V extends MvpView> {

    void apply(V view);
}
