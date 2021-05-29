package com.BUPTJuniorTeam.filemanager.accessobject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * 统一文件操作接口
 */
public interface IAccessObject {
    /**
     * 列出当前目录下的文件
     * @param path 当前目录路径
     * @return ArrayList<String>，文件列表
     */
    public ArrayList<String> list(String path);

    /**
     * 选择合适的软件打开文件，如果是文件夹，则打开文件夹
     * @param filename 需要打开的文件名
     */
    public void open(String filename);

    /**
     * 该文件是否是一个目录
     * @param filename 判断的文件名
     * @return boolean，如果是文件夹返回true
     */
    public boolean isDirectory(String filename);

    /**
     * 判断文件是否存在
     * @param filename 判断的文件名
     * @return boolean，如果文件存在返回true
     */
    public boolean isExisted(String filename);

    /**
     * 获取需要复制的文件的输入流
     * @param filename 需要复制的文件名
     * @return FileInputStream，该文件的输入流
     */
    public FileInputStream copy(String filename);

    /**
     * 获取粘贴目标文件的输出流
     * @param filename 需要粘贴的目标文件名
     * @return FileOutputStream，该文件的输出流
     */
    public FileOutputStream paste(String filename);

    /**
     * 删除文件
     * @param path 需要删除的路径
     */
    public void delete(String path);
}