package com.asic45.neardeal28;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("near.deal")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
