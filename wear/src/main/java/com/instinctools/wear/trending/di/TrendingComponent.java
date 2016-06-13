package com.instinctools.wear.trending.di;

import com.instinctools.common.di.component.ActivityComponent;
import com.instinctools.common.di.component.ApplicationComponent;
import com.instinctools.common.di.module.ActivityModule;
import com.instinctools.common.di.scope.ActivityScope;
import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.wear.trending.TrendingActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, TrendingModule.class})
public interface TrendingComponent extends ActivityComponent {

    TrendingPresenter trendingPresenter();

    void inject(TrendingActivity trendingActivity);
}
