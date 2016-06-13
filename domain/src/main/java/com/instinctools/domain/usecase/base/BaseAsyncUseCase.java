package com.instinctools.domain.usecase.base;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseAsyncUseCase<T> extends BaseUseCase<T> {

    @Override
    protected Scheduler getSubscribeScheduler() {
        return Schedulers.io();
    }

    @Override
    protected Scheduler getObserveScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
