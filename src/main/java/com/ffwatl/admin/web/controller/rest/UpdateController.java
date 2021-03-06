package com.ffwatl.admin.web.controller.rest;


import com.ffwatl.admin.catalog.domain.ProductImpl;
import com.ffwatl.admin.catalog.domain.presenter.ItemUpdatePresenter;
import com.ffwatl.admin.catalog.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller, serves all the ajax requests that starts with '/admin/ajax/update' and have 'POST' type.
 */
@RestController
@RequestMapping(value = "/admin/ajax/update", method = RequestMethod.POST)
public class UpdateController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductService productService;

    /**
     * Method for update information about one single Product;
     * @param update UpdatePresenter object that contains all the Product fields to change;
     * @return status 200 if everything is ok.
     */
    @RequestMapping(value = "/item/single", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> updateSingleItem(@RequestBody ItemUpdatePresenter update){
        try {
            productService.updateSingleItem(update);
        }catch (Exception e){
            logger.error("Error on single Product update. " + update.getItem());
            logger.error(e.getMessage());
            throw e;
        }
        return ResponseEntity.ok("Ok");
    }

    /**
     * Method for update item status information;
     * @param item Product object with only 2 parameters - 'isActive' and 'id'.
     * @return status 200 if everything is ok.
     */
    @RequestMapping(value = "/item/status")
    @ResponseBody
    public ResponseEntity<String> changeItemStatus(@RequestBody ProductImpl item){
        try {
            productService.changeItemStatus(item);
        }catch (Exception e){
            logger.error("Error while changing item status. id = "+item.getId()+", isActive = "+item.isActive());
            logger.error(e.getMessage());
            throw e;
        }
        return ResponseEntity.ok("Ok");
    }

    /**
     * Method for update information about more than one Product objects;
     * @param update UpdatePresenter object that contains all the Product's IDs and fields to change;
     * @return status 200 if everything is ok.
     */
    @RequestMapping(value = "/item/multi")
    @ResponseBody
    public ResponseEntity<String> updateItems(@RequestBody ItemUpdatePresenter update){
        try{
            productService.updateItems(update);
        }catch (Exception e){
            logger.error("Error on multi items update. " + update);
            logger.error(e.getMessage());
            throw e;
        }
        return ResponseEntity.ok("ok");
    }

}