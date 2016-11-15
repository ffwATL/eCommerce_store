package com.ffwatl.admin.catalog.service;


import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ConverterDTO<I> {

    public static final int DTO_OBJECT = 0;
    public static final int ENTITY_OBJECT = 1;

    public List<I> transformList(@NotNull List<? extends I> old, int objectType){
        switch (objectType){
            case DTO_OBJECT: return old.stream().map(this::transformEntity2DTO).collect(Collectors.toList());
            case ENTITY_OBJECT: return old.stream().map(this::transformDTO2Entity).collect(Collectors.toList());
            default: throw new IllegalArgumentException("Wrong object type were passed; [type] = " + objectType);
        }
    }

    public abstract I transformDTO2Entity(I old);

    public abstract I transformEntity2DTO(I old);

}