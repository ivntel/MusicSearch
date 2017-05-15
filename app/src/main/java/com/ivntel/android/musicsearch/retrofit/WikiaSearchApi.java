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
    @GET("/api.php?func=getSong&artist=Tom+Waits&song=new+coat+of+paint&fmt=json")
    void getWikiaSearchResults(Callback<LyricResponse> callback);
}
