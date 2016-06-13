package com.instinctools.data.giphy.repository;

import com.instinctools.data.giphy.model.Gif;

import java.util.List;

import rx.Observable;

public interface GifRepository {

    Observable<List<Gif>> getTrendingGifs();
}
