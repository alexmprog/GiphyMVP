package com.instinctools.tv.trending;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.instinctools.common.ui.trending.TrendingGifPresenter;
import com.instinctools.common.ui.trending.TrendingGifView;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.tv.R;

public class TrendingCardPresenter extends Presenter {

    // should be different values
    private static final int CARD_WIDTH = 400;
    private static final int CARD_HEIGHT = 400;

    private static int selectedBackgroundColor;
    private static int defaultBackgroundColor;

    public TrendingCardPresenter() {
    }

    @Override
    public TrendingViewHolder onCreateViewHolder(ViewGroup parent) {
        final Context context = parent.getContext();
        defaultBackgroundColor = ContextCompat.getColor(context, R.color.colorPrimary);
        selectedBackgroundColor = ContextCompat.getColor(context, R.color.colorPrimaryDark);

        final ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new TrendingViewHolder(cardView);
    }

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? selectedBackgroundColor : defaultBackgroundColor;
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof Gif) {
            Gif gif = (Gif) item;
            TrendingGifPresenter presenter = new TrendingGifPresenter();
            presenter.setModel(gif);
            ((TrendingViewHolder) viewHolder).bindPresenter(presenter);
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ((TrendingViewHolder) viewHolder).unbindPresenter();
    }

    class TrendingViewHolder extends Presenter.ViewHolder implements TrendingGifView {

        ImageCardView cardView;

        ImageView imageView;

        TrendingGifPresenter presenter;

        public TrendingViewHolder(View view) {
            super(view);
            cardView = (ImageCardView) view;
            imageView = cardView.getMainImageView();
        }

        @Override
        public void showImage(@NonNull String imageUrl) {
            Context context = cardView.getContext();
            Glide.with(context).load(imageUrl).centerCrop().into(imageView);
            Glide.with(context).load(imageUrl).asGif().into(imageView);
        }

        @Override
        public void showGifImage(@NonNull String imageUrl) {
            Context context = cardView.getContext();
            Glide.with(context).load(imageUrl).centerCrop().into(imageView);
        }

        void bindPresenter(TrendingGifPresenter presenter) {
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            if (presenter != null) {
                this.presenter = presenter;
                this.presenter.attachView(this);
            }
        }

        void unbindPresenter() {
            cardView.setBadgeImage(null);
            cardView.setMainImage(null);
            if (presenter != null) {
                presenter.detachView();
                presenter = null;
            }
        }

        @Override
        public void goToGifDetails(@NonNull Gif gif) {
            // do nothing here - other click interface on tv item
        }
    }
}
