package com.instinctools.phone.trending;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.instinctools.common.GiphyApp;
import com.instinctools.common.mvp.di.module.ActivityModule;
import com.instinctools.common.mvp.ui.activity.BaseMvpAppCompatActivity;
import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.common.ui.trending.TrendingView;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.phone.R;
import com.instinctools.phone.details.DetailsActivity;
import com.instinctools.phone.trending.di.DaggerTrendingComponent;
import com.instinctools.phone.trending.di.TrendingComponent;
import com.instinctools.phone.trending.di.TrendingModule;
import com.instinctools.phone.util.DisplayMetricsUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrendingActivity extends BaseMvpAppCompatActivity<TrendingView, TrendingPresenter, TrendingComponent> implements TrendingView, TrendingAdapter.ClickListener {

    @Bind(R.id.button_message)
    Button messageButton;

    @Bind(R.id.image_message)
    ImageView messageImage;

    @Bind(R.id.progress)
    ProgressBar recyclerProgress;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerGif;

    @Bind(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.text_message)
    TextView messageText;

    @Bind(R.id.toolbar_browse)
    Toolbar toolbar;

    @Bind(R.id.layout_message)
    View messageLayout;

    private boolean isTabletLayout;

    @Inject
    TrendingAdapter trendingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        ButterKnife.bind(this);

        getMvpPresenter().attachView(this);

        setSupportActionBar(toolbar);
        isTabletLayout = DisplayMetricsUtil.isScreenW(600);

        setupViews();
        getMvpPresenter().getTrendingGifs();
    }

    private void setupViews() {
        trendingAdapter.setClickListener(this);

        recyclerGif.setLayoutManager(setLayoutManager());
        recyclerGif.setHasFixedSize(true);
        recyclerGif.setAdapter(trendingAdapter);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMvpPresenter().getTrendingGifs();
            }
        });
    }

    private RecyclerView.LayoutManager setLayoutManager() {
        RecyclerView.LayoutManager layoutManager;
        if (!isTabletLayout) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 3;
                }
            });
            layoutManager = gridLayoutManager;
        }
        return layoutManager;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button_message)
    public void onReloadButtonClick() {
        getMvpPresenter().getTrendingGifs();
    }

    @Override
    public void showProgress() {
        if (recyclerGif.getVisibility() == View.VISIBLE && trendingAdapter.getItemCount() > 0) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            recyclerProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
        recyclerProgress.setVisibility(View.GONE);
    }

    @Override
    public void showData(@NonNull List<Gif> gifs) {
        recyclerGif.setVisibility(View.VISIBLE);
        trendingAdapter.clearAndAddAll(gifs);
    }

    @Override
    public void showError() {
        recyclerGif.setVisibility(View.GONE);
        messageImage.setImageResource(R.drawable.ic_sentiment_data);
        messageText.setText(getString(R.string.text_error_loading_gifs));
        messageButton.setText(getString(R.string.text_reload));
        showMessageLayout(true);
    }

    @Override
    public void showEmpty() {
        recyclerGif.setVisibility(View.GONE);
        messageImage.setImageResource(R.drawable.ic_empty_data);
        messageText.setText(getString(R.string.text_no_shots));
        messageButton.setText(getString(R.string.text_check_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        messageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void goToGif(@NonNull Gif gif) {
        startActivity(DetailsActivity.getStartIntent(this, gif));
    }

    @Override
    public TrendingComponent createActivityComponent() {
        return DaggerTrendingComponent
                .builder()
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
    public void onGifClick(Gif gif) {
        goToGif(gif);
    }
}
