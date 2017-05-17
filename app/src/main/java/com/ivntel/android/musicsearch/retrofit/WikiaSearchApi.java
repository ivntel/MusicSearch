package com.ivntel.android.musicsearch.retrofit;

import android.content.Intent;

import com.ivntel.android.musicsearch.LyricsActivity;
import com.ivntel.android.musicsearch.model.LyricResponse;
import com.ivntel.android.musicsearch.model.Song;
import com.ivntel.android.musicsearch.model.SongsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import static com.ivntel.android.musicsearch.LyricsActivity.ARG_SONG;

/**
 * Created by ivnte on 2017-05-14.
 */

public interface WikiaSearchApi {

    @GET("/lyric.do?apikey=651165cb611eab8d3eb75f097fbc7701&territory=US&reqtype=offlineviews&trackid=")
    void getWikiaSearchResults(String artistNameAndSong, Callback<LyricResponse> callback);
}
