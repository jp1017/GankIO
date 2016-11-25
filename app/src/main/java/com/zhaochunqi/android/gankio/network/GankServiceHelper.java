package com.zhaochunqi.android.gankio.network;


import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by developer on 24/11/2016.
 */

public class GankServiceHelper {
    private static GankService mGankService;
    private Application mApplication;

    public GankServiceHelper(Application application) {
        mApplication = application;
    }

    public GankService getGankService() {
        if (mGankService != null) {
            return mGankService;
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(mApplication.getCacheDir(), "cacheFileName"), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankService.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(GankService.class);
    }

}
