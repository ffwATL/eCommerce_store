package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.Size;

public interface SizeDao {

    Size findById(long id);
    void removeById(long id);

}