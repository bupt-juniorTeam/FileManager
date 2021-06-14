package com.BUPTJuniorTeam.filemanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import com.BUPTJuniorTeam.filemanager.R;
import com.cxyzy.colorpickerbar.ColorPickerBar;



public class PreferenceActivity extends AppCompatActivity {

  private ColorPickerBar ColorPicker1 = null;
  private ColorPickerBar ColorPicker2 = null;
  private ImageView img1 = null;
  private ImageView img2 = null;
  private ImageView img3 = null;
  private ToggleButton darkModeToggle = null;
  private Button button = null;

  private String[] arrayForColor={"#D71345", "#BED742", "#4E72B8", "#9B95C9", "#45B97C"};


  public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();
    SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);

    // Check which radio button was clicked
    switch(view.getId()) {
      case R.id.radio_B://点击字体大时
        if (checked)
          // Pirates are the best
          preferences.edit().putInt("fontSize", 18).apply();;
          break;
      case R.id.radio_M://点击字体中时
        if (checked)
          // Ninjas rule
          preferences.edit().putInt("fontSize", 16).apply();;
          break;
      case R.id.radio_S://点击
        if (checked)
          // Pirates are the best
          preferences.edit().putInt("fontSize", 12).apply();;
          break;
    }
  }
  private void loadViews() {
    ColorPicker1 = (ColorPickerBar) findViewById(R.id.colorPickerBar1);
    ColorPicker2 = (ColorPickerBar) findViewById(R.id.colorPickerBar2);
    img1=(ImageView)findViewById(R.id.img1);
    img2=(ImageView)findViewById(R.id.img2);
    img3=(ImageView)findViewById(R.id.img3);
    darkModeToggle=(ToggleButton)findViewById(R.id.darkMode);
    button = (Button) findViewById(R.id.pref_done);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preference);
    SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
//    SharedPreferences prefs = getActivity().getSharedPreferences(
//            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    loadViews();
    ColorPicker1.init(arrayForColor);
    ColorPicker1.selectItem(0);
    ColorPicker2.init(arrayForColor);
    ColorPicker2.selectItem(0);

    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(PreferenceActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });

    //进行背景颜色的设置，背景key为backgroundColor,默认颜色为#FFFFFF
    String backgroundColor=preferences.getString("backgroundColor","#FFFFFF");
//    ColorPicker1.selectItem(Arrays.binarySearch(arrayForColor,backgroundColor));
    ColorPicker1.setColorPickerClickListener((ColorPickerBar.ColorPickerClickListener)(new ColorPickerBar.ColorPickerClickListener() {
      public void onClick(int selectedColor) {
        String hexColor = String.format("#%06X", 0xFFFFFF | selectedColor);
        SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        preferences.edit().putString("backgroundColor", hexColor).apply();;
//        prefs.edit().putString("backgroundColor",hexColor);
//        prefs.edit().apply();
        //将颜色（第一个）改变的操作写在此处，kotlin形式的代码如下
      }
    }));

    //进行字体颜色的设置，背景key为fontColor,默认颜色为#FFFFFF
    String fontColor=preferences.getString("fontColor","#FFFFFF");
//    ColorPicker2.selectItem(Arrays.binarySearch(arrayForColor,fontColor));
    ColorPicker2.setColorPickerClickListener((ColorPickerBar.ColorPickerClickListener)(new ColorPickerBar.ColorPickerClickListener() {
      public void onClick(int selectedColor) {
        String hexColor = String.format("#%06X", 0xFFFFFF | selectedColor);
        SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        preferences.edit().putString("fontColor", hexColor).apply();
//        prefs.edit().putString("fontColor",hexColor);
//        prefs.edit().apply();
        //将颜色（第二个）改变的操作写在此处
      }
    }));

    img1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击第一张图片，修改图标
        SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        preferences.edit().putInt("icon", R.mipmap.ic_folder).apply();;
        }
      }
    );

    img2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击第二张图片，修改图标
        SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        preferences.edit().putInt("icon", R.mipmap.ic_folder_2).apply();
        }
      }
    );

    img3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击第三张图片，修改图标
        SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        preferences.edit().putInt("icon", R.mipmap.ic_folder_3).apply();;
        }
      }
    );
    darkModeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        if (isChecked) {
          // The toggle is enabled
          preferences.edit().putBoolean("dark", true).apply();;
        } else {
          // The toggle is disabled
          preferences.edit().putBoolean("dark", false).apply();;
        }
      }
    });
  }
}


