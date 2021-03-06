package com.BUPTJuniorTeam.filemanager.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.BUPTJuniorTeam.filemanager.R;
import com.BUPTJuniorTeam.filemanager.utils.FileProperty;
import com.BUPTJuniorTeam.filemanager.utils.HistoryManager;
import com.BUPTJuniorTeam.filemanager.utils.SpecifiedFileAccess;
import java.io.File;
import java.util.List;

public class FileListAdapter extends ArrayAdapter<FileProperty> {

  private List<FileProperty> list = null;
  private Context context = null;
  private static boolean open_only = false;

  public static void setOpenOnly(boolean open) {
    open_only = open;
  }

  public FileListAdapter(@NonNull Context context, int resource,
      @NonNull List<FileProperty> objects) {
    super(context, resource, objects);
    this.list = objects;
    this.context = context;
  }

  private static class ViewHolder {
    ImageView icon;
    TextView caption;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    ViewHolder holder = null;
    if (convertView == null) {
      holder = new ViewHolder();
      LayoutInflater mInflater=LayoutInflater.from(context);
      convertView = mInflater.inflate(R.layout.file_list, null);
      holder.icon = (ImageView) convertView.findViewById(R.id.file_list_icon);
      holder.caption = (TextView) convertView.findViewById(R.id.file_list_caption);
      convertView.setTag(holder);
    }
    else {
      holder = (ViewHolder) convertView.getTag();
    }

    convertView.setBackgroundColor(Color.parseColor(((MainActivity)context).getBackgroundColor()));
    holder.caption.setTextColor(Color.parseColor(((MainActivity)context).getFrontColor()));
    holder.caption.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ((MainActivity)context).getFontSize());

    final FileProperty property = list.get(position);
    if (property != null) {
      holder.caption.setText(property.getName());

      convertView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          if ("..".equals(property.getName())) {
            ((MainActivity)context).resetCurrentPath(property.getName());
          }
          else {
            HistoryManager manager = HistoryManager.getInstance(context);
            manager.addHistoryItem(property);
            File file = new File(property.getPath() + "/" + property.getName());
            if (file.isDirectory()) {
              if (open_only) {
                ((MainActivity)context).resetCurrentPathDirectly(property.getRelativePath() + property.getName());
              }
              else {
                ((MainActivity)context).resetCurrentPath(property.getName() + "/");
              }
            }
            else {
              SpecifiedFileAccess access = new SpecifiedFileAccess();
              Intent intent = access.getIntent(property.getPath() + "/" + property.getName(), context);
              try {
                ((MainActivity)context).startActivity(intent);
              }
              catch (Exception e) {
                e.printStackTrace();
              }

            }
          }
        }
      });

      convertView.setLongClickable(true);
      convertView.setOnLongClickListener(new OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
          if (!open_only) {
            popMenu(view, property);
          }
          return true;
        }
      });

      if (SpecifiedFileAccess.isApkFileType(property.getName())) {
        holder.icon.setImageResource(R.mipmap.ic_apk);
      }
      else if (SpecifiedFileAccess.isAudioFileType(property.getName())) {
        holder.icon.setImageResource(R.mipmap.ic_audio);
      }
      else if (SpecifiedFileAccess.isImageFileType(property.getName())) {
        holder.icon.setImageResource(R.mipmap.ic_image);
      }
      else if (SpecifiedFileAccess.isPdfFileType(property.getName())) {
        holder.icon.setImageResource(R.mipmap.ic_pdf);
      }
      else if (SpecifiedFileAccess.isTxtFileType(property.getName())) {
        holder.icon.setImageResource(R.mipmap.ic_text);
      }
      else if (SpecifiedFileAccess.isVideoFileType(property.getName())) {
        holder.icon.setImageResource(R.mipmap.ic_video);
      }
      else {
        File file = new File(property.getPath() + "/" + property.getName());
        if (file.isDirectory()) {
          int icon = ((MainActivity)context).getFoldIcon();
          holder.icon.setImageResource(icon);
        }
        else {
          holder.icon.setImageResource(R.mipmap.ic_wtf);
        }
      }
    }

    return convertView;
  }

  private void popMenu(View view, final FileProperty property) {
    final PopupMenu menu = new PopupMenu(context, view);
    menu.getMenuInflater().inflate(R.menu.action_menu, menu.getMenu());
    menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem menuItem) {
        String title = menuItem.getTitle().toString();
        if ("Copy".equals(title)) {
          ((MainActivity)context).copyFile(property.getRelativePath() + property.getName());
        }
        else if ("Move".equals(title)) {
          ((MainActivity)context).moveFile(property.getRelativePath() + property.getName());
        }
        else if ("Delete".equals(title)) {
          ((MainActivity)context).removeFile(property.getRelativePath() + property.getName());
        }
        else if ("Detail".equals(title)) {
          showDetailDialog(property);
        }
        else if ("Compress".equals(title)) {
          ((MainActivity)context).compress(property.getPath() + "/" + property.getName());
        }
        else if ("Decompress".equals(title)) {
          ((MainActivity)context).decompress(property.getPath() + "/" + property.getName());
        }
        return true;
      }
    });

    menu.show();
  }

  private String getFileDetail(FileProperty property) {
    String type = property.getType();
    if (property.getType() == null) {
      File file = new File(property.getPath() + "/" + property.getName());
      if (file.isDirectory()) {
        type = "Directory";
      }
      else {
        type = "Unknown";
      }
    }

    return "????????????" + property.getName() + "\n" +
        "?????????" + property.getPath() + "\n" +
        "?????????????????????" + property.getModified_time() + "\n" +
        "???????????????" + type + "\n" +
        "???????????????" + String.valueOf(property.getSize()) + "\n";
  }

  private void showDetailDialog(FileProperty property) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setIcon(R.mipmap.ic_about);
    builder.setTitle("????????????");
    builder.setMessage(getFileDetail(property));
    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
      }
    });

    builder.show();
  }
}
