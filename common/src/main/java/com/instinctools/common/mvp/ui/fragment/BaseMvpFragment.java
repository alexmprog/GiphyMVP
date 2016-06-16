package com.instinctools.common.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.instinctools.common.mvp.presenter.MvpPresenter;
import com.instinctools.common.mvp.view.MvpView;

import javax.inject.Inject;

public abstract class BaseMvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends Fragment {

    @Inject
    protected MvpFragmentDelegate<V, P> fragmentDelegate;

    protected P getMvpPresenter() {
        return fragmentDelegate.getMvpPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inject();
        fragmentDelegate.initPresenter(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDelegate.onDestroy();
    }

    public abstract void inject();
}
