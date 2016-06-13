package com.instinctools.tv.trending.di;

import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.domain.usecase.gif.GetTrendingGifsUseCase;
import com.instinctools.tv.trending.TrendingAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class TrendingModule {

    @Provides
    public TrendingPresenter provideTrendingPresenter(GetTrendingGifsUseCase getTrendingGifsUseCase) {
        return new TrendingPresenter(getTrendingGifsUseCase);
    }

    @Provides
    public TrendingAdapter provideTrendingAdapter() {
        return new TrendingAdapter();
    }
}
