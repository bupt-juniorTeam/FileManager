package com.BUPTJuniorTeam.filemanager.utils;

import java.io.File;

public class Futils {
    public static long folderSize(File directory, boolean rootMode) {
        long length = 0;
        for (File file:directory.listFiles()) {

            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file,rootMode);
        }
        return length;
    }

    public boolean canListFiles(File f) {
        try {
            if (f.canRead() && f.isDirectory())
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] parseName(String line){
        boolean linked=false;String name="",link="";
        String[] array=line.split(" ");
        for(int i=0;i<array.length;i++){
            if(array[i].contains("->")){linked=true;}
        }
        if(!linked){int p=getColonPosition(array);
            for(int i=p+1;i<array.length;i++){name=name+" "+array[i];}
            name=name.trim();
            return new String[]{name,"",array[0]};
        }
        else if(linked){int p=getColonPosition(array);
            int q=getLinkPosition(array);
            for(int i=p+1;i<q;i++){name=name+" "+array[i];}
            name=name.trim();
            for(int i=q+1;i<array.length;i++){link=link+" "+array[i];}
            return  new String[]{name,link,array[0]};
        }
        return new String[]{name,"",array[0]};
    }

    public int getColonPosition(String[] array){
        for(int i=0;i<array.length;i++){
            if(array[i].contains(":"))return i;
        }
        return  0;
    }

    public int getLinkPosition(String[] array){
        for(int i=0;i<array.length;i++){
            if(array[i].contains("->"))return i;
        }
        return  0;
    }
}
