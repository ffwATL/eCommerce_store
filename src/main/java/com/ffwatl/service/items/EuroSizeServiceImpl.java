package com.ffwatl.service.items;

import com.ffwatl.dao.items.EuroSizeRepository;
import com.ffwatl.domain.items.CommonCategory;
import com.ffwatl.domain.items.clothes.size.EuroSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EuroSizeServiceImpl implements EuroSizeService {

    @Autowired
    private EuroSizeRepository euroSizeRepository;


    @Override
    public EuroSize findById(long id) {
        return euroSizeRepository.findOne(id);
    }

    @Override
    @Transactional
    public void save(EuroSize eu) {
        euroSizeRepository.save(eu);

    }

    @Override
    @Transactional
    public List<EuroSize> findByCat(CommonCategory cat){
        return euroSizeRepository.findByCat(cat);
    }

    @Override
    @Transactional
    public void save(List<EuroSize> list) {
        euroSizeRepository.save(list);
    }

    @Override
    public List<EuroSize> findAll() {
        return euroSizeRepository.findAll();
    }

}