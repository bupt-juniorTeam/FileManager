package com.BUPTJuniorTeam.filemanager.task;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.BUPTJuniorTeam.filemanager.utils.Futils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTask extends Service {
    public final String EXTRACT_CONDITION = "ZIPPING";
    public final String EXTRACT_PROGRESS = "ZIP_PROGRESS";
    public final String EXTRACT_COMPLETED = "ZIP_COMPLETED";
    // Binder given to clients
    HashMap<Integer, Boolean> hash = new HashMap<Integer, Boolean>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void publishResult(boolean b) {
        Intent intent = new Intent("run");
        intent.putExtra("run", b);
        sendBroadcast(intent);

    }

    private void publishResults(int id, String fileName, int i, boolean b) {
        Intent intent = new Intent(EXTRACT_CONDITION);
        intent.putExtra(EXTRACT_PROGRESS, i);
        intent.putExtra("id", id);
        intent.putExtra("name", fileName);
        intent.putExtra(EXTRACT_COMPLETED, b);
        sendBroadcast(intent);
    }

    class zip {
        public zip() {}

        int count;
        long size, totalBytes;
        String fileName;

        public void execute(int id, ArrayList<File> a, String fileOut) {
            for (File f1 : a) {
                if(f1.isDirectory()) {
                    totalBytes = totalBytes + new Futils().folderSize(f1, false);
                } else {
                    totalBytes = totalBytes + f1.length();
                }
            }
            FileOutputStream out = null;
            count = a.size();
            fileName = fileOut;
            File zipDirectory = new File(fileOut);
            //publishResult(true);
            try {
                out = new FileOutputStream(zipDirectory);
                zos = new ZipOutputStream(new BufferedOutputStream(out));
            } catch (Exception e) {
            }

            try {
                zos.flush();
                zos.close();
            } catch (IOException e) {
            }
        }

        ZipOutputStream zos;
        private int isCompressed = 0;

        private void compressFile(int id, File file, String path) throws IOException {
            if (!file.isDirectory()) {
                byte[] buf = new byte[1024];
                int len;
                FileInputStream in = new FileInputStream(file);
                if (path.length() > 0)
                    zos.putNextEntry(new ZipEntry(path + "/" + file.getName()));
                else
                    zos.putNextEntry(new ZipEntry(file.getName()));
                while ((len = in.read(buf)) > 0) {
                    if (hash.get(id)) {
                        zos.write(buf, 0, len);
                        size += len;
                        //publishResult(true);
                        int p = Math.round(size * 100 /totalBytes);
                        System.out.println(id + " " + p + " " + hash.get(id));
                        //publishResults(id, fileName, p, false);
                    }
                }
                in.close();
                return;
            }
            if (file.list() == null) {
                return;
            }
            for (String fileName : file.list()) {
                File f = new File(file.getAbsolutePath() + File.separator
                        + fileName);
                compressFile(id, f, path + File.separator + file.getName());
            }
        }

        private void decompressFile(int id, File file, String path) throws IOException {
        }
    }
}
