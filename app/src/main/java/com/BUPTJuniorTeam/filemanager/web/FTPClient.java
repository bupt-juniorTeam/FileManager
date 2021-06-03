package com.BUPTJuniorTeam.filemanager.web;

import android.annotation.SuppressLint;
import com.BUPTJuniorTeam.filemanager.accessobject.FileProperty;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPClient {
    private String url;
    private org.apache.commons.net.ftp.FTPClient ftpClient = null;

    public FTPClient(String url) {
        this.url = url;
        ftpClient = new org.apache.commons.net.ftp.FTPClient();
        ftpClient.setControlEncoding("UTF-8");
    }

    public boolean login(String username, String password) {
        try {
            ftpClient.connect(url);
            int reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception("No Connection");
            }

            ftpClient.login(username, password);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception("Wrong Username/Password");
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
        }
        catch (Exception e) {
            e.printStackTrace();
            ftpClient = null;
            return false;
        }

        return true;
    }

    public ArrayList<String> list(String path) {
        ArrayList<String> res = new ArrayList<>();
        if (ftpClient != null) {
            try {
                FTPFile[] files = ftpClient.listFiles(path);
                for (FTPFile file : files) {
                    res.add(file.getName());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public OutputStream upload(String path) {
        if (ftpClient == null) {
            return null;
        }

        OutputStream stream = null;
        try {
            stream = ftpClient.storeFileStream(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return stream;
    }

    public InputStream download(String path) {
        if (ftpClient == null) {
            return null;
        }

        InputStream stream = null;
        try {
            stream = ftpClient.retrieveFileStream(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return stream;
    }

    public boolean delete(String path, String filename) {
        boolean flag = true;
        if (ftpClient != null) {
            try {
                ftpClient.changeWorkingDirectory(path);
                FTPFile[] files = ftpClient.listFiles();
                for (FTPFile file : files) {
                    if (file.getName().equals(filename)) {
                        if (file.isFile()) {
                            flag = ftpClient.deleteFile(filename);
                        }
                        else {
                            flag = ftpClient.removeDirectory(filename);
                        }
                        break;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }

    public boolean isDirectory(String path, String filename) {
        boolean flag = true;
        if (ftpClient != null) {
            try {
                ftpClient.changeWorkingDirectory(path);
                FTPFile[] files = ftpClient.listFiles();
                for (FTPFile file : files) {
                    if (file.getName().equals(filename)) {
                        flag = file.isDirectory();
                        break;
                    }

                }
            }
            catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }

    public boolean isExisted(String path, String filename) {
        boolean flag = true;
        if (ftpClient != null) {
            try {
                ftpClient.changeWorkingDirectory(path);
                FTPFile[] files = ftpClient.listFiles();
                for (FTPFile file : files) {
                    if (file.getName().equals(filename)) {
                        flag = true;
                        break;
                    }

                }
            }
            catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }

    public FileProperty getProperty(String path, String filename) {
        FileProperty property = null;
        if (ftpClient != null) {
            try {
                ftpClient.changeWorkingDirectory(path);
                FTPFile[] files = ftpClient.listFiles();
                for (FTPFile file : files) {
                    if (file.getName().equals(filename)) {
                        property = new FileProperty();
                        property.setName(file.getName());
                        property.setPath(path);
                        property.setSize(file.getSize());

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        property.setModified_time(formatter.format(file.getTimestamp()));

                        if (file.isFile()) {
                            property.setType("File");
                        }
                        else if (file.isDirectory()) {
                            property.setType("Directory");
                        }
                        else {
                            property.setType("Unknown");
                        }

                        break;
                    }

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
