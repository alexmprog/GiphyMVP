package com.instinctools.wear.trending.di;

import com.instinctools.common.mvp.di.component.ActivityComponent;
import com.instinctools.common.mvp.di.component.ApplicationComponent;
import com.instinctools.common.mvp.di.module.ActivityModule;
import com.instinctools.common.mvp.di.scope.ActivityScope;
import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.wear.trending.TrendingActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, TrendingModule.class})
public interface TrendingComponent extends ActivityComponent {

    TrendingPresenter trendingPresenter();

    void inject(TrendingActivity trendingActivity);
}
