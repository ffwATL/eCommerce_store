package com.ffwatl.service.clothes;


import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClothesItemService {

    ClothesItem findById(long id);

    void removeById(long id);

    void save(ClothesItem item, List<MultipartFile> file, String email) throws IOException;
}
