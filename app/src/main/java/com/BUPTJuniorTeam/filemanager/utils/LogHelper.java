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

    public void writeLog(String string) {
        if (file == null)
            file = new File("history.log");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            bufferedWriter.write(string + "\n");

            bufferedWriter.close();
        } catch (IOException e) { }
    }

    public ArrayList<FileProperty> readLog(){
        ArrayList<FileProperty> fileProperties = new ArrayList<>();

        if (file == null)
            file = new File("history.log");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String string;
            ArrayList<String> his = new ArrayList<>();
            while ((string = bufferedReader.readLine()) != null) {
                his.add(string);
            }

            for (int i = his.size() - 1; Math.max(his.size() - 10, 0) <= i; i--) {
                fileProperties.add(new FileProperty(his.get(i)));
            }

            bufferedReader.close();
        } catch (IOException e) { }

        return fileProperties;
    }

    public void finish() { }
}
