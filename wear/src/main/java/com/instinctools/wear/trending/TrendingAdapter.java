package com.instinctools.wear.trending;

import android.content.Context;
import android.support.wearable.view.GridPagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.wear.R;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends GridPagerAdapter {

    private Context context;
    private List<Gif> gifs;
    private ClickListener clickListener;

    public TrendingAdapter(Context context) {
        this.context = context;
        this.gifs = new ArrayList<>();
    }

    public void setGifs(List<Gif> gifs) {
        this.gifs = gifs;
        notifyDataSetChanged();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getRowCount() {
        return gifs.size();
    }

    @Override
    public int getColumnCount(int i) {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i, int i1) {
        Gif shot = gifs.get(i);

        FrameLayout view = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.item_gif, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.bind(shot);
        view.setTag(viewHolder);
        viewGroup.addView(view);
        return viewHolder.frameLayout;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
        viewGroup.removeView((View) o);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    class ViewHolder {
        ImageView imageView;

        FrameLayout frameLayout;

        public ViewHolder(FrameLayout frameLayout) {
            this.frameLayout = frameLayout;
            imageView = (ImageView) frameLayout.findViewById(R.id.image_view);
        }

        void bind(final Gif gif) {
            String gifUrl = gif.getGifUrl();
            if (!TextUtils.isEmpty(gifUrl)) {
                Glide.with(context).load(gifUrl).asGif().into(imageView);
            } else {
                Glide.with(context).load(gif.getImageUrl()).centerCrop().into(imageView);
            }

            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onGifClick(gif);
                    }
                }
            });
        }
    }

    public interface ClickListener {
        void onGifClick(Gif gif);
    }
}