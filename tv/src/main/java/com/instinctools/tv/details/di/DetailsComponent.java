package com.instinctools.tv.details.di;

import com.instinctools.common.di.component.ActivityComponent;
import com.instinctools.common.di.component.ApplicationComponent;
import com.instinctools.common.di.module.ActivityModule;
import com.instinctools.common.di.scope.ActivityScope;
import com.instinctools.tv.details.DetailsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, DetailsModule.class})
public interface DetailsComponent extends ActivityComponent {

    void inject(DetailsActivity detailsActivity);
}
