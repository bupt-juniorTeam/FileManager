package com.BUPTJuniorTeam.filemanager.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SDCardUtils {
    public static List<String> getAllSDCards(Context context) {
        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(context, null);

        List<String> sdCards = new ArrayList<>();
        for (File file : externalStorageVolumes) {
            int index = file.getAbsolutePath().indexOf("/Android/");
            sdCards.add(file.getAbsolutePath().substring(0, index));
        }
        return sdCards;
    }

    public static String getExternalSDCards(Context context) {
        List<String> sdCards = getAllSDCards(context);
        if (sdCards.size() >= 2)
            return sdCards.get(1);
        else
            return null;
    }
}
