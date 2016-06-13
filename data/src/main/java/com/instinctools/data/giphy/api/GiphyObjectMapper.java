package com.instinctools.data.giphy.api;

import android.support.annotation.NonNull;

import com.instinctools.data.giphy.model.Gif;
import com.instinctools.data.giphy.model.remote.GifData;

import java.util.ArrayList;
import java.util.List;

public class GiphyObjectMapper {

    public GiphyObjectMapper() {
    }

    public List<Gif> toListGif(@NonNull List<GifData> gifList) {

        List<Gif> gifs = new ArrayList<>();

        if (gifList.isEmpty()) {
            return gifs;
        }

        for (GifData gif : gifList) {
            gifs.add(Gif.create(gif.userName(), gif.downsizedGif(), gif.downsizedImage()));
        }

        return gifs;
    }
}
