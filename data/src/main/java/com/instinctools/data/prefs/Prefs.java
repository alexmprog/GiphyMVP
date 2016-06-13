package com.instinctools.data.prefs;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.inject.Singleton;

@Singleton
public class Prefs {

    private final HashMap<Storage, SharedPreferences> mSharedPreferences = new HashMap<>();

    // need for storage access synchronization
    private final ReentrantReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();
    private final Lock mRead = mReadWriteLock.readLock();
    private final Lock mWrite = mReadWriteLock.writeLock();


    public Prefs(@NonNull Context context) {
        initializeVaults(context);
    }

    public boolean contains(@NonNull Storage storage, @NonNull String key) {
        SharedPreferences preferences = getPreferences(storage);
        mRead.lock();
        try {
            return preferences.contains(key);
        } finally {
            mRead.unlock();
        }
    }

    public Map<String, ?> getAll(@NonNull Storage storage) {
        SharedPreferences preferences = getPreferences(storage);
        mRead.lock();
        try {
            return preferences.getAll();
        } finally {
            mRead.unlock();
        }
    }

    public int getInt(@NonNull Storage storage, @NonNull String key, int defValue) {
        SharedPreferences preferences = getPreferences(storage);
        mRead.lock();
        try {
            return preferences.getInt(key, defValue);
        } finally {
            mRead.unlock();
        }
    }

    public boolean getBoolean(@NonNull Storage storage, @NonNull String key, boolean defValue) {
        SharedPreferences preferences = getPreferences(storage);
        mRead.lock();
        try {
            return preferences.getBoolean(key, defValue);
        } finally {
            mRead.unlock();
        }
    }

    public long getLong(@NonNull Storage storage, @NonNull String key, long defValue) {
        SharedPreferences preferences = getPreferences(storage);
        mRead.lock();
        try {
            return preferences.getLong(key, defValue);
        } finally {
            mRead.unlock();
        }
    }

    public float getFloat(@NonNull Storage storage, @NonNull String key, float defValue) {
        SharedPreferences preferences = getPreferences(storage);
        mRead.lock();
        try {
            return preferences.getFloat(key, defValue);
        } finally {
            mRead.unlock();
        }
    }

    public String getString(@NonNull Storage storage, @NonNull String key, String defValue) {
        SharedPreferences preferences = getPreferences(storage);
        mRead.lock();
        try {
            return preferences.getString(key, defValue);
        } finally {
            mRead.unlock();
        }
    }

    public Set<String> getStringSet(@NonNull Storage storage, @NonNull String key, Set<String> defValues) {
        SharedPreferences preferences = getPreferences(storage);
        mRead.lock();
        try {
            return preferences.getStringSet(key, defValues);
        } finally {
            mRead.unlock();
        }
    }

    public void putLong(@NonNull Storage storage, @NonNull String key, long value) {
        SharedPreferences.Editor editor = getEditor(storage);
        mWrite.lock();
        try {
            editor.putLong(key, value);
            editor.apply();
        } finally {
            mWrite.unlock();
        }
    }

    public void putInt(@NonNull Storage storage, @NonNull String key, int value) {
        SharedPreferences.Editor editor = getEditor(storage);
        mWrite.lock();
        try {
            editor.putInt(key, value);
            editor.apply();
        } finally {
            mWrite.unlock();
        }
    }

    public void putFloat(@NonNull Storage storage, @NonNull String key, float value) {
        SharedPreferences.Editor editor = getEditor(storage);
        mWrite.lock();
        try {
            editor.putFloat(key, value);
            editor.apply();
        } finally {
            mWrite.unlock();
        }
    }

    public void putBoolean(@NonNull Storage storage, @NonNull String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(storage);
        mWrite.lock();
        try {
            editor.putBoolean(key, value);
            editor.apply();
        } finally {
            mWrite.unlock();
        }
    }

    public void putString(@NonNull Storage storage, @NonNull String key, String value) {
        SharedPreferences.Editor editor = getEditor(storage);
        mWrite.lock();
        try {
            editor.putString(key, value);
            editor.apply();
        } finally {
            mWrite.unlock();
        }
    }

    public void putStringSet(@NonNull Storage storage, @NonNull String key, Set<String> value) {
        SharedPreferences.Editor editor = getEditor(storage);
        mWrite.lock();
        try {
            editor.putStringSet(key, value);
            editor.apply();
        } finally {
            mWrite.unlock();
        }
    }

    public void remove(@NonNull Storage storage, @NonNull String key) {
        SharedPreferences.Editor editor = getEditor(storage);
        mWrite.lock();
        try {
            editor.remove(key);
            editor.apply();
        } finally {
            mWrite.unlock();
        }
    }

    public void clear(@NonNull Storage storage) {
        SharedPreferences.Editor editor = getEditor(storage);
        mWrite.lock();
        try {
            editor.clear();
            editor.apply();
        } finally {
            mWrite.unlock();
        }
    }

    private void initializeVaults(@NonNull Context context) {
        for (Storage storage : Storage.values()) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(storage.toString(), ContextWrapper.MODE_PRIVATE);
            mSharedPreferences.put(storage, sharedPreferences);
        }
    }

    @NonNull
    private SharedPreferences.Editor getEditor(@NonNull Storage storage) {
        return getPreferences(storage).edit();
    }

    @NonNull
    private SharedPreferences getPreferences(@NonNull Storage storage) {
        return mSharedPreferences.get(storage);
    }

    public enum Storage {

        DEFAULT("default_prefs");

        private final String mText;

        Storage(String text) {
            mText = text;
        }

        public String toString() {
            return mText;
        }
    }
}
