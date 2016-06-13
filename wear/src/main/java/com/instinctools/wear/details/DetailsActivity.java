package com.instinctools.wear.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.instinctools.common.GiphyApp;
import com.instinctools.common.di.module.ActivityModule;
import com.instinctools.common.ui.details.DetailsPresenter;
import com.instinctools.common.ui.details.DetailsView;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.wear.R;
import com.instinctools.wear.base.BaseActivity;
import com.instinctools.wear.details.di.DaggerDetailsComponent;
import com.instinctools.wear.details.di.DetailsComponent;
import com.instinctools.wear.details.di.DetailsModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity<DetailsComponent> implements DetailsView {

    public static final String EXTRA_GIF = "com.instinctools.wear.details.EXTRA_GIF";

    public static Intent getStartIntent(Context context, Gif gif) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_GIF, gif);
        return intent;
    }

    @Bind(R.id.image_view)
    ImageView imageView;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    DetailsPresenter detailsPresenter;

    private RequestListener<String, GlideDrawable> glideDrawableRequestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
        }
    };

    private RequestListener<String, GifDrawable> glideGifDrawableRequestListener = new RequestListener<String, GifDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
        }
    };

    @Override
    protected DetailsComponent createActivityComponent() {
        return DaggerDetailsComponent.builder()
                .applicationComponent(GiphyApp.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .detailsModule(new DetailsModule())
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        detailsPresenter.attachView(this);

        Gif gif = getIntent().getParcelableExtra(EXTRA_GIF);
        if (gif == null) {
            throw new IllegalArgumentException("Details activity requires a gif instance!");
        }

        detailsPresenter.checkData(gif);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        detailsPresenter.detachView();
    }

    @Override
    public void showGif(@NonNull Gif gif) {
        String gifUrl = gif.getGifUrl();
        if (!TextUtils.isEmpty(gifUrl)) {
            Glide.with(this).load(gifUrl).asGif().listener(glideGifDrawableRequestListener).into(imageView);
        } else {
            Glide.with(this).load(gif.getImageUrl()).centerCrop().listener(glideDrawableRequestListener).into(imageView);
        }
        progressBar.setVisibility(View.VISIBLE);
    }
}
