package com.instinctools.domain.di;

import com.instinctools.data.giphy.repository.GifRepository;
import com.instinctools.domain.usecase.gif.GetTrendingGifsUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {

    @Provides
    public GetTrendingGifsUseCase providesGetTrendingGifsUseCase(GifRepository gifRepository) {
        return new GetTrendingGifsUseCase(gifRepository);
    }
}
