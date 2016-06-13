package com.instinctools.data.di.module;

import android.content.Context;

import com.instinctools.data.prefs.Prefs;

import dagger.Module;
import dagger.Provides;

@Module
public class PrefsModule {
    @Provides
    Prefs providePrefs(Context context) {
        return new Prefs(context);
    }
}
