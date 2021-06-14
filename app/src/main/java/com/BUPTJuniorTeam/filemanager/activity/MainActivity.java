package com.BUPTJuniorTeam.filemanager.activity;

import android.Manifest.permission;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.BUPTJuniorTeam.filemanager.R;
import com.BUPTJuniorTeam.filemanager.task.CopyTask;
import com.BUPTJuniorTeam.filemanager.task.DeleteTask;
import com.BUPTJuniorTeam.filemanager.task.ListTask;
import com.BUPTJuniorTeam.filemanager.task.MoveTask;
import java.io.File;
import java.util.ArrayList;

/**
 * 浏览界面
 */
public class MainActivity extends AppCompatActivity {
    private ListView listView = null;
    private ListView listView2 = null;
    private ImageButton menuButton = null;
    private DrawerLayout drawerLayout = null;
    private LinearLayout leftLayout = null;
    private TextView pathLabel = null;
    private ImageButton doneButton = null;
    private ProgressDialog dialog = null;

    private String currentPath = "Internal Storage";
    private String fromPath = "";

    private enum TransferType {
        TRANSFER_TYPE_MOVE,
        TRANSFER_TYPE_COPY
    };

    private TransferType transferType;

    private ListTask listTask = null;
    private CopyTask copyTask = null;
    private DeleteTask deleteTask = null;
    private MoveTask moveTask = null;

    public void resetCurrentPath(String path) {
        if ("Internal Storage".equals(path)) {
            currentPath = "Internal Storage/";
        }
        else if ("External Storage".equals(path)) {
            currentPath = "External Storage/";
        }
        else if ("History".equals(path)) {
            currentPath = "History/";
            FileListAdapter.setOpenOnly(true);
        }
        else if ("..".equals(path)) {
            File file = new File(currentPath);
            file = file.getParentFile();
            path = file.getName() + "/";

            String temp = currentPath.substring(0, currentPath.length() - 1);
            int pos = temp.lastIndexOf('/');
            currentPath = currentPath.substring(0, pos + 1);
        }
        else {
            currentPath += path;
        }

        pathLabel.setText(path);
        loadMainListView();
    }

    private void loadViews() {
        listView = (ListView) findViewById(R.id.left_list);
        menuButton = (ImageButton) findViewById(R.id.menu_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftLayout = (LinearLayout) findViewById(R.id.left);
        pathLabel = (TextView) findViewById(R.id.path_label);
        listView2 = (ListView) findViewById(R.id.main_list);
        doneButton = (ImageButton) findViewById(R.id.menu_done);
    }

    private void loadLeftListView() {
        final ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.left_list_view_internal));
        list.add(getString(R.string.left_list_view_external));
        list.add(getString(R.string.left_list_view_history));
        list.add(getString(R.string.left_list_view_preference));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this,
            android.R.layout.simple_expandable_list_item_1,
            list
        );

        listView.setAdapter(adapter);

        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(leftLayout);
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    resetCurrentPath("Internal Storage");
                }
                else if (i == 1) {
                    resetCurrentPath("External Storage");
                }
                else if (i == 2) {
                    resetCurrentPath("History");
                }
                else if (i == 3) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, PreferenceActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(leftLayout);
            }
        });
    }

    private void loadMainListView() {
        listTask = new ListTask(listView2, this);
        listTask.execute(currentPath);
    }

    private void getPermission() {
//        ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE, permission.MEDIA_CONTENT_CONTROL, permission.ACCESS_MEDIA_LOCATION}, 100);
        if (VERSION.SDK_INT >= VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                  Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                  startActivity(intent);
            }
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE, permission.MEDIA_CONTENT_CONTROL, permission.ACCESS_MEDIA_LOCATION}, 100);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        loadViews();
        loadLeftListView();
        resetCurrentPath("Internal Storage");

        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("正在执行...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                if (transferType == TransferType.TRANSFER_TYPE_COPY) {
                    copyTask = new CopyTask(MainActivity.this);
                    copyTask.execute(fromPath, currentPath);
                }
                else if (transferType == TransferType.TRANSFER_TYPE_MOVE) {
                    moveTask = new MoveTask(MainActivity.this);
                    moveTask.execute(fromPath, currentPath);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if ("Internal Storage/".equals(currentPath) ||
            "External Storage/".equals(currentPath) ||
            "History".equals(currentPath)) {
            super.onBackPressed();
        }
        else {
            resetCurrentPath("..");
        }
    }

    public void copyFile(String from) {
        FileListAdapter.setOpenOnly(true);
        doneButton.setVisibility(View.VISIBLE);
        transferType = TransferType.TRANSFER_TYPE_COPY;
        fromPath = from;
    }

    public void moveFile(String from) {
        FileListAdapter.setOpenOnly(true);
        doneButton.setVisibility(View.VISIBLE);
        transferType = TransferType.TRANSFER_TYPE_MOVE;
        fromPath = from;
    }

    public void removeFile(final String filename) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_about);
        builder.setTitle("确定删除？");
        builder.setMessage("确定删除文件" + filename + "吗？");

        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteTask = new DeleteTask(MainActivity.this);
                deleteTask.execute(filename);

                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("正在执行...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        builder.show();
    }

    public void finishTask() {
        FileListAdapter.setOpenOnly(false);
        doneButton.setVisibility(View.INVISIBLE);
        dialog.hide();
        loadMainListView();
    }
}