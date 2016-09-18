package com.ffwatl.service.items;


import com.ffwatl.manage.entities.items.Item;
import com.ffwatl.manage.presenters.items.ItemPresenter;
import com.ffwatl.manage.presenters.items.update.ItemUpdatePresenter;

import java.util.List;

public interface ItemService {

    Item findById(long id);
    List<Item> findAll();
    void save(Item item);
    void remove(Item item);
    void updateSingleItem(ItemUpdatePresenter update);
    void updateItems(ItemUpdatePresenter update);
    ItemPresenter findItemPresenterById(long id);
}