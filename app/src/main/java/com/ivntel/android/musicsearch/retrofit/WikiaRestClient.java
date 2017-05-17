package com.ivntel.android.musicsearch.retrofit;

import com.ivntel.android.musicsearch.BuildConfig;

import retrofit.RestAdapter;

/**
 * Created by ivnte on 2017-05-14.
 */

public class WikiaRestClient {
    public static final String WIKIA_URL = "http://api.lyricfind.com";
    public static final boolean ENABLE_LOGGING = true;

    private static WikiaSearchApi wikiaSearchApi;

    private WikiaRestClient() {
        // Hidden constructor
    }

    static {
        // Setup our rest adapter and all APIs
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG && ENABLE_LOGGING ? RestAdapter.LogLevel.BASIC : RestAdapter.LogLevel.NONE)
                .setEndpoint(WIKIA_URL)
                .build();
        wikiaSearchApi = restAdapter.create(WikiaSearchApi.class);
    }

    public static WikiaSearchApi getWikiaSearchApi() {
        return wikiaSearchApi;
    }
}
