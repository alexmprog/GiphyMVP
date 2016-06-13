package com.instinctools.tv.trending;

import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

public class TrendingAdapter extends ArrayObjectAdapter {

    private TrendingCardPresenter presenter;

    public TrendingAdapter() {
        presenter = new TrendingCardPresenter();
        setPresenterSelector();
    }

    public void setPresenterSelector() {
        setPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return presenter;
            }
        });
    }

}