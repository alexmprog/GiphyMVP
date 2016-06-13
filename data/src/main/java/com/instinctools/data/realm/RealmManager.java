package com.instinctools.data.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.instinctools.data.giphy.model.Gif;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import timber.log.Timber;

@Singleton
public class RealmManager {

    private Realm realm;
    private RealmObjectMapper realmObjectMapper;

    // need for execute operation with realm
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public RealmManager(@NonNull Context context, RealmObjectMapper realmObjectMapper) {
        this.realmObjectMapper = realmObjectMapper;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    protected Realm getRealm() {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    public void saveRealmGifs(final @NonNull List<Gif> realmGifList) {
        Future<Boolean> submit = executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (realmGifList.isEmpty()) {
                    return false;
                }

                Realm realm = getRealm();
                if (realm == null) {
                    return false;
                }

                //TODO: need remove previous data from database
                final RealmQuery<RealmGif> query = realm.where(RealmGif.class);
                RealmResults<RealmGif> realmGifs = query.findAll();
                realm.beginTransaction();
                realmGifs.deleteAllFromRealm();
                realm.commitTransaction();

                //store data in database
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(realmObjectMapper.fromListGif(realmGifList));
                realm.commitTransaction();
                return true;
            }
        });
        try {
            submit.get();
        } catch (Exception e) {
            Timber.e(e, "Cannot store trending gifs ");
        }
    }

    public Observable<List<Gif>> getTrendingGifs() {
        Future<List<Gif>> submit = executorService.submit(new Callable<List<Gif>>() {
            @Override
            public List<Gif> call() throws Exception {
                Realm realm = getRealm();
                if (realm == null) {
                    return new ArrayList<>();
                }

                final RealmQuery<RealmGif> query = realm.where(RealmGif.class);
                RealmResults<RealmGif> realmGifs = query.findAll();

                List<RealmGif> gifs = new ArrayList<>();
                for (RealmGif realmGif : realmGifs) {
                    gifs.add(realmGif);
                }

                return realmObjectMapper.toListGif(gifs);
            }
        });

        return Observable.from(submit);
    }
}
