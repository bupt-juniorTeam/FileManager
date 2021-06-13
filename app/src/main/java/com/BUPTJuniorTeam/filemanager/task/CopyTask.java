package com.BUPTJuniorTeam.filemanager.task;

import android.content.Context;
import android.os.AsyncTask;

import com.BUPTJuniorTeam.filemanager.utils.FileProperty;

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
        


        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        return;
    }
}
