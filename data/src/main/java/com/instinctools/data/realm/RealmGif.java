package com.instinctools.data.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmGif extends RealmObject {

    private String userName;

    private String gifUrl;

    @PrimaryKey
    private String imageUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
