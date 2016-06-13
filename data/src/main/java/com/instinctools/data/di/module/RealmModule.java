package com.instinctools.data.di.module;

import android.content.Context;

import com.instinctools.data.realm.RealmManager;
import com.instinctools.data.realm.RealmObjectMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class RealmModule {

    @Provides
    RealmManager providesRealmManager(Context context, RealmObjectMapper realmObjectMapper) {
        return new RealmManager(context, realmObjectMapper);
    }

    @Provides
    RealmObjectMapper providesRealmObjectMapper() {
        return new RealmObjectMapper();
    }
}
