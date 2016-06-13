package com.instinctools.data.giphy.repository;

import com.instinctools.data.giphy.datastore.DatabaseGifDataStore;
import com.instinctools.data.giphy.datastore.GifDataStoreFactory;
import com.instinctools.data.giphy.datastore.NetworkGifDataStore;
import com.instinctools.data.giphy.model.Gif;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class GifRepositoryImpl implements GifRepository {

    private GifDataStoreFactory gifDataStoreFactory;

    public GifRepositoryImpl(GifDataStoreFactory gifDataStoreFactory) {
        this.gifDataStoreFactory = gifDataStoreFactory;
    }

    @Override
    public Observable<List<Gif>> getTrendingGifs() {
        final DatabaseGifDataStore databaseGifStorage = gifDataStoreFactory.getDatabaseGifStorage();
        if (!databaseGifStorage.isDataExpired(System.currentTimeMillis())) {
            return databaseGifStorage.getTrendingGifs();
        } else {
            NetworkGifDataStore networkGifStorage = gifDataStoreFactory.getNetworkGifStorage();
            return networkGifStorage.getTrendingGifs().map(new Func1<List<Gif>, List<Gif>>() {
                @Override
                public List<Gif> call(List<Gif> gifList) {
                    databaseGifStorage.saveTrendingGifs(gifList);
                    return gifList;
                }
            });
        }
    }
}
