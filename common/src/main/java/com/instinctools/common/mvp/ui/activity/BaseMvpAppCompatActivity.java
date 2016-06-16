package com.instinctools.common.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.instinctools.common.mvp.di.component.ActivityComponent;
import com.instinctools.common.mvp.presenter.MvpPresenter;
import com.instinctools.common.mvp.view.MvpView;

import javax.inject.Inject;

public abstract class BaseMvpAppCompatActivity<V extends MvpView, P extends MvpPresenter<V>, T extends ActivityComponent> extends AppCompatActivity implements ActivityComponent.Injector<T> {

    @Inject
    protected MvpActivityDelegate<V, P> activityDelegate;

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

