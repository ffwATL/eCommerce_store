package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.SizeDao;
import com.ffwatl.admin.catalog.domain.Field;
import com.ffwatl.admin.catalog.domain.FieldImpl;
import com.ffwatl.admin.catalog.domain.Size;
import com.ffwatl.admin.catalog.domain.SizeImpl;
import com.ffwatl.admin.catalog.domain.dto.FieldDTO;
import com.ffwatl.admin.catalog.domain.dto.SizeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl extends ConverterDTO<Size> implements SizeService{

    @Autowired
    private SizeDao sizeDao;

    @Override
    public Size findById(long id) {
        return sizeDao.findById(id);
    }

    @Override
    @Transactional
    public void removeById(long... id) {
        for(long i: id){
            sizeDao.removeById(i);
        }
    }

    @Override
    public Size transformDTO2Entity(Size old) {
        List<Field> fields = old.getMeasurements().stream().map(f -> new FieldImpl()
                .setId(f.getId())
                .setName(f.getName())
                .setValue(f.getValue()))
                .collect(Collectors.toList());
        return new SizeImpl()
                .setId(old.getId())
                .setEu_size(old.getEu_size())
                .setQuantity(old.getQuantity())
                .setMeasurements(fields);
    }

    @Override
    public Size transformEntity2DTO(Size old) {
        List<Field> fields = old.getMeasurements().stream().map(f -> new FieldDTO()
                .setId(f.getId())
                .setName(f.getName())
                .setValue(f.getValue()))
                .collect(Collectors.toList());
        return new SizeDTO()
                .setId(old.getId())
                .setEu_size(old.getEu_size())
                .setQuantity(old.getQuantity())
                .setMeasurements(fields);
    }


}