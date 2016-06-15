package com.instinctools.common.mvp.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.instinctools.common.mvp.presenter.BaseModelPresenter;
import com.instinctools.common.mvp.view.MvpView;


public class MvpRecyclerHolder<P extends BaseModelPresenter> extends RecyclerView.ViewHolder implements MvpView {

    protected P presenter;

    public MvpRecyclerHolder(View itemView) {
        super(itemView);
    }

    public void bindPresenter(P presenter) {
        if (presenter != null) {
            this.presenter = presenter;
            this.presenter.attachView(this);
        }
    }

    public void unbindPresenter() {
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }
}