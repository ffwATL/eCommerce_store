package com.ffwatl;


import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.group.ItemGroup;
import com.ffwatl.admin.entities.i18n.I18n;
import com.ffwatl.admin.entities.items.CommonCategory;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IGroupDeserializer implements JsonDeserializer<IGroup> {


    @Override
    public IGroup deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return extractIgroup(json.getAsJsonObject(), context);
    }

    private IGroup extractIgroup(JsonObject jsonObject, JsonDeserializationContext context){
        IGroup iGroup = new ItemGroup();
        iGroup.setGroupName(context.deserialize(jsonObject.get("groupName"), I18n.class));
        iGroup.setCat(CommonCategory.valueOf(jsonObject.get("cat").getAsString()));
        iGroup.setLevel(jsonObject.get("level").getAsInt());
        JsonElement childElement = jsonObject.get("child");
        if(childElement != null){
            JsonArray child = childElement.getAsJsonArray();
            List<IGroup> childList = new ArrayList<>();
            for(JsonElement el: child){
                childList.add(extractIgroup(el.getAsJsonObject(),context));
            }
            iGroup.setChild(childList);
        }
        return iGroup;
    }
}