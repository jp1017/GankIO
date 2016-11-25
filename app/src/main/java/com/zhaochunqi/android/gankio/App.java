package com.zhaochunqi.android.gankio;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by developer on 25/11/2016.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }
}
