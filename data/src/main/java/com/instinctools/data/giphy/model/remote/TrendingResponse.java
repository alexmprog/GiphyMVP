package com.instinctools.data.giphy.model.remote;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class TrendingResponse {

    public static TrendingResponse create(final List<GifData> gifs) {
        return new AutoValue_TrendingResponse(gifs);
    }

    public static TypeAdapter<TrendingResponse> typeAdapter(final Gson gson) {
        return new AutoValue_TrendingResponse.GsonTypeAdapter(gson);
    }

    @SerializedName("data")
    public abstract List<GifData> gifs();
}