package com.ffwatl;


import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.ProductCategoryImpl;
import com.ffwatl.admin.i18n.domain.I18n;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryDeserializer implements JsonDeserializer<ProductCategory> {


    @Override
    public ProductCategory deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return extractIgroup(json.getAsJsonObject(), context);
    }

    private ProductCategory extractIgroup(JsonObject jsonObject, JsonDeserializationContext context){
        ProductCategory productCategory = new ProductCategoryImpl();
        productCategory.setCategoryName(context.deserialize(jsonObject.get("groupName"), I18n.class));
        productCategory.setLevel(jsonObject.get("level").getAsInt());
        JsonElement childElement = jsonObject.get("child");
        if(childElement != null){
            JsonArray child = childElement.getAsJsonArray();
            List<ProductCategory> childList = new ArrayList<>();
            for(JsonElement el: child){
                childList.add(extractIgroup(el.getAsJsonObject(),context));
            }
            productCategory.setChild(childList);
        }
        return productCategory;
    }
}