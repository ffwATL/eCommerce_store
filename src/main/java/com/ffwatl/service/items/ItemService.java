package com.ffwatl.service.items;


import com.ffwatl.domain.items.Item;
import com.ffwatl.domain.update.ItemUpdate;

import java.util.List;

public interface ItemService {

    Item findById(long id);
    List<Item> findAll();
    void save(Item item);
    void remove(Item item);
    void updateSingleItem(ItemUpdate update);
    void updateItems(ItemUpdate update);
}