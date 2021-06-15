package com.BUPTJuniorTeam.filemanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;

public class HistoryManager {
  private static final int MAX_ITEMS_SIZE = 20;
  private static HistoryManager instance = null;
  private ArrayList<FileProperty> history;
  private Context context = null;
  public static HistoryManager getInstance(Context context) {
    if (instance == null) {
      instance = new HistoryManager(context);
    }

    return instance;
  }

  private FileProperty readProperty(SharedPreferences preferences, int index) {
    FileProperty property = new FileProperty();
    property.setRelativePath(preferences.getString("history_relative_" + String.valueOf(index), ""));
    property.setName(preferences.getString("history_name_" + String.valueOf(index), ""));
    property.setPath(preferences.getString("history_path_" + String.valueOf(index), ""));
    property.setType(preferences.getString("history_type_" + String.valueOf(index), ""));
    property.setModified_time(preferences.getString("history_time_" + String.valueOf(index), ""));
    property.setSize(preferences.getLong("history_size_" + String.valueOf(index), 0));
    return property;
  }

  private void writeProperty(Editor editor, FileProperty property, int index) {
    editor.putString("history_relative_" + String.valueOf(index), property.getRelativePath());
    editor.putString("history_name_" + String.valueOf(index), property.getName());
    editor.putString("history_path_" + String.valueOf(index), property.getPath());
    editor.putString("history_type_" + String.valueOf(index), property.getType());
    editor.putString("history_time_" + String.valueOf(index), property.getModified_time());
    editor.putLong("history_size_" + String.valueOf(index), property.getSize());
  }

  private HistoryManager(Context context) {
    this.context = context;
    history = new ArrayList<>();

    SharedPreferences preferences = this.context.getSharedPreferences("app", Context.MODE_PRIVATE);
    int size = preferences.getInt("history_size", 0);
    for (int i = 0; i < size; i++) {
      history.add(readProperty(preferences, i));
    }
  }

  private void flush() {
    Editor editor = this.context.getSharedPreferences("app", Context.MODE_PRIVATE).edit();
    editor.putInt("history_size", history.size());
    for (int i = 0; i < history.size(); ++i) {
      writeProperty(editor, history.get(i), i);
    }
    editor.apply();
  }

  public void addHistoryItem(FileProperty property) {
    if (history.size() == MAX_ITEMS_SIZE) {
      history.remove(0);
    }
    else {
      for (int i = 0; i < history.size(); i++) {
        String path1 = history.get(i).getRelativePath() + history.get(i).getName();
        String path2 = property.getRelativePath() + property.getName();
        if (path1.equals(path2)) {
          history.remove(i);
          break;
        }
      }
    }

    history.add(property);
    flush();
  }

  public ArrayList<FileProperty> getHistoryList() {
    ArrayList<FileProperty> res = new ArrayList<>();
    for (int i = history.size() - 1; i > -1; i--) {
      res.add(history.get(i));
    }
    return res;
  }
}
