package com.instinctools.common.di.component;

import android.app.Application;
import android.content.Context;

import com.instinctools.common.di.module.ApplicationModule;
import com.instinctools.data.di.module.DataStoreFactoryModule;
import com.instinctools.data.di.module.GiphyApiModule;
import com.instinctools.data.di.module.PrefsModule;
import com.instinctools.data.di.module.RealmModule;
import com.instinctools.data.di.module.RepositoryModule;
import com.instinctools.data.giphy.api.GiphyObjectMapper;
import com.instinctools.data.giphy.api.GiphyService;
import com.instinctools.data.giphy.datastore.GifDataStoreFactory;
import com.instinctools.data.giphy.repository.GifRepository;
import com.instinctools.data.prefs.Prefs;
import com.instinctools.data.realm.RealmManager;
import com.instinctools.data.realm.RealmObjectMapper;
import com.instinctools.domain.di.UseCaseModule;
import com.instinctools.domain.usecase.gif.GetTrendingGifsUseCase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class, RealmModule.class,
        PrefsModule.class, GiphyApiModule.class, DataStoreFactoryModule.class, UseCaseModule.class})
public interface ApplicationComponent {

    Context provideContext();

    Prefs providePrefs();

    Application provideApplication();

    RealmManager provideRealmManager();

    RealmObjectMapper provideRealmObjectMapper();

    GiphyService provideGiphyService();

    GiphyObjectMapper provideGiphyObjectMapper();

    GifDataStoreFactory provideGifDataStoreFactory();

    GetTrendingGifsUseCase provideGetTrendingGifsUseCase();

    GifRepository provideGifRepository();

}
