package com.instinctools.data.giphy.datastore;

import com.instinctools.data.BuildConfig;
import com.instinctools.data.giphy.api.GiphyObjectMapper;
import com.instinctools.data.giphy.api.GiphyService;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.data.giphy.model.remote.TrendingResponse;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class NetworkGifDataStore implements GifDataStore {

    private GiphyService giphyService;

    private GiphyObjectMapper giphyObjectMapper;

    public NetworkGifDataStore(GiphyService giphyService, GiphyObjectMapper giphyObjectMapper) {
        this.giphyService = giphyService;
        this.giphyObjectMapper = giphyObjectMapper;
    }

    @Override
    public Observable<List<Gif>> getTrendingGifs() {
        return giphyService.latestTrending(BuildConfig.GIPHY_ACCESS_TOKEN, "g").map(new Func1<TrendingResponse, List<Gif>>() {
            @Override
            public List<Gif> call(TrendingResponse trendingResponse) {
                return giphyObjectMapper.toListGif(trendingResponse.gifs());
            }
        });
    }

    @Override
    public void saveTrendingGifs(List<Gif> gifList) {
        // TODO: not supported - we don't plan store some data on server side
    }

    @Override
    public boolean isDataExpired(long timeStamp) {
        // always expired - need load new data
        return true;
    }
}
