package com.instinctools.domain.usecase.base;

import rx.Observable;
import rx.Scheduler;

public abstract class BaseUseCase<T> {

    public BaseUseCase() {
    }

    public Observable<T> perform() {
        return doWork()
                .subscribeOn(getSubscribeScheduler())
                .observeOn(getObserveScheduler());
    }

    protected abstract Observable<T> doWork();

    protected abstract Scheduler getSubscribeScheduler();

    protected abstract Scheduler getObserveScheduler();

}
