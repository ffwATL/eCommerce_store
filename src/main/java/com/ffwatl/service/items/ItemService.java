package com.ffwatl.service.items;


import com.ffwatl.admin.entities.items.DefaultItem;
import com.ffwatl.admin.presenters.items.ItemPresenter;
import com.ffwatl.admin.presenters.items.update.ItemUpdatePresenter;

import java.util.List;

public interface ItemService {

    DefaultItem findById(long id);
    List<DefaultItem> findAll();
    void save(DefaultItem item);
    void remove(DefaultItem item);
    void changeItemStatus(DefaultItem item);
    void updateSingleItem(ItemUpdatePresenter update);
    void updateItems(ItemUpdatePresenter update);
    ItemPresenter findItemPresenterById(long id);
}