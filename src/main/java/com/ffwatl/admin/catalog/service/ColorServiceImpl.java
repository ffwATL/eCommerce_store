package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ColorDao;
import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.ColorImpl;
import com.ffwatl.admin.catalog.domain.dto.ColorDTO;
import com.ffwatl.admin.catalog.domain.dto.response.AccessMode;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.service.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.ffwatl.common.service.ConvertToType.DTO_OBJECT;

@Service("color_service")
@Transactional(readOnly = true)
public class ColorServiceImpl extends Converter<Color> implements ColorService{

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "color_dao")
    private ColorDao colorDao;

    @Override
    public Color findById(long id) {
        if(id < 1) {
            LOGGER.warn("findById --> wrong id={} is given", id);
            throw new IllegalArgumentException("Wrong id is given: id=" + id);
        }
        LOGGER.trace("findById --> id={}", id);
        return colorDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Color c) {
        if(c == null) {
            LOGGER.error("save --> given 'Color' entity is null");
            throw new IllegalArgumentException("Entity 'Color' can't be null");
        }
        if(c instanceof ColorDTO) {
            c = transformDTO2Entity(c, FetchMode.LAZY, AccessMode.ALL);
        }
        LOGGER.trace("save --> {}", c);
        colorDao.save(c);
    }

    @Override
    @Transactional
    public void save(List<Color> list) {
        if(list == null || list.size() < 1) {
            LOGGER.error("save --> incorrect 'Color' entity list is given: {}", list);
            throw new IllegalArgumentException("Entity ColorImpl can't be null");
        }
        list.forEach(this::save);
    }

    @Override
    @Transactional
    public void remove(Color c) {
        if(c == null) {
            LOGGER.error("remove --> given 'Color' entity is null");
            throw new IllegalArgumentException("Entity 'Color' can't be null");
        }

        c = findById(c.getId());
        LOGGER.debug("remove --> {}", c);
        colorDao.remove(c);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        Color color = findById(id);
        LOGGER.debug("removeById --> id={}", id);
        remove(color);
    }

    @Override
    public List<Color> findAll() {
        return transformList(colorDao.findAll(), DTO_OBJECT, FetchMode.LAZY, AccessMode.ALL);
    }

    @Override
    public List<Color> findAllUsed() {
        return transformList(colorDao.findAllUsed(), DTO_OBJECT, FetchMode.LAZY, AccessMode.ALL);
    }


    @Override
    public ColorImpl transformDTO2Entity(Color old, FetchMode fetchMode, AccessMode accessMode) {
        return (ColorImpl) new ColorImpl()
                .setId(old.getId())
                .setColorName(old.getColorName())
                .setHex(old.getHex());
    }

    @Override
    public Color transformEntity2DTO(Color old, FetchMode fetchMode, AccessMode accessMode) {
        return new ColorDTO()
                .setId(old.getId())
                .setColorName(old.getColorName())
                .setHex(old.getHex());
    }
}