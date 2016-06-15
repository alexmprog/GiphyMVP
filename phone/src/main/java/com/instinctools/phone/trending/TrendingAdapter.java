package com.instinctools.phone.trending;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.instinctools.common.mvp.recycler.MvpRecyclerHolder;
import com.instinctools.common.mvp.recycler.MvpRecyclerAdapter;
import com.instinctools.common.ui.trending.TrendingGifPresenter;
import com.instinctools.common.ui.trending.TrendingGifView;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.phone.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrendingAdapter extends MvpRecyclerAdapter<Gif, TrendingGifPresenter, TrendingAdapter.TrendingGifViewHolder> {

    private ClickListener clickListener;

    public TrendingAdapter() {
        super();
    }

    @Override
    public TrendingGifViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TrendingGifViewHolder(inflater.inflate(R.layout.item_trending_gif,
                parent,
                false));
    }

    @NonNull
    @Override
    protected TrendingGifPresenter createPresenter(@NonNull Gif model) {
        TrendingGifPresenter trendingGifPresenter = new TrendingGifPresenter();
        trendingGifPresenter.setModel(model);
        return trendingGifPresenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Gif model) {
        return model.getImageUrl();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class TrendingGifViewHolder extends MvpRecyclerHolder<TrendingGifPresenter> implements TrendingGifView {

        @Bind(R.id.item_trending_gif_imageview)
        ImageView imageView;

        TrendingGifViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_trending_gif_imageview)
        void onGifClicked() {
            presenter.onGifClicked();
        }

        @Override
        public void showImage(@NonNull String imageUrl) {
            Context context = imageView.getContext();
            Glide.with(context).load(imageUrl).centerCrop().into(imageView);
        }

        @Override
        public void showGifImage(@NonNull String imageUrl) {
            Context context = imageView.getContext();
            Glide.with(context).load(imageUrl).asGif().into(imageView);
        }

        @Override
        public void goToGifDetails(@NonNull Gif gif) {
            if (clickListener != null) {
                clickListener.onGifClick(gif);
            }
        }
    }

    public interface ClickListener {
        void onGifClick(Gif gif);
    }
}

