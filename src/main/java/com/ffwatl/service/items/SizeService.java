package com.ffwatl.service.items;


import com.ffwatl.admin.entities.items.clothes.size.Size;

public interface SizeService {

    Size findById(long id);
    void removeById(long ... id);

}