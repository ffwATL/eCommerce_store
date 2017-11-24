package com.ffwatl.admin.web.controller.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.admin.catalog.domain.BrandImpl;
import com.ffwatl.admin.catalog.domain.dto.response.ClothesOptionsPresenter;
import com.ffwatl.admin.catalog.service.BrandService;
import com.ffwatl.util.Settings;
import com.ffwatl.util.WebUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Rest controller, serves all the ajax requests that starts with '/admin/ajax/save' and have 'POST' type.
 */
@RestController
@RequestMapping(value = "/admin/ajax/save", method = RequestMethod.POST)
public class SaveController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private BrandService brandService;
    @Autowired
    private Settings settings; //contains url for images and directories to save images

    @RequestMapping(value = "/brand")
    @ResponseBody
    public ResponseEntity<ClothesOptionsPresenter> saveNewBrand(@RequestParam("file") MultipartFile file, @RequestParam String b){
        BrandImpl brand;
        try {
            brand = new ObjectMapper().readValue(b, BrandImpl.class);
        } catch (Exception e) {
            LOGGER.error("error on mapping: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        try {
            brandService.save(brand);
            saveBrandImg(settings.getBrandImgDir() + brand.getName().replace(" ", "_"), file);
        }catch (IOException e){
            LOGGER.error("Exception while saving brand img. {}", e.getMessage());
            LOGGER.debug("Removing 'BrandImpl' with id={}", brand.getId());
            brandService.removeById(brand.getId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }catch (Exception e){
            LOGGER.error("Exception while saving 'BrandImpl' into DB or something else: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ClothesOptionsPresenter presenter = new ClothesOptionsPresenter();
        presenter.setBrandImgUrl(settings.getBrandImgUrl());
        presenter.setBrandList(brandService.findAll());
        return ResponseEntity.ok(presenter);
    }

    private void saveBrandImg(String dirPath, MultipartFile file) throws IOException {
        WebUtil.createFolder(dirPath);
        ImageIO.write(Scalr.resize(ImageIO.read(file.getInputStream()),
                250), "jpeg", new File(dirPath + "\\logo.jpg"));
    }
}