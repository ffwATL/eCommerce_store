package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.Size;

public interface SizeService {

    Size findById(long id);
    void removeById(long ... id);

}