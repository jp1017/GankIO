package com.zhaochunqi.android.gankio;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by developer on 25/11/2016.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
