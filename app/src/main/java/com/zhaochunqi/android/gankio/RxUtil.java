package com.zhaochunqi.android.gankio;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by developer on 26/11/2016.
 */

public class RxUtil {

    public static <T> Observable.Transformer<T, T> normalSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> source) {
                return source.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
