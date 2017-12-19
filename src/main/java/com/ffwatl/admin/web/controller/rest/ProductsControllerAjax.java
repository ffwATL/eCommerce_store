package com.ffwatl.admin.web.controller.rest;

import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.dto.ProductDTO;
import com.ffwatl.admin.catalog.domain.dto.request.CatalogRequest;
import com.ffwatl.admin.catalog.domain.dto.response.AccessMode;
import com.ffwatl.admin.catalog.domain.dto.response.ControllerResponse;
import com.ffwatl.admin.catalog.domain.dto.response.PageResponse;
import com.ffwatl.admin.catalog.service.ProductService;
import com.ffwatl.common.persistence.FetchMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
@RestController
public class ProductsControllerAjax {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "product_service")
    private ProductService productService;




    @RequestMapping(value = "/api/public/products", method = RequestMethod.GET)
    public ResponseEntity<PageResponse<Product>> get(@RequestParam CatalogRequest request) {

        return ResponseEntity.ok().body(new PageResponse<>());
    }

    @RequestMapping(value = "/api/public/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<ControllerResponse<Product>> get(@PathVariable("id") long id) {
        LOGGER.trace("GET: /api/public/products/{}", id);
        Product product = productService.findById(id, FetchMode.DEFAULT, AccessMode.CUSTOMER);

        // getting a product by given id
        return ResponseEntity.ok(new ControllerResponse<Product>().setData(product));
    }



    @RequestMapping(value = "/api/public/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ControllerResponse<String>> put(@PathVariable("id") long id, @RequestParam ProductDTO product) {
        long requestedId = product.getId();

        if (requestedId != id) {
            LOGGER.error("PUT: /api/public/products/{}, url and requested id={} doesn't match!", id, requestedId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerResponse<String>()
                    .setMessage("url and requested id doesn't match!"));
        }
        LOGGER.trace("PUT: /api/public/products/{}, {}", id, product);
        productService.processSingleProductRequest(product);
        // update product.. delegate it to the service
        return ResponseEntity.ok(new ControllerResponse<String>().setMessage("OK!"));
    }

    @RequestMapping(value = "/api/private/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ControllerResponse> delete(@PathVariable("id") long id) {
        LOGGER.trace("DELETE: /api/private/products/{}", id);
        try {
            productService.removeById(id);
        }catch (Exception e) {
            LOGGER.error("DELETE: /api/private/products/{}, {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerResponse()
                    .setException(e)
                    .setMessage(e.getMessage()));
        }
        LOGGER.trace("DELETE: /api/private/products/{}, [200]", id);
        return ResponseEntity.ok(ControllerResponse.OK);

    }


    @RequestMapping(value = "/api/private/products", method = RequestMethod.POST)
    public ResponseEntity<ControllerResponse<Long>> create(@RequestParam ProductDTO product) {
        LOGGER.info("POST: /api/private/products, {}", product);
        long id = -1;

        ControllerResponse<Long> response = new ControllerResponse<>();
        try {
            id = productService.save(product).getId();
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("POST: /api/private/products, {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.setData(id).setException(e));
        }

        LOGGER.info("POST: /api/private/products, [200] created a new product with id = {}", id);
        return ResponseEntity.ok(response.setData(id));
    }
}
