package com.instinctools.common.mvp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.instinctools.common.mvp.di.component.ActivityComponent;
import com.instinctools.common.mvp.presenter.MvpPresenter;

import javax.inject.Inject;

public abstract class BaseMvpActivity<P extends MvpPresenter<?>, T extends ActivityComponent> extends Activity implements ActivityComponent.Creator<T> {

    @Inject
    protected MvpActivityDelegate<P, T> activityDelegate;

    private T activityComponent;

    protected P getMvpPresenter() {
        return activityDelegate.getMvpPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        activityDelegate.initPresenter(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        activityDelegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityDelegate.onDestroy();
    }

    public T getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = createActivityComponent();
        }
        return activityComponent;
    }
}

