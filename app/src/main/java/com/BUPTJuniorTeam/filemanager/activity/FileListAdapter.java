package com.BUPTJuniorTeam.filemanager.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.BUPTJuniorTeam.filemanager.R;
import com.BUPTJuniorTeam.filemanager.utils.FileProperty;
import com.BUPTJuniorTeam.filemanager.utils.SpecifiedFileAccess;
import java.io.File;
import java.util.List;

public class FileListAdapter extends ArrayAdapter<FileProperty> {

  private List<FileProperty> list = null;
  private Context context = null;

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

    FileProperty property = list.get(position);
    if (property != null) {
      holder.caption.setText(property.getName());

      if (SpecifiedFileAccess.isApkFileType(property.getPath())) {
        holder.icon.setImageResource(R.mipmap.ic_apk);
      }
      else if (SpecifiedFileAccess.isAudioFileType(property.getPath())) {
        holder.icon.setImageResource(R.mipmap.ic_audio);
      }
      else if (SpecifiedFileAccess.isImageFileType(property.getPath())) {
        holder.icon.setImageResource(R.mipmap.ic_image);
      }
      else if (SpecifiedFileAccess.isPdfFileType(property.getPath())) {
        holder.icon.setImageResource(R.mipmap.ic_pdf);
      }
      else if (SpecifiedFileAccess.isTxtFileType(property.getPath())) {
        holder.icon.setImageResource(R.mipmap.ic_text);
      }
      else if (SpecifiedFileAccess.isVideoFileType(property.getPath())) {
        holder.icon.setImageResource(R.mipmap.ic_video);
      }
      else {
        File file = new File(property.getPath());
        if (file.isDirectory()) {
          holder.icon.setImageResource(R.mipmap.ic_folder);
        }
        else {
          holder.icon.setImageResource(R.mipmap.ic_wtf);
        }
      }
    }

    return convertView;
  }
}
