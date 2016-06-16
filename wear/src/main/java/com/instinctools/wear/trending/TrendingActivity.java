package com.instinctools.wear.trending;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.instinctools.common.GiphyApp;
import com.instinctools.common.mvp.di.module.ActivityModule;
import com.instinctools.common.mvp.ui.activity.BaseMvpActivity;
import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.common.ui.trending.TrendingView;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.wear.R;
import com.instinctools.wear.details.DetailsActivity;
import com.instinctools.wear.trending.di.DaggerTrendingComponent;
import com.instinctools.wear.trending.di.TrendingComponent;
import com.instinctools.wear.trending.di.TrendingModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrendingActivity extends BaseMvpActivity<TrendingView, TrendingPresenter, TrendingComponent> implements TrendingView, TrendingAdapter.ClickListener {

    @Bind(R.id.pager_shots)
    GridViewPager trendingPager;
    @Bind(R.id.page_indicator)
    DotsPageIndicator pageIndicator;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.layout_error)
    View errorView;
    @Bind(R.id.text_error)
    TextView errorText;
    @Bind(R.id.image_error)
    ImageView errorImage;

    @Inject
    TrendingAdapter trendingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        ButterKnife.bind(this);
        getMvpPresenter().attachView(this);

        trendingPager.setAdapter(trendingAdapter);
        trendingAdapter.setClickListener(this);
        pageIndicator.setPager(trendingPager);
        pageIndicator.setDotColor(ContextCompat.getColor(this, R.color.colorPrimary));
        pageIndicator.setDotColorSelected(ContextCompat.getColor(this, R.color.colorAccent));
        pageIndicator.setDotRadius(4);
        getMvpPresenter().getTrendingGifs();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        errorImage.setImageResource(R.drawable.ic_sentiment_data);
        errorText.setText(getString(R.string.text_error_loading_gifs));
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        errorImage.setImageResource(R.drawable.ic_empty_data);
        errorText.setText(getString(R.string.text_no_shots));
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessageLayout(boolean show) {
        errorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.layout_error)
    public void onErrorLayoutClick() {
        getMvpPresenter().getTrendingGifs();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public TrendingComponent createActivityComponent() {
        return DaggerTrendingComponent.builder()
                .applicationComponent(GiphyApp.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .trendingModule(new TrendingModule())
                .build();
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showData(@NonNull List<Gif> gifs) {
        trendingAdapter.setGifs(gifs);
    }

    @Override
    public void goToGif(@NonNull Gif gif) {
        startActivity(DetailsActivity.getStartIntent(this, gif));
    }

    @Override
    public void onGifClick(Gif gif) {
        getMvpPresenter().onGifClicked(gif);
    }
}
