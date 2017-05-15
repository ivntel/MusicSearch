package com.ivntel.android.musicsearch.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ivnte on 2017-05-14.
 */

public class LyricResponse implements Serializable {
    public static final String TAG = LyricResponse.class.getSimpleName();

    public static final String SONG_LYRICS = "song";

    @SerializedName(SONG_LYRICS)
    private List<Lyric> lyrics;

    public LyricResponse() {
    }

    public List<Lyric> getLyrics() {
        return lyrics;
    }

    public void setLyrics(List<Lyric> lyrics) {
        this.lyrics = lyrics;
    }
}