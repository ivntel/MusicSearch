package com.ivntel.android.musicsearch.retrofit;

import com.ivntel.android.musicsearch.model.SongsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by ivnte on 2017-05-13.
 */

public interface SearchApi {
    @GET("/search/")
    void getItunesSearchResults(@Query("term") String term, Callback<SongsResponse> searchCallback);
}