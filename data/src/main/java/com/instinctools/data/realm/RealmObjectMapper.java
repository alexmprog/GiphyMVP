package com.instinctools.data.realm;

import android.support.annotation.NonNull;

import com.instinctools.data.giphy.model.Gif;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class RealmObjectMapper {

    public RealmObjectMapper() {
    }

    public List<RealmGif> fromListGif(@NonNull List<Gif> gifList) {
        List<RealmGif> realmGifs = new ArrayList<>();

        if (gifList.isEmpty()) {
            return realmGifs;
        }

        for (Gif gif : gifList) {
            RealmGif realmGif = new RealmGif();
            realmGif.setUserName(gif.getUserName());
            realmGif.setGifUrl(gif.getGifUrl());
            realmGif.setImageUrl(gif.getImageUrl());
            realmGifs.add(realmGif);
        }

        return realmGifs;
    }

    public List<Gif> toListGif(@NonNull List<RealmGif> realmGifList) {
        List<Gif> gifs = new ArrayList<>();

        if (realmGifList.isEmpty()) {
            return gifs;
        }

        for (RealmGif gif : realmGifList) {
            gifs.add(Gif.create(gif.getUserName(), gif.getGifUrl(), gif.getImageUrl()));
        }

        return gifs;
    }
}
