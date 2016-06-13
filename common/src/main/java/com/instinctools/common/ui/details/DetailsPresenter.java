package com.instinctools.common.ui.details;

import com.instinctools.common.ui.base.BasePresenter;
import com.instinctools.data.giphy.model.Gif;

public class DetailsPresenter extends BasePresenter<DetailsView> {

    public void checkData(Gif gif) {
        // TODO: can add additional logic
        getMvpView().showGif(gif);
    }
}
