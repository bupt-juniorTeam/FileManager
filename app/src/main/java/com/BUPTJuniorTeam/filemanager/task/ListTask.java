package com.BUPTJuniorTeam.filemanager.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ListView;
import com.BUPTJuniorTeam.filemanager.R;
import com.BUPTJuniorTeam.filemanager.activity.FileListAdapter;
import com.BUPTJuniorTeam.filemanager.utils.FileProperty;
import com.BUPTJuniorTeam.filemanager.utils.SpecifiedFileAccess;
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
    ArrayList<FileProperty> fileProperties = new ArrayList<>();
    String path = strings[0];
    int first = path.indexOf("/");
    String type = path.substring(0, first);

    if ("Internal Storage".equals(type)) {
      path = Environment.getExternalStorageDirectory().getAbsolutePath() + path.substring(first);
      File file = new File(path);
      File[] files = file.listFiles();
      if (files != null) {
        for (File f : files) {
          FileProperty property = new FileProperty();
          property.setName(f.getName());
          long time = f.lastModified();
          @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String string_time = formatter.format(time);
          property.setModified_time(string_time);
          property.setPath(file.getPath());
          property.setSize(file.length());
          String file_type = fileAccess.getFileMemeType(f.getName());
          property.setType(file_type);
          fileProperties.add(property);
        }

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
    }
    else if ("External Storage".equals(type)) {

    }
    else if ("History".equals(type)) {

    }

    return fileProperties;
  }

  @Override
  protected void onPostExecute(ArrayList<FileProperty> fileProperties) {
    super.onPostExecute(fileProperties);

    FileListAdapter adapter = new FileListAdapter(context, R.layout.file_list, fileProperties);
    list.setAdapter(adapter);
  }
}
