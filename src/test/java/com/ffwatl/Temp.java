package com.ffwatl;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Temp {

    @Test
    @Ignore
    public void convertData(){
        File file = new File("AJI05.DAT");
        if(!file.exists()) throw new AssertionError("file not exist");
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
            FileWriter writer = new FileWriter("converted__15.txt");
            int i = 0;
            boolean skip = false;

            while (inputStream.available() > 0){
                String s = inputStream.readLine();

                if(i == 700) {
                    skip = true;
                }else if(i == 8192){
                    i = 0;
                    skip = false;
                }

                if(!skip){
                    writeResult(s, writer);
                }
                i++;
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResult(final String s, FileWriter writer) throws IOException {
        String[] arr = s.split("    ");
        int value = Integer.valueOf(arr[0])*15;
        StringBuilder builder = new StringBuilder()
                .append("[")
                .append(value)
                .append(",")
                .append(arr[1])
                .append("],");
        writer.append(builder.toString());
    }

    @Test
    @Ignore
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