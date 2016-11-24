package com.zhaochunqi.android.gankio.network;


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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(GankService.class);
    }
}
