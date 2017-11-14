package com.ffwatl.admin.web.controller.rest;

import com.ffwatl.admin.catalog.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author ffw_ATL.
 */
@RestController
public class ProductsControllerAjax {

    @RequestMapping(value = "/api/public/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> get(@PathVariable("id") long id) {
        return ResponseEntity.ok("cool");
    }



    @RequestMapping(value = "/api/public/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> put(@PathVariable("id") long id, @RequestParam Product product){
        return ResponseEntity.ok("/api/public/products/ 200 OK");
    }
}
