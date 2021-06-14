package com.BUPTJuniorTeam.filemanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.BUPTJuniorTeam.filemanager.R;
import com.cxyzy.colorpickerbar.ColorPickerBar;

import java.util.Arrays;



public class PreferenceActivity extends AppCompatActivity {

  private ColorPickerBar ColorPicker1 = null;
  private ColorPickerBar ColorPicker2 = null;
  private ImageView img1 = null;
  private ImageView img2 = null;
  private ImageView img3 = null;
  private ToggleButton darkModeToggle = null;


  private String[] arrayForColor={"#D71345", "#BED742", "#4E72B8", "#9B95C9", "#45B97C"};


  public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();

    // Check which radio button was clicked
    switch(view.getId()) {
      case R.id.radio_B://点击字体大时
        if (checked)
          // Pirates are the best
          break;
      case R.id.radio_M://点击字体中时
        if (checked)
          // Ninjas rule
          break;
      case R.id.radio_S://点击
        if (checked)
          // Pirates are the best
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
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preference);
//    SharedPreferences prefs = getActivity().getSharedPreferences(
//            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    loadViews();
    ColorPicker1.init(arrayForColor);
    ColorPicker1.selectItem(0);
    ColorPicker2.init(arrayForColor);
    ColorPicker2.selectItem(0);

    //进行背景颜色的设置，背景key为backgroundColor,默认颜色为#FFFFFF
//    String backgroundColor=prefs.getString("backgroundColor","#FFFFFF");
//    ColorPicker1.selectItem(Arrays.binarySearch(arrayForColor,backgroundColor));
    ColorPicker1.setColorPickerClickListener((ColorPickerBar.ColorPickerClickListener)(new ColorPickerBar.ColorPickerClickListener() {
      public void onClick(int selectedColor) {
        String hexColor = String.format("#%06X", 0xFFFFFF | selectedColor);
//        prefs.edit().putString("backgroundColor",hexColor);
//        prefs.edit().apply();
        //将颜色（第一个）改变的操作写在此处，kotlin形式的代码如下
      }
    }));

    //进行字体颜色的设置，背景key为fontColor,默认颜色为#FFFFFF
//    String fontColor=prefs.getString("fontColor","#FFFFFF");
//    ColorPicker2.selectItem(Arrays.binarySearch(arrayForColor,fontColor));
    ColorPicker2.setColorPickerClickListener((ColorPickerBar.ColorPickerClickListener)(new ColorPickerBar.ColorPickerClickListener() {
      public void onClick(int selectedColor) {
        String hexColor = String.format("#%06X", 0xFFFFFF | selectedColor);
//        prefs.edit().putString("fontColor",hexColor);
//        prefs.edit().apply();
        //将颜色（第二个）改变的操作写在此处
      }
    }));

    img1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击第一张图片，修改图标
        }
      }
    );

    img2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击第二张图片，修改图标
        }
      }
    );

    img3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击第三张图片，修改图标
        }
      }
    );
    darkModeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          // The toggle is enabled
        } else {
          // The toggle is disabled
        }
      }
    });
  }
}


