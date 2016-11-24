package com.zhaochunqi.android.gankio.network;


import com.zhaochunqi.android.gankio.beans.Datas;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by developer on 24/11/2016.
 */

public interface GankService {
    String URL = "http://gank.io";

    @GET("/api/data/{type}/{count}/{page}")
    Observable<Datas> getDatas(
            @Path("type") String type,
            @Path("count") String count,
            @Path("page") String page
    );
}
