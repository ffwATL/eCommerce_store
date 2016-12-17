package com.ffwatl;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;


public class Temp {

    @Test
   /* @Ignore*/
    public void readGson() throws FileNotFoundException {
        int i = 5;
        i = i++;
        System.out.println(i);
        /*JsonReader reader = new JsonReader(new FileReader("item_group_tree.json"));
        Category i = new Gson().fromJson(reader, CategoryImpl.class);*/
        AtomicInteger aI = new AtomicInteger(2);
      /*  fillParent(null, i);*/

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

   /* private void fillParent(CategoryImpl parent, CategoryImpl child){
        if(child == null) return;
        for(CategoryImpl i: child.setParent(parent).getChild()){
            fillParent(child, i);
        }
    }*/
}