package com.instinctools.data.di.module;

import com.instinctools.data.giphy.api.GiphyObjectMapper;
import com.instinctools.data.giphy.api.GiphyService;
import com.instinctools.data.giphy.datastore.GifDataStoreFactory;
import com.instinctools.data.prefs.Prefs;
import com.instinctools.data.realm.RealmManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DataStoreFactoryModule {

    @Provides
    public GifDataStoreFactory providesGifDataStoreFactory(RealmManager realmManager, GiphyService giphyService, GiphyObjectMapper giphyObjectMapper, Prefs prefs) {
        return new GifDataStoreFactory(realmManager, giphyService, giphyObjectMapper, prefs);
    }
}
