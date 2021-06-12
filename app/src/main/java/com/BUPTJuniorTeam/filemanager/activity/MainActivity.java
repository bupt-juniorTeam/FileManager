package com.BUPTJuniorTeam.filemanager.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.BUPTJuniorTeam.filemanager.R;
import com.BUPTJuniorTeam.filemanager.task.ListTask;
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
    private String currentPath = "Internal Storage";

    private ListTask listTask = null;

    private void resetCurrentPath(String path) {
        if ("Internal Storage".equals(path)) {
            currentPath = "Internal Storage/";
        }
        else if ("External Storage".equals(path)) {
            currentPath = "External Storage/";
        }
        else if ("History".equals(path)) {
            currentPath = "History/";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadViews();
        loadLeftListView();
        resetCurrentPath("Internal Storage");
    }

}