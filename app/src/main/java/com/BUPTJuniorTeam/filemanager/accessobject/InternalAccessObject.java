package com.BUPTJuniorTeam.filemanager.accessobject;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class InternalAccessObject implements IAccessObject {
    private SpecifiedFileAccess fileAccess;

    public InternalAccessObject() {
        fileAccess = new SpecifiedFileAccess();
    }

    public File createFile(String filename){
return null;
    }

    /**
     * 列出当前目录下的文件
     *
     * @param path 当前目录路径
     * @return ArrayList<String>，文件列表
     * 路径是否存在由上层判断
     */
    @Override
    public ArrayList<String> list(String path) {
        // TODO: 获取文件列表
        File file = new File(path);
        File[] files = file.listFiles();
        ArrayList<String> s = new ArrayList<>();
        //空目录
        if (files == null) {
            return s;
        }
        //包括文件夹和文件
        for (int i = 0; i < files.length; ++i) {
            s.add(files[i].getName());
        }
        return s;
    }


    /**
     * 选择合适的软件打开文件，如果是文件夹，则打开文件夹
     *
     * @param filename 需要打开的文件名
     * 文件是否存在由上层判断
     */
    @Override
    public Intent open(String filename) {
        return fileAccess.getIntent(filename);
    }

    /**
     * 该文件是否是一个目录
     *
     * @param filename 判断的文件名
     * @return boolean，如果是文件夹返回true
     * 文件是否存在由上层判断
     */
    @Override
    public boolean isDirectory(String filename) {
        File file = new File(filename);
        return file.isDirectory();
    }

    /**
     * 判断文件或者文件夹是否存在
     *
     * @param filename 判断的文件名
     * @return boolean，如果文件存在返回true
     */
    @Override
    public boolean isExisted(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    /**
     * 获取需要复制的文件的输入流
     *
     * @param filename 需要复制的文件名
     * @return FileInputStream，该文件的输入流
     * 文件是否存在由上层判断
     * 是否获取成功由上层调用函数判断
     */
    @Override
    public FileInputStream copy(String filename) throws FileNotFoundException {
        File file = new File(filename);
        FileInputStream inputStream = new FileInputStream(file);

        return inputStream;

    }

    /**
     * 获取粘贴目标文件的输出流
     *
     * @param filename 需要粘贴的目标文件名
     * @return FileOutputStream，该文件的输出流
     * 文件是否存在由上层判断
     * 是否获取成功由上层调用函数判断
     */
    @Override
    public FileOutputStream paste(String filename) throws FileNotFoundException {
        File file = new File(filename);

        FileOutputStream outputStream = new FileOutputStream(file);

        return outputStream;
    }

    /**
     * 删除文件
     *
     * @param path 需要删除的路径
     * @return boolean，如果删除成功返回true
     * 文件是否存在由上层判断
     */
    @Override
    public boolean delete(String path) {
        File file = new File(path);
        if (file.isFile())
            return deleteSingleFile(path);
        else
            return deleteDirectory(path);

    }


    /**
     * 删除单个文件
     *
     * @param filename 需要删除的文件名
     * @return boolean，如果删除成功返回true
     */
    private boolean deleteSingleFile(String filename) {
        File file = new File(filename);
        //如果是一个文件就直接删除
        if (file.isFile()) {
            //删除成功
            if (file.delete()) {
                return true;
            }
            //删除失败
            else {
                return false;
            }
        }
        //不存在返回失败
        else {
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param path 需要删除的文件路径
     * @return boolean，如果删除成功返回true
     */
    private boolean deleteDirectory(String path) {
        //自动添加分隔符
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);
        //如果不存着或者不是个目录
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean isSuccess = true;
        //删除文件夹种的所有文件和子目录
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                //删除子文件
                if (file.isFile()) {
                    isSuccess = deleteSingleFile(file.getAbsolutePath());
                    if (!isSuccess) break;
                }
                //删除子目录
                else if (file.isDirectory()) {
                    isSuccess = deleteDirectory(file.getAbsolutePath());
                    if (!isSuccess) break;
                }
            }
            if (!isSuccess) {
                return false;
            }
        }
        //删除当前目录
        return dirFile.delete();
    }

    /**
     * 列出当前目录下的文件
     *
     * @param filename 当前文件名
     * @return FileProperty，文件属性
     * 文件是否存在由上层判断
     */
    public FileProperty getProperty(String filename) {
        File file = new File(filename);
        FileProperty fileProperty = new FileProperty();
        //name
        fileProperty.setName(file.getName());
        long time = file.lastModified();
        //date
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String string_time = formatter.format(time);
        fileProperty.setModified_time(string_time);
        //path
        fileProperty.setPath(file.getPath());
        //size
        fileProperty.setSize(file.length());
        String type=fileAccess.getFileMemeType(filename);
        fileProperty.setType(type);
        return fileProperty;

    }
}
