package com.ffwatl.admin.web.controller.rest;

import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.request.CatalogRequest;
import com.ffwatl.admin.catalog.domain.response.Catalog;
import com.ffwatl.admin.catalog.domain.response.ControllerResponse;
import com.ffwatl.admin.catalog.domain.response.ControllerResponseImpl;
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
    public ResponseEntity<Catalog> get(@RequestParam CatalogRequest request) {
        return ResponseEntity.ok().body(null);
    }

    @RequestMapping(value = "/api/public/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> get(@PathVariable("id") long id) {
        return ResponseEntity.ok("cool");
    }



    @RequestMapping(value = "/api/public/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> put(@PathVariable("id") long id, @RequestParam Product product){
        return ResponseEntity.ok("/api/public/products/ 200 OK");
    }

    @RequestMapping(value = "/api/private/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ControllerResponse> delete(@PathVariable("id") long id) {
        try {
            productService.removeById(id);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerResponseImpl()
                    .setException(e)
                    .setMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ControllerResponseImpl()
                .setMessage("OK!"));

    }
}
