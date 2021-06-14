package com.BUPTJuniorTeam.filemanager.utils;

import java.io.File;

public class Futils {
    public static long folderSize(File directory, boolean rootMode) {
        long length = 0;
        for (File file:directory.listFiles()) {

            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file,rootMode);
        }
        return length;
    }
}
