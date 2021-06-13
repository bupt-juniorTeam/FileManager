package com.BUPTJuniorTeam.filemanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileOperation {

    public static boolean copyDir(File sourceDir, File targetDir) {
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

    public static boolean copyFile(File source, File target) {
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

    public static boolean copyFiletoDir(File sourceFile, File targetDir) {
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        File targetFile = new File(targetDir.getAbsolutePath() + "/" + sourceFile.getName());
        return copyFile(sourceFile, targetFile);
    }

    public static boolean deleteDir(File dir) {
        File[] files = dir.listFiles();
        for (File file: files) {
            if (file.isDirectory()) {
                if (!deleteDir(new File(dir.getAbsolutePath() + "/" + file.getName())))
                    return false;
            } else {
                if (!deleteFile(file))
                    return false;
            }
        }
        return dir.delete();
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

}
