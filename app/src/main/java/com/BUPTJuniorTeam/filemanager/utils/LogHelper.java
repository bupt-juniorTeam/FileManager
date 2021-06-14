package com.BUPTJuniorTeam.filemanager.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LogHelper {
    private File file;

    public LogHelper() {}
    public LogHelper(File f) {
        this.file = f;
    }

    public void writeLog(String string) throws IOException {
        if (file == null)
            file = new File("history.log");
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        bufferedWriter.write(string + "\n");

        bufferedWriter.close();
    }

    public ArrayList<FileProperty> readLog() throws IOException {
        if (file == null)
            file = new File("history.log");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        ArrayList<String> his = new ArrayList<>();
        String string;
        while ((string = bufferedReader.readLine()) != null) {
            his.add(string);
        }

        ArrayList<FileProperty> fileProperties = new ArrayList<>();
        for (int i = his.size()-1; Math.max(his.size()-10, 0) <= i; i-- ) {
            fileProperties.add(new FileProperty(his.get(i)));
        }

        bufferedReader.close();
        return fileProperties;
    }

    public void finish() { }
}
