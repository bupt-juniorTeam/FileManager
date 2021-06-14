package com.BUPTJuniorTeam.filemanager.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.BUPTJuniorTeam.filemanager.activity.MainActivity;
import com.BUPTJuniorTeam.filemanager.utils.FileOperation;
import com.BUPTJuniorTeam.filemanager.utils.SDCardUtils;
import com.BUPTJuniorTeam.filemanager.utils.StorageLocation;

import java.io.File;

public class DeleteTask extends AsyncTask<String, String, Boolean> {

    private Context context;

    public DeleteTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String source = strings[0];
        String sourceType = source.substring(0, source.indexOf("/"));

        if (sourceType.equals(StorageLocation.INTERNAL)) {
            source = Environment.getExternalStorageDirectory().getAbsolutePath() + source.substring(source.indexOf("/"));
        } else if (sourceType.equals(StorageLocation.EXTERNAL)) {
            String sdCardRoot = SDCardUtils.getExternalSDCards(context);
            source = sdCardRoot + source.substring(source.indexOf("/"));
        }

        File sourceFile = new File(source);
        if (sourceFile.isDirectory()) {
            return FileOperation.deleteDir(sourceFile);
        } else if (sourceFile.isFile()){
            return FileOperation.deleteFile(sourceFile);
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        ((MainActivity)context).finishTask();
    }
}
