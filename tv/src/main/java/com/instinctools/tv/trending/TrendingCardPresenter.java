package com.instinctools.tv.trending;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
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
        return new ViewHolder(cardView);
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

            final ImageCardView cardView = (ImageCardView) viewHolder.view;
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

            Context context = cardView.getContext();
            ImageView imageView = cardView.getMainImageView();

            String gifUrl = gif.getGifUrl();
            if (!TextUtils.isEmpty(gifUrl)) {
                Glide.with(context).load(gifUrl).asGif().into(imageView);
            } else {
                Glide.with(context).load(gif.getImageUrl()).centerCrop().into(imageView);
            }
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        if (viewHolder.view instanceof ImageCardView) {
            ImageCardView cardView = (ImageCardView) viewHolder.view;

            // clear images from memort
            cardView.setBadgeImage(null);
            cardView.setMainImage(null);
        }
    }
}
