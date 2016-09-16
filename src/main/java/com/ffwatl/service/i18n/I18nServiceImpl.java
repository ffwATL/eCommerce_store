package com.ffwatl.service.i18n;

import com.ffwatl.dao.i18n.I18nDao;
import com.ffwatl.domain.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class I18nServiceImpl implements I18nService{

    @Autowired
    private I18nDao i18nDao;

    @Override
    @Transactional
    public void save(I18n t) {
        if(t == null) throw new IllegalArgumentException("Object can't be null");
        I18n t2 = findByEn(t.getLocale_en());
        if(t2 != null)return;
        i18nDao.save(t);
    }

    @Override
    public I18n findByEn(String name) {
        if(name == null) throw new IllegalArgumentException("Name can't be null");
        List<I18n> list = i18nDao.findByEn(name);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void removeByEn(String name) {
        if(name == null) throw new IllegalArgumentException("Name can't be null");
        List<I18n> list = i18nDao.findByEn(name);
        if(list.size() < 1) throw new IllegalArgumentException("Such object doesn't present in the table");
        i18nDao.remove(list.get(0));
    }
}