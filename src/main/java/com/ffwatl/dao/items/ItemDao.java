package com.ffwatl.dao.items;


import com.ffwatl.admin.entities.items.DefaultItem;

import java.util.List;

public interface ItemDao {

    DefaultItem findById(long id);
    List<DefaultItem> findAll();
    void save(DefaultItem item);
    void remove(DefaultItem item);

}
