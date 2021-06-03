package com.BUPTJuniorTeam.filemanager.accessobject;

import android.content.Intent;
import com.BUPTJuniorTeam.filemanager.web.FTPClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class FTPAccessObject implements IAccessObject {

  private FTPClient ftpClient = null;

  public boolean connect(String url, String username, String password) {
    ftpClient = new FTPClient(url);
    return ftpClient.login(username, password);
  }

  @Override
  public File createFile(String filename) {
    return null;
  }

  @Override
  public FileProperty getProperty(String filename) {
    if (ftpClient == null) {
      return null;
    }

    int index = filename.lastIndexOf('/');
    return ftpClient.getProperty(filename.substring(0, index), filename.substring(index + 1));
  }

  @Override
  public ArrayList<String> list(String path) {
    if (ftpClient == null) {
      return new ArrayList<>();
    }

    return ftpClient.list(path);
  }

  @Override
  public Intent open(String filename) {
    return null;
  }

  @Override
  public boolean isDirectory(String filename) {
    int index = filename.lastIndexOf('/');
    return ftpClient != null && ftpClient.isDirectory(filename.substring(0, index), filename.substring(index + 1));
  }

  @Override
  public boolean isExisted(String filename) {
    int index = filename.lastIndexOf('/');
    return ftpClient != null && ftpClient.isExisted(filename.substring(0, index), filename.substring(index + 1));
  }

  @Override
  public InputStream copy(String filename) throws FileNotFoundException {
    if (ftpClient == null) {
      return null;
    }

    return ftpClient.download(filename);
  }

  @Override
  public OutputStream paste(String filename) throws FileNotFoundException {
    if (ftpClient == null) {
      return null;
    }

    return ftpClient.upload(filename);
  }

  @Override
  public boolean delete(String path) {
    int index = path.lastIndexOf('/');
    return ftpClient != null && ftpClient.delete(path.substring(0, index), path.substring(index + 1));
  }
}
