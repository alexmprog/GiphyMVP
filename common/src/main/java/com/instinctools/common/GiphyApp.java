package com.instinctools.common;

import android.app.Application;
import android.content.Context;

import com.instinctools.common.di.component.ApplicationComponent;
import com.instinctools.common.di.component.DaggerApplicationComponent;
import com.instinctools.common.di.module.ApplicationModule;
import com.instinctools.data.di.module.GiphyApiModule;
import com.instinctools.data.di.module.DataStoreFactoryModule;
import com.instinctools.data.di.module.PrefsModule;
import com.instinctools.data.di.module.RealmModule;
import com.instinctools.data.di.module.RepositoryModule;
import com.instinctools.domain.BuildConfig;
import com.instinctools.domain.di.UseCaseModule;

import timber.log.Timber;

public class GiphyApp extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mApplicationComponent = DaggerApplicationComponent.builder()
                .repositoryModule(new RepositoryModule())
                .dataStoreFactoryModule(new DataStoreFactoryModule())
                .giphyApiModule(new GiphyApiModule())
                .realmModule(new RealmModule())
                .prefsModule(new PrefsModule())
                .useCaseModule(new UseCaseModule())
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static GiphyApp get(Context context) {
        return (GiphyApp) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
