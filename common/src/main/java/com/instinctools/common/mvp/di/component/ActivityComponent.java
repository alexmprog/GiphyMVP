package com.instinctools.common.mvp.di.component;

public interface ActivityComponent {

    interface Injector<T extends ActivityComponent> {
        T createActivityComponent();

        void inject();
    }
}
