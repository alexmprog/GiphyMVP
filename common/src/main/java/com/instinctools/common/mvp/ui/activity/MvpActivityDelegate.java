package com.instinctools.common.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.instinctools.common.mvp.di.component.ActivityComponent;
import com.instinctools.common.mvp.presenter.MvpPresenter;
import com.instinctools.common.mvp.presenter.PresenterHolder;

import javax.inject.Inject;

import dagger.Lazy;

class MvpActivityDelegate<P extends MvpPresenter<?>, T extends ActivityComponent> {

    @Inject
    protected PresenterHolder presenterHolder;

    @Inject
    protected Lazy<P> mvpPresenterRef;

    private P mvpPresenter;

    @Inject
    MvpActivityDelegate() {
    }

    void initPresenter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // first creation
            mvpPresenter = mvpPresenterRef.get();
        } else {
            // get from cache
            mvpPresenter = presenterHolder.restorePresenter(savedInstanceState);
            if (mvpPresenter == null) {
                // something wrong with cache
                mvpPresenter = mvpPresenterRef.get();
            }
        }
    }

    P getMvpPresenter() {
        return mvpPresenter;
    }

    void onSaveInstanceState(Bundle outState) {
        presenterHolder.savePresenter(mvpPresenter, outState);
    }

    void onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
            mvpPresenterRef = null;
        }
    }
}
