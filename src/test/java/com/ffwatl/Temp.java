package com.ffwatl;

import com.ffwatl.admin.entities.group.ItemGroup;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Temp {

    @Test
    @Ignore
    public void readGson() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader("item_group_tree.json"));
        ItemGroup i = new Gson().fromJson(reader, ItemGroup.class);
      /*  fillParent(null, i);*/
        System.err.println(i);
    }

    @Test
    @Ignore
    public void images(){
        String dirName = "c:\\atl_folder\\items\\images\\item_2";
        int count = 1;
        String end = "l.jpg";
        for(File f: finder(dirName, end)){
            f.renameTo(new File(dirName+"\\image"+(count++)+end));
        }
    }

    public void rearrangeImages(String dirName, String[] ends){
        for(String end: ends){
            int count = 0;
            for(File f: finder(dirName, end)){
                f.renameTo(new File(dirName+"\\image"+(count++)+end));
            }
        }
    }

    private File[] finder(String dirName, String endName){
        return new File(dirName).listFiles((dir1, filename) -> {return filename.endsWith(endName);});
    }

   /* private void fillParent(ItemGroup parent, ItemGroup child){
        if(child == null) return;
        for(ItemGroup i: child.setParent(parent).getChild()){
            fillParent(child, i);
        }
    }*/
}