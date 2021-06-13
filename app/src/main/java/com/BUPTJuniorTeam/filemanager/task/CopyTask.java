package com.BUPTJuniorTeam.filemanager.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.BUPTJuniorTeam.filemanager.utils.FileOperation;
import com.BUPTJuniorTeam.filemanager.utils.FileProperty;
import com.BUPTJuniorTeam.filemanager.utils.SDCardUtils;
import com.BUPTJuniorTeam.filemanager.utils.StorageLocation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CopyTask extends AsyncTask<String, String, Boolean> {

    private Context context;

    public CopyTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String source = strings[0];
        String target = strings[1];

        String sourceType = source.substring(0, source.indexOf("/"));
        String targetType = target.substring(0, target.indexOf("/"));

        if (sourceType.equals(StorageLocation.INTERNAL)) {
            source = Environment.getExternalStorageDirectory().getAbsolutePath() + source.substring(source.indexOf("/"));
        } else if (sourceType.equals(StorageLocation.EXTERNAL)) {
            String sdCardRoot = SDCardUtils.getExternalSDCards(context);
            source = sdCardRoot + source.substring(source.indexOf("/"));
        }

        if (targetType.equals(StorageLocation.INTERNAL)) {
            target = Environment.getExternalStorageDirectory().getAbsolutePath() + target.substring(target.indexOf("/"));
        } else if (sourceType.equals(StorageLocation.EXTERNAL)) {
            String sdCardRoot = SDCardUtils.getExternalSDCards(context);
            target = sdCardRoot + target.substring(target.indexOf("/"));
        }

        File sourceFile = new File(source);
        File targetDir = new File(target);

        if (sourceFile.isDirectory()) {
            return FileOperation.copyDir(sourceFile, targetDir);
        } else if (sourceFile.isFile()) {
            return FileOperation.copyFiletoDir(sourceFile, targetDir);
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        return;
    }
}
