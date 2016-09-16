package com.ffwatl;

import com.ffwatl.domain.group.ItemGroup;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class Temp {

    @Test
    @Ignore
    public void readGson() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader("item_group_tree.json"));
        ItemGroup i = new Gson().fromJson(reader, ItemGroup.class);
        fillParent(null, i);
        System.err.println(i);
    }

    private void fillParent(ItemGroup parent, ItemGroup child){
        if(child == null) return;
        for(ItemGroup i: child.setParent(parent).getChild()){
            fillParent(child, i);
        }
    }
}