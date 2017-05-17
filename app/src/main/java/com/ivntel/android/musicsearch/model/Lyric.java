package com.ivntel.android.musicsearch.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lyric implements Serializable {


    public static final String TITLE = "title";

    public static final String LYRICS = "lyrics";

    @SerializedName(TITLE)
    private String title;

    @SerializedName(LYRICS)
    private String lyrics;

    public Lyric() {
    }

    public static String getTITLE() {
        return TITLE;
    }

    public static String getLYRICS() {
        return LYRICS;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
