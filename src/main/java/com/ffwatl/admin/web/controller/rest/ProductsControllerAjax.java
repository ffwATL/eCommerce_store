package com.ffwatl.admin.web.controller.rest;

import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.dto.request.CatalogRequest;
import com.ffwatl.admin.catalog.domain.dto.response.ControllerResponse;
import com.ffwatl.admin.catalog.domain.dto.response.PageResponse;
import com.ffwatl.admin.catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author ffw_ATL.
 */
@RestController
public class ProductsControllerAjax {

    @Autowired
    private ProductService productService;




    @RequestMapping(value = "/api/public/products", method = RequestMethod.GET)
    public ResponseEntity<PageResponse<Product>> get(@RequestParam CatalogRequest request) {
        return ResponseEntity.ok().body(new PageResponse<>());
    }

    @RequestMapping(value = "/api/public/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<ControllerResponse<Product>> get(@PathVariable("id") long id) {
        // getting a product by given id
        return ResponseEntity.ok(new ControllerResponse<>());
    }



    @RequestMapping(value = "/api/public/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ControllerResponse<String>> put(@PathVariable("id") long id, @RequestParam Product product) {
        // update product.. delegate it to the service
        return ResponseEntity.ok(new ControllerResponse<>());
    }

    @RequestMapping(value = "/api/private/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ControllerResponse> delete(@PathVariable("id") long id) {
        try {
            productService.removeById(id);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerResponse()
                    .setException(e)
                    .setMessage(e.getMessage()));
        }
        return ResponseEntity.ok(ControllerResponse.OK);

    }
}
