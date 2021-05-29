package com.BUPTJuniorTeam.filemanager.accessobject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class InternalAccessObject implements IAccessObject {
    /**
     * 列出当前目录下的文件
     *
     * @param path 当前目录路径
     * @return ArrayList<String>，文件列表
     */
    @Override
    public ArrayList<String> list(String path) {
        // TODO: 获取文件列表
        return null;
    }

    /**
     * 选择合适的软件打开文件，如果是文件夹，则打开文件夹
     *
     * @param filename 需要打开的文件名
     */
    @Override
    public void open(String filename) {
        // TODO: 打开文件
    }

    /**
     * 该文件是否是一个目录
     *
     * @param filename 判断的文件名
     * @return boolean，如果是文件夹返回true
     */
    @Override
    public boolean isDirectory(String filename) {
        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param filename 判断的文件名
     * @return boolean，如果文件存在返回true
     */
    @Override
    public boolean isExisted(String filename) {
        return false;
    }

    /**
     * 获取需要复制的文件的输入流
     *
     * @param filename 需要复制的文件名
     * @return FileInputStream，该文件的输入流
     */
    @Override
    public FileInputStream copy(String filename) {
        return null;
    }

    /**
     * 获取粘贴目标文件的输出流
     *
     * @param filename 需要粘贴的目标文件名
     * @return FileOutputStream，该文件的输出流
     */
    @Override
    public FileOutputStream paste(String filename) {
        return null;
    }

    /**
     * 删除文件
     *
     * @param path 需要删除的路径
     */
    @Override
    public void delete(String path) {

    }
}
