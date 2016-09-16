package com.ffwatl.dao.items;


import com.ffwatl.domain.items.Item;

import java.util.List;

public interface ItemDao {

    Item findById(long id);
    List<Item> findAll();
    void save(Item item);
    void remove(Item item);

}
