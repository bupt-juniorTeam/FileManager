package com.BUPTJuniorTeam.filemanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.BUPTJuniorTeam.filemanager.accessobject.AccessType;

import java.util.ArrayList;

public class FileManagerService extends Service {
    /**
     * 回调接口，用于Service向Activity发送数据
     * @param <T> 发送数据的类型
     */
    public interface ICallBack<T> {
        /**
         * 向Activity发送数据
         * @param data 需要发送的数据
         */
        public void callback(T data);
    }

    public class ServiceBinder extends Binder {
        public FileManagerService getService() {
            return FileManagerService.this;
        }
    }

    private final IBinder serviceBinder = new ServiceBinder();

    private ICallBack<Float> progressCallBack;
    private ICallBack<ArrayList<String>> fileListCallBack;
    private Thread taskThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (taskThread.isAlive()) {
            try {
                taskThread.join(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置进度回调函数
     * @param callBack 回调函数
     */
    public void setProgressCallBack(ICallBack<Float> callBack) {
        progressCallBack = callBack;
    }

    /**
     * 设置文件列表回调函数
     * @param callBack 回调函数
     */
    public void setFileListCallBack(ICallBack<ArrayList<String>> callBack) {
        fileListCallBack = callBack;
    }

    /**
     * 列出目录下的文件列表
     * @param type 目录类型
     * @param path 目录路径
     */
    public void list(AccessType type, String path) {
        // TODO: 创建另一条线程，加载文件列表并通过回调函数返回值
    }

    /**
     * 打开文件
     * @param type 文件所属目录的类型
     * @param filename 文件名
     */
    public void open(AccessType type, String filename) {
        // TODO: 调用系统API打开文件，如果是路径则重新加载路径
    }
}
