package com.ffwatl.common.service;


import com.ffwatl.common.persistence.FetchMode;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public abstract class ConverterDTO<I> {

    public static final int DTO_OBJECT = 0;
    public static final int ENTITY_OBJECT = 1;

    public List<I> transformList(@NotNull List<? extends I> old, int toObjectType, FetchMode fetchMode) {
        List<I> result = new ArrayList<>();

        switch (toObjectType) {
            case DTO_OBJECT: {
                for (I o: old) {
                    result.add(transformEntity2DTO(o, fetchMode));
                }
                return result;
            }
            case ENTITY_OBJECT: {
                for (I o: old) {
                    result.add(transformDTO2Entity(o, fetchMode));
                }
                return result;
            }
            default: throw new IllegalArgumentException("Wrong object type were passed; [type] = " + toObjectType);
        }
    }

    //FIXME: FetchMode need to be added!!
    public abstract I transformDTO2Entity(I old, FetchMode fetchMode);

    public abstract I transformEntity2DTO(I old, FetchMode fetchMode);

}