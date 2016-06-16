package com.instinctools.common.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.instinctools.common.mvp.presenter.MvpPresenter;
import com.instinctools.common.mvp.presenter.PresenterHolder;
import com.instinctools.common.mvp.view.MvpView;

import javax.inject.Inject;

import dagger.Lazy;

public class MvpFragmentDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    @Inject
    protected PresenterHolder presenterHolder;

    @Inject
    protected Lazy<P> mvpPresenterRef;

    private P mvpPresenter;

    @Inject
    public MvpFragmentDelegate() {
    }

    public void initPresenter(@Nullable Bundle savedInstanceState) {
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

    public P getMvpPresenter() {
        return mvpPresenter;
    }

    public void onSaveInstanceState(Bundle outState) {
        presenterHolder.savePresenter(mvpPresenter, outState);
    }

    public void onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
            mvpPresenterRef = null;
        }
    }
}
