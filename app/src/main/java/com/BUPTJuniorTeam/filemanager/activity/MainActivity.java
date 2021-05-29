package com.BUPTJuniorTeam.filemanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.BUPTJuniorTeam.filemanager.R;
import com.BUPTJuniorTeam.filemanager.accessobject.AccessType;

/**
 * 启动界面，允许用户选择访问的方式（内部，外部，FTP，历史记录）
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 打开特定类型的根目录
     * @param root 根目录地址
     */
    private void open(String root) {
        // TODO: 打开内部存储根目录
    }
}