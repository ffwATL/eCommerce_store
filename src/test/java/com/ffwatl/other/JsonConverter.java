package com.ffwatl.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mmed 11/22/17
 */
public class JsonConverter {

    private Map<String, Object> types = new HashMap<>();

    @Test
    @Ignore
    public void convert() throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream("old.json"),"UTF-8");
        Map<String, Object> map = new ObjectMapper().readValue(reader, Map.class);
        List<Map<String, Object>> favorites = (List<Map<String, Object>>) map.get("favorites");

        for(Map<String, Object> entry: favorites) {
            String itemType = (String) entry.get("itemType");
            String itemName = (String) entry.get("itemName");
            String itemPhase = (String) entry.get("itemPhase");
            Integer discountMin = (Integer) entry.get("discountMin");

            Map<String, Object> typeEntry = getMapFromByKey(itemType, types);
            Map<String, Object> itemEntry = getMapFromByKey(itemName, typeEntry);
            Map<String, Object> phaseEntry = getMapFromByKey(itemPhase, itemEntry);

            if (itemPhase != null) {
                phaseEntry.put("discountMin", discountMin);
                if (!itemEntry.containsKey("checkPhase")) {
                    itemEntry.put("checkPhase", true);
                }
            }else {
                itemEntry.put("discountMin", discountMin);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("favoritesOn", true);
        result.put("rulesOn", true);
        result.put("favorites", types);

        new ObjectMapper().writeValue(new File("out.json"), result);
        System.err.println(types);
    }

    private Map<String, Object> getMapFromByKey(String key, Map<String, Object> from) {
        if (from.containsKey(key) && from.get(key) instanceof Map) {
            return (Map<String, Object>) from.get(key);
        }

        Map<String, Object> result = new HashMap<>();
        if (key != null) {
            from.put(key, result);
        }
        return result;
    }
}
