package com.BUPTJuniorTeam.filemanager.test;

import android.content.Context;

import com.BUPTJuniorTeam.filemanager.task.CopyTask;
import com.BUPTJuniorTeam.filemanager.utils.StorageLocation;

public class TestCopyTask {

    public static void testCopyTask(Context context) {

        // file INTERNAL TO INTERNAL
        // 29 success
        CopyTask copyTask1 = new CopyTask(context);
        copyTask1.execute(StorageLocation.INTERNAL + "/Podcasts/music.mp3", StorageLocation.INTERNAL + "/Music");

        // file EXTERNAL TO EXTERNAL
        // 29 fail
        CopyTask copyTask2 = new CopyTask(context);
        copyTask2.execute(StorageLocation.EXTERNAL + "/MISC/music.mp3", StorageLocation.EXTERNAL + "/MISC/THM");
        // 30 success
        CopyTask copyTask3 = new CopyTask(context);
        copyTask3.execute(StorageLocation.EXTERNAL + "/Pictures/IMG_20210613_161215.jpg", StorageLocation.EXTERNAL + "/DCIM");

        // file INTERNAL TO EXTERNAL
        // 29 fail
        CopyTask copyTask4 = new CopyTask(context);
        copyTask1.execute(StorageLocation.INTERNAL + "/Music/music.mp3", StorageLocation.EXTERNAL + "/MISC");
        // 30 success
        CopyTask copyTask5 = new CopyTask(context);
        copyTask5.execute(StorageLocation.INTERNAL + "/Pictures/IMG_20210614_105014.jpg", StorageLocation.EXTERNAL + "/DCIM");

        // file EXTERNAL TO INTERNAL
        // 29 success
        CopyTask copyTask6 = new CopyTask(context);
        copyTask6.execute(StorageLocation.EXTERNAL + "/MISC/IDX/idx00", StorageLocation.INTERNAL + "/Music");


        // directory   internal to internal
        // 29 success
        CopyTask copyTask7 = new CopyTask(context);
        copyTask7.execute(StorageLocation.INTERNAL + "/Music", StorageLocation.INTERNAL + "/Podcasts");

    }
}
