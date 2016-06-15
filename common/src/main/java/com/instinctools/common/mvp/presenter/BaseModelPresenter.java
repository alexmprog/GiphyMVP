package com.instinctools.common.mvp.presenter;


import com.instinctools.common.mvp.view.MvpView;

public abstract class BaseModelPresenter<M, V extends MvpView> extends BasePresenter<V> {

    protected M model;

    public void setModel(M model) {
        resetState();
        this.model = model;
        if (setupDone()) {
            updateView();
        }
    }

    public M getModel() {
        return model;
    }

    protected void resetState() {
        // do nothing here - will implement in child class
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
        if (setupDone()) {
            updateView();
        }
    }

    protected boolean setupDone() {
        return getMvpView() != null && model != null;
    }

    protected abstract void updateView();
}
