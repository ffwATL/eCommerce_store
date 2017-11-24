package com.ffwatl.admin.web.controller.rest;

import com.ffwatl.admin.catalog.domain.Brand;
import com.ffwatl.admin.catalog.domain.dto.response.ControllerResponse;
import com.ffwatl.admin.catalog.service.BrandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mmed 11/15/17
 */
@RestController
public class BrandController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private BrandService brandService;

    @RequestMapping(name = "/api/public/brands", method = RequestMethod.GET)
    public ResponseEntity<List<Brand>> get(@RequestParam boolean onlyUsed){
        List<Brand> brandList;

        if (onlyUsed) {
            brandList = brandService.findAllUsed();
        }else {
            brandList = brandService.findAll();
        }
        LOGGER.debug("get request for url: /api/public/brands, onlyUsed={}\n{}", onlyUsed, brandList);
        return ResponseEntity.ok(brandList);
    }

    @RequestMapping(name = "/api/public/brands", method = RequestMethod.PUT)
    public ResponseEntity<ControllerResponse> put(@RequestParam Brand brand) {
        LOGGER.debug("put request for url: /api/public/brands\n{}", brand);
        try {
            brandService.save(brand);
        }catch (Exception e) {
            LOGGER.error("put request for url: /api/public/brands, exception:{}", e.toString());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ControllerResponse()
                            .setException(e)
                            .setMessage(e.getMessage()));
        }

        return ResponseEntity.ok(ControllerResponse.OK);
    }


    @RequestMapping(name = "/api/public/brands/{id}", method = RequestMethod.GET)
    public ResponseEntity<Brand> get(@PathVariable long id) {
        Brand result = brandService.findById(id);

        LOGGER.trace("get request for url: /api/public/brands/{}\n{}", id, result);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(name = "/api/public/brands/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ControllerResponse> delete(@PathVariable long id) {
        LOGGER.debug("delete request for url: /api/public/brands/{}", id);

        try {
            brandService.removeById(id);
        }catch (Exception e) {
            LOGGER.error("delete request for url: /api/public/brands/{}\nexception: {}", id, e.toString());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ControllerResponse()
                            .setException(e)
                            .setMessage("Can't remove 'Brand' object with id="+id));
        }
        return ResponseEntity.ok(ControllerResponse.OK);
    }
}