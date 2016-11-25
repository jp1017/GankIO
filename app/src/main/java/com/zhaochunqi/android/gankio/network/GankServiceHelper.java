package com.zhaochunqi.android.gankio.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by developer on 24/11/2016.
 */

public class GankServiceHelper {
    private static GankService mGankService;

    public static GankService getGankService() {
        if (mGankService != null) {
            return mGankService;
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankService.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(GankService.class);
    }
}
