package com.instinctools.common.mvp.di.component;

public interface ActivityComponent {

    interface Creator<T extends ActivityComponent> {
        T createActivityComponent();

        void inject();
    }
}
