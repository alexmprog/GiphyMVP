package com.instinctools.data.giphy.model.remote;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

@AutoValue
public abstract class GifData {

    @SerializedName("username")
   public abstract String userName();

    public abstract Map<String, Map<String, String>> images();

    public static TypeAdapter<GifData> typeAdapter(final Gson gson) {
        return new AutoValue_GifData.GsonTypeAdapter(gson);
    }

    public String downsizedGif() {
        return images().get("fixed_height_downsampled").get("url");
    }

    public String downsizedImage() {
        return images().get("fixed_height_still").get("url");
    }
}

