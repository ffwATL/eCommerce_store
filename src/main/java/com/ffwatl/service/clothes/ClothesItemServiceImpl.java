package com.ffwatl.service.clothes;

import com.ffwatl.dao.clothes.ClothesItemDao;
import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import com.ffwatl.manage.entities.items.clothes.size.Size;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.items.ColorService;
import com.ffwatl.service.items.EuroSizeService;
import com.ffwatl.service.users.UserService;
import com.ffwatl.util.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ClothesItemServiceImpl implements ClothesItemService{

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ClothesItemDao clothesItemDao;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private EuroSizeService euroSizeService;
    @Autowired
    private ItemGroupService itemGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private Settings settings;

    @Override
    public ClothesItem findById(long id) {
        return clothesItemDao.findById(id);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        clothesItemDao.remove(findById(id));
    }

    @Override
    @Transactional
    public void save(ClothesItem item, List<MultipartFile> file, String email) throws IOException {
        try {
            String dirPath = settings.getPhotoDir() + "item_" + item.getId();
            Path path = Paths.get(dirPath).toAbsolutePath();
            Files.createDirectory(path);
            int count = 1;
            for(MultipartFile f: file){
                saveImages(f, dirPath, count++);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        item.setAddedBy(userService.findByEmail(email));
        item.setColor(colorService.findById(item.getColor().getId()));
        item.setItemGroup(itemGroupService.findById(item.getItemGroup().getId()));
        item.setBrand(brandService.findById(item.getBrand().getId()));
        for (Size s: item.getSize()){
            s.setEu_size(euroSizeService.findById(s.getEu_size().getId()));
        }
        item.setImportDate(new Date());
        item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
        clothesItemDao.save(item);
    }

    private void saveImages(MultipartFile f, String dirPath, int count) throws IOException {
        if (f == null) return;
        File file = new File(dirPath + "\\"+"image"+count+"xl.jpg");
        try(OutputStream os = new FileOutputStream(file)) {
            ImageIO.write(Scalr.resize(ImageIO.read(f.getInputStream()),
                    115), "jpeg", new File(dirPath + "\\image" + count + "s.jpg"));
            ImageIO.write(Scalr.resize(ImageIO.read(f.getInputStream()),
                    230), "jpeg", new File(dirPath + "\\image"+count+"m.jpg"));
            ImageIO.write(Scalr.resize(ImageIO.read(f.getInputStream()),
                    370), "jpeg", new File(dirPath + "\\image"+count+"l.jpg"));
            os.write(f.getBytes());
        }catch (IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }
}