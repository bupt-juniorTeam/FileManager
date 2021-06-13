package com.BUPTJuniorTeam.filemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 负责打开具体的文件以及判断文件类型
 */
public class SpecifiedFileAccess {
  public SpecifiedFileAccess() {
  }

  //删除地址参数，以免判断类型是判断失误
  public static String removeParams(String url) {
    return url.replaceAll("\\?.*", "");
  }

  public static String sFileExtensions;

  // Audio
  public static final int FILE_TYPE_MP3 = 1;
  public static final int FILE_TYPE_M4A = 2;
  public static final int FILE_TYPE_WAV = 3;
  public static final int FILE_TYPE_XMF = 4;
  public static final int FILE_TYPE_MID = 5;
  public static final int FILE_TYPE_WMA = 6;
  public static final int FILE_TYPE_OGG = 7;
  private static final int FIRST_AUDIO_FILE_TYPE = FILE_TYPE_MP3;
  private static final int LAST_AUDIO_FILE_TYPE = FILE_TYPE_OGG;

  // Video
  public static final int FILE_TYPE_MP4 = 11;
  public static final int FILE_TYPE_M4V = 12;
  public static final int FILE_TYPE_3GPP = 13;
  public static final int FILE_TYPE_3GPP2 = 14;
  public static final int FILE_TYPE_WMV = 15;
  private static final int FIRST_VIDEO_FILE_TYPE = FILE_TYPE_MP4;
  private static final int LAST_VIDEO_FILE_TYPE = FILE_TYPE_WMV;

  // Image
  public static final int FILE_TYPE_JPEG = 21;
  public static final int FILE_TYPE_GIF = 22;
  public static final int FILE_TYPE_PNG = 23;
  public static final int FILE_TYPE_BMP = 24;
  public static final int FILE_TYPE_WBMP = 25;
  private static final int FIRST_IMAGE_FILE_TYPE = FILE_TYPE_JPEG;
  private static final int LAST_IMAGE_FILE_TYPE = FILE_TYPE_WBMP;

  // APK
  public static final int FILE_TYPE_APK = 31;

  //PPT
  public static final int FILE_TYPE_PPT = 41;

  //WORD
  public static final int FILE_TYPE_WORD = 46;

  //XLS
  public static final int FILE_TYPE_XLS = 51;

  //PDF
  public static final int FILE_TYPE_PDF = 51;

  //TXT
  public static final int FILE_TYPE_TXT = 56;

  //CHM
  private static final int FILE_TYPE_CHM = 61;


  //静态内部类
  static class MediaFileType {
    int fileType;
    String mimeType;

    MediaFileType(int fileType, String mimeType) {
      this.fileType = fileType;
      this.mimeType = mimeType;
    }

  }


  private static HashMap<String, MediaFileType> sFileTypeMap

      = new HashMap<String, MediaFileType>();

  private static HashMap<String, Integer> sMimeTypeMap

      = new HashMap<String, Integer>();

  static void addFileType(String extension, int fileType, String mimeType) {

    sFileTypeMap.put(extension, new MediaFileType(fileType, mimeType));

    sMimeTypeMap.put(mimeType, new Integer(fileType));

  }

  static {

    addFileType("MP3", FILE_TYPE_MP3, "audio/mpeg");
    addFileType("M4A", FILE_TYPE_M4A, "audio/mp4");
    addFileType("WAV", FILE_TYPE_WAV, "audio/x-wav");
    addFileType("XMF", FILE_TYPE_XMF, "audio/midi");
    addFileType("WMA", FILE_TYPE_WMA, "audio/x-ms-wma");
    addFileType("OGG", FILE_TYPE_OGG, "application/ogg");
    addFileType("MID", FILE_TYPE_MID, "audio/midi");
    addFileType("MP4", FILE_TYPE_MP4, "video/mp4");
    addFileType("M4V", FILE_TYPE_M4V, "video/mp4");
    addFileType("3GP", FILE_TYPE_3GPP, "video/3gpp");
    addFileType("3GPP", FILE_TYPE_3GPP, "video/3gpp");
    addFileType("3G2", FILE_TYPE_3GPP2, "video/3gpp2");
    addFileType("3GPP2", FILE_TYPE_3GPP2, "video/3gpp2");
    addFileType("WMV", FILE_TYPE_WMV, "video/x-ms-wmv");
    addFileType("JPG", FILE_TYPE_JPEG, "image/jpeg");
    addFileType("JPEG", FILE_TYPE_JPEG, "image/jpeg");
    addFileType("GIF", FILE_TYPE_GIF, "image/gif");
    addFileType("PNG", FILE_TYPE_PNG, "image/png");
    addFileType("BMP", FILE_TYPE_BMP, "image/x-ms-bmp");
    addFileType("WBMP", FILE_TYPE_WBMP, "image/vnd.wap.wbmp");
    addFileType("WORD", FILE_TYPE_WORD, "application/msword");
    addFileType("TXT", FILE_TYPE_TXT, "text/plain");
    addFileType("XLS", FILE_TYPE_XLS, "application/vnd.ms-excel");
    addFileType("PPT", FILE_TYPE_PPT, "application/vnd.ms-powerpoint");
    addFileType("PDF", FILE_TYPE_PDF, "application/pdf");
    addFileType("CHM", FILE_TYPE_CHM, "application/x-chm");
    addFileType("APK", FILE_TYPE_APK, "application/vnd.android.package-archive");


    // compute file extensions list for native Scanner
    StringBuilder builder = new StringBuilder();
    Iterator<String> iterator = sFileTypeMap.keySet().iterator();
    while (iterator.hasNext()) {
      if (builder.length() > 0) {
        builder.append(',');
      }
      builder.append(iterator.next());
    }
    sFileExtensions = builder.toString();

  }


  public Intent getIntent(String filename, Context context) {
    Intent intent;
    if (isWordFileType(filename)) {
      intent = getWordFileIntent(filename, context);
    } else if (isApkFileType(filename)) {
      intent = getApkFileIntent(filename, context);
    } else if (isTxtFileType(filename)) {
      intent = getTextFileIntent(filename,false, context);
    } else if (isChmFileType(filename)) {
      intent = getChmFileIntent(filename, context);
    } else if (isPdfFileType(filename)) {
      intent = getPdfFileIntent(filename, context);
    } else if (isXlsFileType(filename)) {
      intent = getExcelFileIntent(filename, context);
    } else if (isAudioFileType(filename)) {
      intent = getAudioFileIntent(filename, context);
    } else if (isVideoFileType(filename)) {
      intent = getVideoFileIntent(filename, context);
    } else if (isImageFileType(filename)) {
      intent = getImageFileIntent(filename, context);
    } else if (isPptFileType(filename)) {
      intent = getPptFileIntent(filename, context);
    } else {
      intent = getAllIntent(filename, context);
    }
    return intent;
  }

  public  String getFileMemeType(String path) {
    int lastDot = path.lastIndexOf(".");
    if (lastDot < 0)
      return null;
    MediaFileType type = sFileTypeMap.get(path.substring(lastDot + 1).toUpperCase());
    if (null != type) return type.mimeType;
    return null;
  }

  private static final String UNKNOWN_STRING = "<unknown>";


  private static boolean isAudioFileType(int fileType) {

    return ((fileType >= FIRST_AUDIO_FILE_TYPE &&
        fileType <= LAST_AUDIO_FILE_TYPE));
  }

  private static boolean isWordFileType(int fileType) {

    return (fileType == FILE_TYPE_WORD || fileType == FILE_TYPE_XLS || fileType == FILE_TYPE_PPT);
  }

  private static boolean isPptFileType(int fileType) {
    return (fileType == FILE_TYPE_PPT);
  }

  private static boolean isXlsFileType(int fileType) {
    return (fileType == FILE_TYPE_XLS);
  }

  private static boolean isPdfFileType(int fileType) {
    return (fileType == FILE_TYPE_PDF);
  }

  private static boolean isChmFileType(int fileType) {
    return (fileType == FILE_TYPE_CHM);
  }

  private static boolean isTxtFileType(int fileType) {
    return (fileType == FILE_TYPE_TXT);
  }

  private static boolean isApkFileType(int fileType) {
    return (fileType == FILE_TYPE_APK);
  }

  private static boolean isWordFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isWordFileType(type.fileType);
    }
    return false;
  }


  private static boolean isPptFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isPdfFileType(type.fileType);
    }
    return false;
  }

  private static boolean isXlsFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isXlsFileType(type.fileType);
    }
    return false;
  }

  public static boolean isPdfFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isPdfFileType(type.fileType);
    }
    return false;
  }

  private static boolean isChmFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isChmFileType(type.fileType);
    }
    return false;
  }

  public static boolean isTxtFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isTxtFileType(type.fileType);
    }
    return false;
  }

  public static boolean isApkFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isApkFileType(type.fileType);
    }
    return false;
  }

  private static boolean isVideoFileType(int fileType) {
    return (fileType >= FIRST_VIDEO_FILE_TYPE &&
        fileType <= LAST_VIDEO_FILE_TYPE);
  }


  private static boolean isImageFileType(int fileType) {
    return (fileType >= FIRST_IMAGE_FILE_TYPE &&
        fileType <= LAST_IMAGE_FILE_TYPE);
  }


  private static MediaFileType getFileType(String path) {
    int lastDot = path.lastIndexOf(".");
    if (lastDot < 0)
      return null;
    return sFileTypeMap.get(path.substring(lastDot + 1).toUpperCase());

  }



  //根据视频文件路径判断文件类型
  public static boolean isVideoFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isVideoFileType(type.fileType);
    }
    return false;

  }

  //根据音频文件路径判断文件类型

  public static boolean isAudioFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isAudioFileType(type.fileType);
    }
    return false;

  }


  //根据mime类型查看文件类型

  private  static int getFileTypeForMimeType(String mimeType) {
    Integer value = sMimeTypeMap.get(mimeType);
    return (value == null ? 0 : value.intValue());
  }


  //根据图片文件路径判断文件类型

  public static boolean isImageFileType(String path) {
    MediaFileType type = getFileType(path);
    if (null != type) {
      return isImageFileType(type.fileType);
    }
    return false;
  }



  // Android获取一个用于打开APK文件的intent
  private Intent getAllIntent(String param, Context context) {
    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setAction(Intent.ACTION_VIEW);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "*/*");
    return intent;
  }

  // Android获取一个用于打开APK文件的intent
  private Intent getApkFileIntent(String param, Context context) {
    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setAction(Intent.ACTION_VIEW);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "application/vnd.android.package-archive");
    return intent;
  }

  // Android获取一个用于打开VIDEO文件的intent
  private Intent getVideoFileIntent(String param, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("oneshot", 0);
    intent.putExtra("configchange", 0);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "video/*");
    return intent;
  }

  // Android获取一个用于打开AUDIO文件的intent
  private Intent getAudioFileIntent(String param, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("oneshot", 0);
    intent.putExtra("configchange", 0);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "audio/*");
    return intent;
  }

  // Android获取一个用于打开Html文件的intent
  private Intent getHtmlFileIntent(String param) {
    Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    intent.setDataAndType(uri, "text/html");
    return intent;
  }

  // Android获取一个用于打开图片文件的intent
  private Intent getImageFileIntent(String param, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "image/*");
    return intent;
  }

  // Android获取一个用于打开PPT文件的intent
  private Intent getPptFileIntent(String param, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
    return intent;
  }

  // Android获取一个用于打开Excel文件的intent
  private Intent getExcelFileIntent(String param, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "application/vnd.ms-excel");
    return intent;
  }

  // Android获取一个用于打开Word文件的intent
  private Intent getWordFileIntent(String param, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "application/msword");
    return intent;
  }

  // Android获取一个用于打开CHM文件的intent
  private Intent getChmFileIntent(String param, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "application/x-chm");
    return intent;
  }

  // Android获取一个用于打开文本文件的intent
  private Intent getTextFileIntent(String param, boolean paramBoolean, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    if (paramBoolean) {
      Uri uri1 = FileProvider.getUriForFile(context, "filemanager", new File(param));;
      intent.setDataAndType(uri1, "text/plain");
    } else {
      Uri uri2 = FileProvider.getUriForFile(context, "filemanager", new File(param));;;
      intent.setDataAndType(uri2, "text/plain");
    }
    return intent;
  }

  // Android获取一个用于打开PDF文件的intent
  private Intent getPdfFileIntent(String param, Context context) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    Uri uri = FileProvider.getUriForFile(context, "filemanager", new File(param));
    intent.setDataAndType(uri, "application/pdf");
    return intent;
  }

}
