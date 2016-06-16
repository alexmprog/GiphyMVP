package com.instinctools.tv.trending;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.instinctools.common.GiphyApp;
import com.instinctools.common.mvp.di.module.ActivityModule;
import com.instinctools.common.mvp.ui.activity.BaseMvpActivity;
import com.instinctools.tv.R;
import com.instinctools.tv.trending.di.DaggerTrendingComponent;
import com.instinctools.tv.trending.di.TrendingComponent;
import com.instinctools.tv.trending.di.TrendingModule;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrendingActivity extends BaseMvpActivity<BaseTrendingView, BaseTrendingPresenter, TrendingComponent> implements BaseTrendingView {

    @Bind(R.id.frame_container)
    FrameLayout fragmentContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        ButterKnife.bind(this);
        getMvpPresenter().attachView(this);
    }

    @Override
    public boolean onSearchRequested() {
        return true;
    }

    @Override
    public TrendingComponent createActivityComponent() {
        return DaggerTrendingComponent.builder()
                .applicationComponent(GiphyApp.get(this).getComponent())
                .trendingModule(new TrendingModule())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showTrendingView() {
        getFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), TrendingFragment.newInstance()).commit();
    }
}
