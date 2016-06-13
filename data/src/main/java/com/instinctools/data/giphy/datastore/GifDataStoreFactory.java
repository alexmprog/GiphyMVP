package com.instinctools.data.giphy.datastore;

import com.instinctools.data.giphy.api.GiphyObjectMapper;
import com.instinctools.data.giphy.api.GiphyService;
import com.instinctools.data.prefs.Prefs;
import com.instinctools.data.realm.RealmManager;

public class GifDataStoreFactory {

    private RealmManager realmManager;
    private GiphyService giphyService;
    private GiphyObjectMapper giphyObjectMapper;
    private Prefs prefs;

    private DatabaseGifDataStore databaseGifDataStore;

    private NetworkGifDataStore networkGifDataStore;

    public GifDataStoreFactory(RealmManager realmManager, GiphyService giphyService, GiphyObjectMapper giphyObjectMapper, Prefs prefs) {
        this.realmManager = realmManager;
        this.giphyService = giphyService;
        this.giphyObjectMapper = giphyObjectMapper;
        this.prefs = prefs;
    }

    public DatabaseGifDataStore getDatabaseGifStorage() {
        if (databaseGifDataStore == null) {
            databaseGifDataStore = new DatabaseGifDataStore(realmManager, prefs);
        }
        return databaseGifDataStore;
    }

    public NetworkGifDataStore getNetworkGifStorage() {
        if (networkGifDataStore == null) {
            networkGifDataStore = new NetworkGifDataStore(giphyService, giphyObjectMapper);
        }
        return networkGifDataStore;
    }
}
