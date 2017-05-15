package com.ivntel.android.musicsearch.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ivnte on 2017-05-14.
 */

public class Lyric implements Serializable {

    public static final String LYRICS = "lyrics";

    @SerializedName(LYRICS)
    private String songLyrics;

    public String getSongLyrics() {
        return songLyrics;
    }

    public void setSongLyrics(String songLyrics) {
        this.songLyrics = songLyrics;
    }

    public static String getLYRICS() {
        return LYRICS;
    }
}
