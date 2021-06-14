package com.BUPTJuniorTeam.filemanager.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import com.BUPTJuniorTeam.filemanager.R;
import com.BUPTJuniorTeam.filemanager.activity.FileListAdapter;
import com.BUPTJuniorTeam.filemanager.utils.FileProperty;
import com.BUPTJuniorTeam.filemanager.utils.SDCardUtils;
import com.BUPTJuniorTeam.filemanager.utils.SpecifiedFileAccess;
import com.BUPTJuniorTeam.filemanager.utils.StorageLocation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListTask extends AsyncTask<String, String, ArrayList<FileProperty>> {

  private ListView list = null;
  private Context context = null;
  private SpecifiedFileAccess fileAccess = new SpecifiedFileAccess();

  public ListTask(ListView list, Context context) {
    super();
    this.list = list;
    this.context = context;
  }

  @Override
  protected ArrayList<FileProperty> doInBackground(String... strings) {
    String type = strings[0].substring(0, strings[0].indexOf("/"));

    if (StorageLocation.INTERNAL.equals(type)) {
        return getInternalList(strings[0]);
    }
    else if (StorageLocation.EXTERNAL.equals(type)) {
        return getExternalList(strings[0]);
    }
    else if (StorageLocation.HISTORY.equals(type)) {

    }
    return new ArrayList<>();
  }

  @Override
  protected void onPostExecute(ArrayList<FileProperty> fileProperties) {
    super.onPostExecute(fileProperties);

    FileListAdapter adapter = new FileListAdapter(context, R.layout.file_list, fileProperties);
    list.setAdapter(adapter);
  }

  private ArrayList<FileProperty> getInternalList(String str) {
    ArrayList<FileProperty> fileProperties = new ArrayList<>();

    String path = str;
    int first = path.indexOf("/");
    path = Environment.getExternalStorageDirectory().getAbsolutePath() + path.substring(first);
    File file = new File(path);
    File[] files = file.listFiles();
    if (!(StorageLocation.INTERNAL + "/").equals(str)) {
      FileProperty property = new FileProperty();
      property.setName("..");
      long time = file.getParentFile().lastModified();
      @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String string_time = formatter.format(time);
      property.setModified_time(string_time);
      property.setPath(file.getParent());
      property.setSize(file.length());
      String file_type = fileAccess.getFileMemeType(file.getParent());
      property.setType(file_type);
      fileProperties.add(property);
    }

    if (files != null) {
      for (File f : files) {
        FileProperty property = new FileProperty();
        property = new FileProperty();
        property.setName(f.getName());
        long time = file.getParentFile().lastModified();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String string_time = formatter.format(time);
        property.setModified_time(string_time);
        property.setPath(file.getPath());
        property.setSize(file.length());
        String file_type = fileAccess.getFileMemeType(f.getName());
        property.setType(file_type);
        fileProperties.add(property);
      }
    }

    return fileProperties;
  }

  private ArrayList<FileProperty> getExternalList(String str) {
    ArrayList<FileProperty> fileProperties = new ArrayList<>();

    String path = str;
    int first = path.indexOf("/");
    String sdCardRoot = SDCardUtils.getExternalSDCards(context);
    // 无SDCard
    if (sdCardRoot == null)
        return fileProperties;


    path = sdCardRoot + path.substring(first);
    Log.d("TAG", path);
    File file = new File(path);
    File[] files = file.listFiles();
    if (!(StorageLocation.EXTERNAL + "/").equals(str)) {
      FileProperty property = new FileProperty();
      property.setName("..");
      long time = file.getParentFile().lastModified();
      @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String string_time = formatter.format(time);
      property.setModified_time(string_time);
      property.setPath(file.getParent());
      property.setSize(file.length());
      String file_type = fileAccess.getFileMemeType(file.getParent());
      property.setType(file_type);
      fileProperties.add(property);
    }

    if (files != null) {
      for (File f : files) {
        FileProperty property = new FileProperty();
        property = new FileProperty();
        property.setName(f.getName());
        long time = file.getParentFile().lastModified();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String string_time = formatter.format(time);
        property.setModified_time(string_time);
        property.setPath(file.getPath());
        property.setSize(file.length());
        String file_type = fileAccess.getFileMemeType(f.getName());
        property.setType(file_type);
        fileProperties.add(property);
      }
    }

    return fileProperties;
  }
}
