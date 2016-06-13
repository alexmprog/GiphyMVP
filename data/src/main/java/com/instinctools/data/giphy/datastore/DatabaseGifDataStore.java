package com.instinctools.data.giphy.datastore;

import com.instinctools.data.giphy.model.Gif;
import com.instinctools.data.prefs.PrefKeys;
import com.instinctools.data.prefs.Prefs;
import com.instinctools.data.realm.RealmManager;

import java.util.List;

import rx.Observable;

public class DatabaseGifDataStore implements GifDataStore {

    private RealmManager realmManager;

    private Prefs prefs;

    public DatabaseGifDataStore(RealmManager realmManager, Prefs prefs) {
        this.realmManager = realmManager;
        this.prefs = prefs;
    }

    @Override
    public Observable<List<Gif>> getTrendingGifs() {
        return realmManager.getTrendingGifs();
    }

    @Override
    public void saveTrendingGifs(List<Gif> gifList) {
        prefs.putLong(Prefs.Storage.DEFAULT, PrefKeys.GET_TRENDING_GIF_REQUEST_TIME, System.currentTimeMillis());
        realmManager.saveRealmGifs(gifList);
    }

    @Override
    public boolean isDataExpired(long timeStamp) {
        long lastTime = prefs.getLong(Prefs.Storage.DEFAULT, PrefKeys.GET_TRENDING_GIF_REQUEST_TIME, 0);
        return timeStamp - 60 * 1000 > lastTime;
    }
}
