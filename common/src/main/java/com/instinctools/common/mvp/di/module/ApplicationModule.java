package com.instinctools.common.mvp.di.module;

import android.app.Application;
import android.content.Context;

import com.instinctools.common.mvp.presenter.PresenterHolder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public PresenterHolder providesPresenterHolder() {
        return new PresenterHolder(60 * 1000);
    }

    @Provides
    public Context providesContext() {
        return mApplication;
    }

}