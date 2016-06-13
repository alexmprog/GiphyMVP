package com.instinctools.data.giphy.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Gif implements Parcelable {

    public abstract String getUserName();

    public abstract String getGifUrl();

    public abstract String getImageUrl();

    public static Gif create(final String userName, final String gifUrl, final String imageUrl) {
        return new AutoValue_Gif(userName, gifUrl, imageUrl);
    }
}
