package com.ffwatl;


import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.CategoryImpl;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryDeserializer implements JsonDeserializer<Category> {


    @Override
    public Category deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return extractIgroup(json.getAsJsonObject(), context);
    }

    private Category extractIgroup(JsonObject jsonObject, JsonDeserializationContext context){
        Category category = new CategoryImpl();
        category.setGroupName(context.deserialize(jsonObject.get("groupName"), I18n.class));
        category.setCat(CommonCategory.valueOf(jsonObject.get("cat").getAsString()));
        category.setLevel(jsonObject.get("level").getAsInt());
        JsonElement childElement = jsonObject.get("child");
        if(childElement != null){
            JsonArray child = childElement.getAsJsonArray();
            List<Category> childList = new ArrayList<>();
            for(JsonElement el: child){
                childList.add(extractIgroup(el.getAsJsonObject(),context));
            }
            category.setChild(childList);
        }
        return category;
    }
}