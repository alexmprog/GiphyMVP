package com.instinctools.tv.trending.di;

import com.instinctools.common.mvp.di.component.ActivityComponent;
import com.instinctools.common.mvp.di.component.ApplicationComponent;
import com.instinctools.common.mvp.di.module.ActivityModule;
import com.instinctools.common.mvp.di.scope.ActivityScope;
import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.tv.trending.TrendingActivity;
import com.instinctools.tv.trending.TrendingFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, TrendingModule.class})
public interface TrendingComponent extends ActivityComponent {

    TrendingPresenter trendingPresenter();

    void inject(TrendingActivity trendingActivity);

    void inject(TrendingFragment trendingFragment);
}
