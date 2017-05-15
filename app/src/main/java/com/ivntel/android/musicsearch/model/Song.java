package com.ivntel.android.musicsearch.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by ivnte on 2017-05-13.
 */

public class Song implements Serializable {



    public static final String PROPERTY_ARTWORK_URL_100 = "artworkUrl100";
    public static final String PROPERTY_ARTIST_NAME = "artistName";
    public static final String PROPERTY_TRACK_NAME = "trackName";
    public static final String COLLECTION_NAME = "collectionName";
    public static final String TRACK_PRICE = "trackPrice";
    public static final String SONG_LENGTH = "trackTimeMillis";
    public static final String ALBUM_PRICE = "collectionPrice";
    public static final String GENRE_NAME = "primaryGenreName";


    @SerializedName(PROPERTY_ARTIST_NAME)
    private String artistName;

    @SerializedName(PROPERTY_TRACK_NAME)
    private String songName;

    @SerializedName(COLLECTION_NAME)
    private String collectionName;

    @SerializedName(TRACK_PRICE)
    private Double trackPrice;

    @SerializedName(SONG_LENGTH)
    private int songLength;

    @SerializedName(PROPERTY_ARTWORK_URL_100)
    private String artistUrl;

    @SerializedName(ALBUM_PRICE)
    private Double albumPrice;

    @SerializedName(GENRE_NAME)
    private String genreName;

    public Song() {
    }

    public int getSongLength() {
        return songLength;
    }

    public Double getAlbumPrice() { return albumPrice; }

    public void setAlbumPrice(Double albumPrice) { this.albumPrice = albumPrice; }

    public String getGenreName() { return genreName; }

    public void setGenreName(String genreName) { this.genreName = genreName; }

    public void setSongLength(int songLength) {
        this.songLength = songLength;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(Double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}