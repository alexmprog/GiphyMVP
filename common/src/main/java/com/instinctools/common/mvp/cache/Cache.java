package com.instinctools.common.mvp.cache;

import android.support.annotation.NonNull;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import timber.log.Timber;

public class Cache<K, V> {

    private final ConcurrentHashMap<K, V> objectsMap;

    private long expirationValue;

    private final ConcurrentHashMap<K, Long> timestampsMap;

    private Evictor evictor;

    public Cache(long expirationValue) {
        this.expirationValue = expirationValue;
        this.objectsMap = new ConcurrentHashMap<>();
        this.timestampsMap = new ConcurrentHashMap<>();

        // create and start cache evictor
        this.evictor = new Evictor();
        evictor.start();
    }

    public void put(@NonNull K key, @NonNull V value) {
        long timestamp = System.currentTimeMillis();
        objectsMap.put(key, value);
        timestampsMap.put(key, timestamp);
    }

    public V getIfPresent(@NonNull K key) {
        V value = objectsMap.get(key);
        if (value == null) {
            return null;
        }

        Long time = timestampsMap.get(key);
        if (time == null) {
            return null;
        }

        long timestamp = System.currentTimeMillis();
        if (timestamp - expirationValue < time) {
            return value;
        }

        return null;
    }

    public void invalidate(@NonNull K key) {
        objectsMap.remove(key);
        timestampsMap.remove(key);
    }

    final class Evictor extends Thread {

        // can change this time in future
        public static final int SLEEP_TIME = 1000 * 60;

        @Override
        public void run() {

            while (true) {
                Set<K> keySet = timestampsMap.keySet();
                if (!keySet.isEmpty()) {
                    long timestamp = System.currentTimeMillis();
                    for (K key : keySet) {
                        Long time = timestampsMap.get(key);
                        if (timestamp - expirationValue > time) {
                            // need remove object from cache
                            timestampsMap.remove(key);
                        }
                    }
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    Timber.e("Evictor thread is interrupted");
                }
            }
        }
    }
}