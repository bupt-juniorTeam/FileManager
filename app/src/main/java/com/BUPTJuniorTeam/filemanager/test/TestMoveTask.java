package com.BUPTJuniorTeam.filemanager.test;

import android.content.Context;

import com.BUPTJuniorTeam.filemanager.task.MoveTask;
import com.BUPTJuniorTeam.filemanager.utils.StorageLocation;

public class TestMoveTask {

    public static void testMoveTask(Context context) {

        // file INTERNAL success
        new MoveTask(context).execute(StorageLocation.INTERNAL + "/Music/music.mp3", StorageLocation.INTERNAL + "/Podcasts/Music");

        // dir INTERNAL success
        new MoveTask(context).execute(StorageLocation.INTERNAL + "/Podcasts/Music", StorageLocation.INTERNAL + "/Music");
    }
}
