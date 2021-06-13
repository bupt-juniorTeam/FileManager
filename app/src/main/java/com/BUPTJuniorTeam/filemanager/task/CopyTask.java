package com.BUPTJuniorTeam.filemanager.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

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
            return copyDir(sourceFile, targetDir);
        } else if (sourceFile.isFile()) {
            return copyFiletoDir(sourceFile, targetDir);
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        return;
    }

    private boolean copyDir(File sourceDir, File targetDir) {
        if (!sourceDir.exists())
            return false;

        File[] fileList = sourceDir.listFiles();
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        for (File file : fileList) {
            if (file.isDirectory()) {
                if (!copyDir(file, new File(targetDir.getAbsolutePath() + "/" + file.getName())))
                    return false;
            } else {
                if (!copyFile(file, new File(targetDir.getAbsolutePath() + "/" + file.getName())))
                    return false;
            }
        }
        return true;
    }

    private boolean copyFile(File source, File target) {
        try {
            InputStream streamFrom = new FileInputStream(source);
            OutputStream streamTo = new FileOutputStream(target);
            byte buffer[]=new byte[1024];
            int len;
            while ((len= streamFrom.read(buffer)) > 0){
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean copyFiletoDir(File sourceFile, File targetDir) {
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        File targetFile = new File(targetDir.getAbsolutePath() + "/" + sourceFile.getName());
        return copyFile(sourceFile, targetFile);
    }
}
