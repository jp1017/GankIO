package com.zhaochunqi.android.gankio.network;

import com.orhanobut.logger.Logger;

import rx.functions.Action1;

/**
 * Created by developer on 26/11/2016.
 */

public class WebFailureAction implements Action1<Throwable> {
    @Override
    public void call(Throwable throwable) {
        Logger.d(throwable.getMessage());
    }
}
