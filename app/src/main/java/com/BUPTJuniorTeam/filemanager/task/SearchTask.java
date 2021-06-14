package com.BUPTJuniorTeam.filemanager.task;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.BUPTJuniorTeam.filemanager.activity.MainActivity;
import com.BUPTJuniorTeam.filemanager.utils.FileProperty;
import com.BUPTJuniorTeam.filemanager.utils.RootHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

public class SearchTask extends AsyncTask<String, String, ArrayList<FileProperty>> {
    ProgressDialog a;
    boolean run = true;
    MainActivity m;
    // Main tab;

    public SearchTask(MainActivity m) { // Main tab
        this.m = m;
    }

    @Override
    protected ArrayList<FileProperty> doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        a = new ProgressDialog(m);
        a.setIndeterminate(true);
        a.setTitle("Searching");
        a.setButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                run = false;
                a.dismiss();
            }
        });
        a.setCancelable(false);
        a.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (a != null) {
            a.setMessage("Searching " + values[0]);
        }
    }

    @Override
    protected void onPostExecute(ArrayList<FileProperty> fileProperties) {
        if (run) {
        }
        a.dismiss();
    }

    protected ArrayList<String []> doInBackground(Bundle[] p1) {
        Bundle b = p1[0];
        String FILENAME = b.getString("FILENAME");
        String FILEPATH = b.getString("FILEPATH");

        return getSearchResult(new File(FILEPATH), FILENAME);
    }

    ArrayList<String[]> lis = new ArrayList<String[]>();
    public ArrayList<String []> getSearchResult(File f, String text) {
        lis.clear();

        search(f, text);

        return lis;
    }

    public void search(File file, String text) {
        if (file.isDirectory()) {

            ArrayList<String[]> f = new ArrayList<>();// = RootHelper.getFilesList(file.getPath()); //,tab.rootMode,tab.showHidden);
            // do you have permission to read this directory?

            for (String[] x : f) {
                File temp=new File(x[0]);
                publishProgress(temp.getPath());
                if (run) {
                    if (temp.isDirectory()) {
                        if (temp.getName().toLowerCase()
                                .contains(text.toLowerCase())) {
                            lis.add(x);
                        }
                        //System.out
                        //.println(file.getAbsoluteFile() );

                        search(temp, text);

                    } else {
                        if (temp.getName().toLowerCase()
                                .contains(text.toLowerCase())) {
                            lis.add(x);
                        }
                    }//	publishProgress(temp.getPath());
                }
            }
        } else {
            System.out
                    .println(file.getAbsoluteFile() + "Permission Denied");
        }
    }
}
