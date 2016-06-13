package com.instinctools.phone.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DisplayMetricsUtil {

    public static boolean isScreenW(int widthDp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        return screenWidth >= widthDp;
    }

}