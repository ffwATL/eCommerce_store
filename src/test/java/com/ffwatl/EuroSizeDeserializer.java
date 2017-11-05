/*
package com.ffwatl;

import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;
import com.ffwatl.admin.i18n.domain.I18n;
import com.google.gson.*;

import java.lang.reflect.Type;



public class EuroSizeDeserializer implements JsonDeserializer<ProductAttributeType> {

    @Override
    public ProductAttributeType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new ProductAttributeType()
                .setCat(CommonCategory.valueOf(jsonObject.get("cat").getAsString()))
                .setName(context.deserialize(jsonObject.get("name"), I18n.class));
    }
}*/
