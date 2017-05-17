package com.ivntel.android.musicsearch.model;

import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ivnte on 2017-05-14.
 */

public class LyricResponse implements Serializable {
    public static final String TAG = LyricResponse.class.getSimpleName();

    public static final String RESPONSE = "response";

    @SerializedName(RESPONSE)
    private Response response;

    public LyricResponse(){

    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static String getRESPONSE() {
        return RESPONSE;
    }
}

