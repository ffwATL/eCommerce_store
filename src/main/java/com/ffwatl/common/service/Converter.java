package com.ffwatl.common.service;


import com.ffwatl.admin.catalog.domain.dto.response.AccessMode;
import com.ffwatl.common.persistence.FetchMode;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public abstract class Converter<I> {


    public List<I> transformList(@NotNull List<? extends I> old, ConvertToType type, FetchMode fetchMode, AccessMode accessMode) {
        List<I> result = new ArrayList<>();

        switch (type) {
            case DTO_OBJECT: {
                for (I o: old) {
                    result.add(transformEntity2DTO(o, fetchMode, accessMode));
                }
                return result;
            }
            case ENTITY_OBJECT: {
                for (I o: old) {
                    result.add(transformDTO2Entity(o, fetchMode, accessMode));
                }
                return result;
            }
            default: throw new IllegalArgumentException("Wrong object type were passed; [type] = " + type);
        }
    }

    //FIXME: FetchMode need to be added!!
    public abstract I transformDTO2Entity(I old, FetchMode fetchMode, AccessMode accessMode);

    public abstract I transformEntity2DTO(I old, FetchMode fetchMode, AccessMode accessMode);
}