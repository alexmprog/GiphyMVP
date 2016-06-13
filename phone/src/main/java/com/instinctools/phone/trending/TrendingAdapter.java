package com.instinctools.phone.trending;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.phone.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingGifViewHolder> {

    private List<Gif> gifs;
    private ClickListener clickListener;

    public TrendingAdapter() {
        this.gifs = new ArrayList<>();
    }

    @Override
    public TrendingGifViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TrendingGifViewHolder(inflater.inflate(R.layout.item_trending_gif,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TrendingGifViewHolder holder, int position) {
        final Gif gif = gifs.get(position);
        holder.bindData(gif);
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    void setGifs(@NonNull final List<Gif> gifs) {
        this.gifs.clear();
        this.gifs.addAll(gifs);
        notifyDataSetChanged();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class TrendingGifViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_trending_gif_imageview)
        ImageView imageView;

        Gif gif;

        TrendingGifViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(@NonNull Gif gif) {
            this.gif = gif;

            Context context = imageView.getContext();
            String gifUrl = gif.getGifUrl();
            if (!TextUtils.isEmpty(gifUrl)) {
                Glide.with(context).load(gifUrl).asGif().into(imageView);
            } else {
                Glide.with(context).load(gif.getImageUrl()).centerCrop().into(imageView);
            }
        }

        @OnClick(R.id.item_trending_gif_imageview)
        void onGifClicked() {
            if (clickListener != null) {
                clickListener.onGifClick(gif);
            }
        }
    }

    public interface ClickListener {
        void onGifClick(Gif gif);
    }
}

