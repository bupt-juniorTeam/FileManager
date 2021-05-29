package com.BUPTJuniorTeam.filemanager.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.BUPTJuniorTeam.filemanager.R;
import com.BUPTJuniorTeam.filemanager.service.FileManagerService;

import java.util.ArrayList;

/**
 * 浏览界面，浏览目录下所有文件列表，允许用户对文件进行操作
 */
public class BrowserActivity extends AppCompatActivity {

    private String currentPath;
    private ArrayList<String> fileList;
    private FileManagerService fileManagerService;
    private Float progress;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            fileManagerService = ((FileManagerService.ServiceBinder)iBinder).getService();

            fileManagerService.setProgressCallBack(new FileManagerService.ICallBack<Float>() {
                @Override
                public void callback(Float data) {
                    onProgressUpdate(data);
                }
            });
            fileManagerService.setFileListCallBack(new FileManagerService.ICallBack<ArrayList<String>>() {
                @Override
                public void callback(ArrayList<String> data) {
                    onFileListUpdate(data);
                }
            });

            Intent intent = new Intent();
            fileManagerService.onStartCommand(intent, 0, 0);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
    }

    /**
     * 列出当前路径下的所有文件
     */
    private void list() {
        // TODO: 列出所有文件
    }

    /**
     * 打开目标文件，如果是文件夹则打开文件夹并重新列出子目录和文件
     * @param filename 目标文件名
     */
    private void open(String filename) {
        // TODO: 打开目标文件
    }

    /**
     * 更新当前任务进度回调函数，若进度为1，则关闭进度条显示
     * @param progress 当前进度
     */
    private void onProgressUpdate(Float progress) {
        // TODO: 更新进度
    }

    /**
     * 更新当前文件列表的回调函数，刷新文件列表显示
     * @param list 当前文件列表
     */
    private void onFileListUpdate(ArrayList<String> list) {
        // TODO: 更新文件列表
    }
}