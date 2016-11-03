package com.ffwatl.dao.clothes;


import com.ffwatl.admin.entities.items.clothes.size.Size;

public interface SizeDao {

    Size findById(long id);
    void removeById(long id);

}