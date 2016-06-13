package com.instinctools.wear.trending.di;

import android.content.Context;

import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.domain.usecase.gif.GetTrendingGifsUseCase;
import com.instinctools.wear.trending.TrendingAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class TrendingModule {

    @Provides
    public TrendingPresenter providesTrendingPresenter(GetTrendingGifsUseCase getTrendingGifsUseCase) {
        return new TrendingPresenter(getTrendingGifsUseCase);
    }

    @Provides
    public TrendingAdapter providesTrendingAdapter(Context context) {
        return new TrendingAdapter(context);
    }

}
