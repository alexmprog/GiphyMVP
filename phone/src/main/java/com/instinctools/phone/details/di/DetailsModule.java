package com.instinctools.phone.details.di;

import com.instinctools.common.ui.details.DetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailsModule {

    @Provides
    public DetailsPresenter providesDetailsPresenter() {
        return new DetailsPresenter();
    }
}
