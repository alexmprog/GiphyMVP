package com.instinctools.tv.trending.di;

import com.instinctools.common.di.component.ActivityComponent;
import com.instinctools.common.di.component.ApplicationComponent;
import com.instinctools.common.di.module.ActivityModule;
import com.instinctools.common.di.scope.ActivityScope;
import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.tv.trending.TrendingFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, TrendingModule.class})
public interface TrendingComponent extends ActivityComponent {

    TrendingPresenter trendingPresenter();

    void inject(TrendingFragment trendingFragment);
}
