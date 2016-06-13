package com.instinctools.tv.trending;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.instinctools.common.ui.trending.TrendingPresenter;
import com.instinctools.common.ui.trending.TrendingView;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.tv.R;
import com.instinctools.tv.base.BaseActivity;
import com.instinctools.tv.details.DetailsActivity;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;

public class TrendingFragment extends VerticalGridFragment implements TrendingView {

    private static final int NUM_COLUMNS = 5;
    private static final int BACKGROUND_UPDATE_DELAY = 300;

    private BackgroundManager backgroundManager;
    private DisplayMetrics metrics;
    private Drawable defaultBackground;
    private Handler handler;
    private Runnable backgroundRunnable;
    private ProgressDialog progressDialog;

    @Inject
    TrendingPresenter trendingPresenter;

    @Inject
    TrendingAdapter trendingAdapter;

    public static TrendingFragment newInstance() {
        return new TrendingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TrendingActivity) getActivity()).getActivityComponent().inject(this);
        trendingPresenter.attachView(this);

        setupFragment();
        prepareBackgroundManager();
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

        setAdapter(trendingAdapter);
        setOnItemViewSelectedListener(mOnItemViewSelectedListener);
        setOnItemViewClickedListener(mOnItemViewClickedListener);
        trendingPresenter.getTrendingGifs();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (backgroundRunnable != null) {
            handler.removeCallbacks(backgroundRunnable);
            backgroundRunnable = null;
        }
        backgroundManager = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        backgroundManager.release();
    }

    private void prepareBackgroundManager() {
        backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        defaultBackground =
                new ColorDrawable(ContextCompat.getColor(getActivity(),
                        R.color.mid_gray));
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    private void setupFragment() {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        setBadgeDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.badge));

        handler = new Handler();
    }

    private void startBackgroundTimer(final URI backgroundURI) {
        if (backgroundRunnable != null) {
            handler.removeCallbacks(backgroundRunnable);
        }
        backgroundRunnable = new Runnable() {
            @Override
            public void run() {
                if (backgroundURI != null) {
                    updateBackground(backgroundURI.toString());
                }
            }
        };
        handler.postDelayed(backgroundRunnable, BACKGROUND_UPDATE_DELAY);
    }

    protected void updateBackground(String uri) {
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .asBitmap()
                .centerCrop()
                .error(defaultBackground)
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap resource,
                                                GlideAnimation<? super Bitmap>
                                                        glideAnimation) {
                        backgroundManager.setBitmap(resource);
                    }
                });
        if (backgroundRunnable != null) {
            handler.removeCallbacks(backgroundRunnable);
        }
    }

    private OnItemViewClickedListener mOnItemViewClickedListener = new OnItemViewClickedListener() {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Gif) {
                goToGif((Gif) item);
            }
        }
    };

    private OnItemViewSelectedListener mOnItemViewSelectedListener = new OnItemViewSelectedListener() {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Gif) {
                Gif gif = (Gif) item;
                String backgroundUrl = gif.getGifUrl();
                if (TextUtils.isEmpty(backgroundUrl)) {
                    backgroundUrl = gif.getImageUrl();
                }

                startBackgroundTimer(URI.create(backgroundUrl));
            }
        }
    };

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(getActivity(), getString(R.string.dialog_loading_title),
                    getString(R.string.dialog_loading_gifs), true);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void showGifs(@NonNull List<Gif> gifs) {
        trendingAdapter.addAll(0, gifs);
    }

    @Override
    public void showError() {
        // TODO: need show error message
    }

    @Override
    public void showEmpty() {
        // TODO: need show empty message
    }

    @Override
    public void showMessageLayout(boolean show) {
        // TODO: need show message layout
    }

    @Override
    public void goToGif(@NonNull Gif gif) {
        Activity activity = getActivity();
        activity.startActivity(DetailsActivity.getStartIntent(activity, gif));
    }

}
