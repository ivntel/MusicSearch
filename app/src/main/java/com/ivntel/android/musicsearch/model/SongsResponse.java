package com.ivntel.android.musicsearch.model;

import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.List;

/**
 * Created by ivnte on 2017-05-13.
 */

public class SongsResponse implements Serializable {
    public static final String TAG = SongsResponse.class.getSimpleName();

    public static final String PROPERTY_RESULT_COUNT = "resultCount";
    public static final String PROPERTY_RESULTS = "results";

    @SerializedName(PROPERTY_RESULT_COUNT)
    private int resultCount;

    @SerializedName(PROPERTY_RESULTS)
    private List<Song> songs;

    public SongsResponse() {
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}