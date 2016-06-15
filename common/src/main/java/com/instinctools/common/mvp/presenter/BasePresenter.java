package com.instinctools.common.mvp.presenter;

import com.instinctools.common.mvp.view.MvpView;

import java.lang.ref.WeakReference;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private WeakReference<T> mvpView;

    @Override
    public void attachView(T mvpView) {
        this.mvpView = new WeakReference<>(mvpView);
    }

    @Override
    public void detachView() {
        if (mvpView != null) {
            mvpView.clear();
        }
    }

    public boolean isViewAttached() {
        return mvpView != null && mvpView.get() != null;
    }

    public T getMvpView() {
        if (mvpView != null) {
            return mvpView.get();
        }
        return null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
