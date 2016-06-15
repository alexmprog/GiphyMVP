package com.instinctools.common.ui.trending;

import android.support.annotation.NonNull;

import com.instinctools.common.mvp.presenter.BasePresenter;
import com.instinctools.data.giphy.model.Gif;
import com.instinctools.domain.usecase.gif.GetTrendingGifsUseCase;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class TrendingPresenter extends BasePresenter<TrendingView> {

    private GetTrendingGifsUseCase getTrendingGifsUseCase;
    private Subscription subscription;

    public TrendingPresenter(GetTrendingGifsUseCase getTrendingGifsUseCase) {
        this.getTrendingGifsUseCase = getTrendingGifsUseCase;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void onGifClicked(@NonNull Gif gif) {
        getMvpView().goToGif(gif);
    }

    public void getTrendingGifs() {
        checkViewAttached();
        getMvpView().showMessageLayout(false);
        getMvpView().showProgress();

        subscription = getTrendingGifsUseCase.perform().subscribe(new Action1<List<Gif>>() {
            @Override
            public void call(List<Gif> gifList) {
                getMvpView().hideProgress();
                if (!gifList.isEmpty()) {
                    getMvpView().showGifs(gifList);
                } else {
                    getMvpView().showEmpty();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Timber.e(throwable, "There was an error retrieving the shots");
                getMvpView().hideProgress();
                getMvpView().showError();
            }
        });
    }
}
