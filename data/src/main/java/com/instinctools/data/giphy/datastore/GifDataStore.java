package com.instinctools.data.giphy.datastore;

import com.instinctools.data.giphy.model.Gif;

import java.util.List;

import rx.Observable;

public interface GifDataStore {

    Observable<List<Gif>> getTrendingGifs();

    void saveTrendingGifs(List<Gif> gifList);

    boolean isDataExpired(long timeStamp);
}
