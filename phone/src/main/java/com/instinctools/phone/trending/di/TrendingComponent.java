package com.instinctools.phone.trending.di;

import com.instinctools.common.di.component.ActivityComponent;
import com.instinctools.common.di.component.ApplicationComponent;
import com.instinctools.common.di.module.ActivityModule;
import com.instinctools.common.di.scope.ActivityScope;
import com.instinctools.phone.trending.TrendingActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, TrendingModule.class})
public interface TrendingComponent extends ActivityComponent {

    void inject(TrendingActivity trendingActivity);
}
