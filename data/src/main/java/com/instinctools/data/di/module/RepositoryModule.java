package com.instinctools.data.di.module;

import com.instinctools.data.giphy.datastore.GifDataStoreFactory;
import com.instinctools.data.giphy.repository.GifRepository;
import com.instinctools.data.giphy.repository.GifRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    GifRepository providesGifRepository(GifDataStoreFactory gifDataStoreFactory) {
        return new GifRepositoryImpl(gifDataStoreFactory);
    }
}
