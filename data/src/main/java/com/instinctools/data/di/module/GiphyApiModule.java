package com.instinctools.data.di.module;

import com.instinctools.data.giphy.api.GiphyObjectMapper;
import com.instinctools.data.giphy.api.GiphyService;
import com.instinctools.data.giphy.api.GiphyServiceFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class GiphyApiModule {

    @Provides
    GiphyService provideGiphyService() {
        return GiphyServiceFactory.createGiphyService();
    }

    @Provides
    GiphyObjectMapper provideGiphyObjectMapper() {
        return new GiphyObjectMapper();
    }
}
