package com.instinctools.wear.details.di;

import com.instinctools.common.mvp.di.component.ActivityComponent;
import com.instinctools.common.mvp.di.component.ApplicationComponent;
import com.instinctools.common.mvp.di.module.ActivityModule;
import com.instinctools.common.mvp.di.scope.ActivityScope;
import com.instinctools.wear.details.DetailsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, DetailsModule.class})
public interface DetailsComponent extends ActivityComponent {

    void inject(DetailsActivity detailsActivity);
}
