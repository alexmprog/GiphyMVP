package com.instinctools.tv.trending;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.instinctools.common.GiphyApp;
import com.instinctools.common.di.module.ActivityModule;
import com.instinctools.tv.R;
import com.instinctools.tv.base.BaseActivity;
import com.instinctools.tv.trending.di.DaggerTrendingComponent;
import com.instinctools.tv.trending.di.TrendingComponent;
import com.instinctools.tv.trending.di.TrendingModule;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrendingActivity extends BaseActivity<TrendingComponent> {

    @Bind(R.id.frame_container)
    FrameLayout fragmentContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        ButterKnife.bind(this);
        showTrendingFragment();
    }

    @Override
    public boolean onSearchRequested() {
        return true;
    }

    public void showTrendingFragment() {
        getFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), TrendingFragment.newInstance()).commit();
    }

    @Override
    protected TrendingComponent createActivityComponent() {
        return DaggerTrendingComponent.builder()
                .applicationComponent(GiphyApp.get(this).getComponent())
                .trendingModule(new TrendingModule())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
