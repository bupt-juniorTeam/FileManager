package com.BUPTJuniorTeam.filemanager.test;

import android.content.Context;

import com.BUPTJuniorTeam.filemanager.task.DeleteTask;
import com.BUPTJuniorTeam.filemanager.utils.StorageLocation;

public class TestDeleteTask {

    public static void testDeleteTask(Context context) {
        // file INTERNAL
        // 29 success
        new DeleteTask(context).execute(StorageLocation.INTERNAL + "/Music/music.mp3");

        // file EXTERNAL
        // 29 fail
        // 30 success
        new DeleteTask(context).execute(StorageLocation.EXTERNAL + "/DCIM/IMG_20210613_161215.jpg");

        // dir INTERNAL 29 success
        new DeleteTask(context).execute(StorageLocation.INTERNAL + "/Music/Music");

        // dir EXTERNAL 30 success
        new DeleteTask(context).execute(StorageLocation.EXTERNAL + "/Music/Music");
    }
}
