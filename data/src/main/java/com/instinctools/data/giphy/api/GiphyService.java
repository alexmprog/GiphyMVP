package com.instinctools.data.giphy.api;

import com.instinctools.data.giphy.model.remote.TrendingResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GiphyService {

    @GET("v1/gifs/trending")
    Observable<TrendingResponse> latestTrending(@Query("api_key") String apiKey, @Query("rating") String rating);
}
