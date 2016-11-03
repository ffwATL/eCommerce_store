package com.ffwatl.service.items;


import com.ffwatl.dao.clothes.SizeDao;
import com.ffwatl.admin.entities.items.clothes.size.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SizeServiceImpl implements SizeService{

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
}