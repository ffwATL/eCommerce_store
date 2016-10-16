package com.ffwatl.manage.controller.rest;


import com.ffwatl.manage.entities.items.Item;
import com.ffwatl.manage.presenters.items.update.ItemUpdatePresenter;
import com.ffwatl.service.items.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller, serves all the ajax requests that starts with '/manage/ajax/update' and have 'POST' type.
 */
@RestController
@RequestMapping(value = "/manage/ajax/update", method = RequestMethod.POST)
public class UpdateController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ItemService itemService;

    /**
     * Method for update information about one single Item;
     * @param update UpdatePresenter object that contains all the Item fields to change;
     * @return status 200 if everything is ok.
     */
    @RequestMapping(value = "/item/single", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> updateSingleItem(@RequestBody ItemUpdatePresenter update){
        try {
            itemService.updateSingleItem(update);
        }catch (Exception e){
            logger.error("Error on single Item update. " + update.getItem());
            logger.error(e.getMessage());
            throw e;
        }
        return ResponseEntity.ok("Ok");
    }

    /**
     * Method for update item status information;
     * @param item Item object with only 2 parameters - 'isActive' and 'id'.
     * @return status 200 if everything is ok.
     */
    @RequestMapping(value = "/item/status")
    @ResponseBody
    public ResponseEntity<String> changeItemStatus(@RequestBody Item item){
        try {
            itemService.changeItemStatus(item);
        }catch (Exception e){
            logger.error("Error while changing item status. id = "+item.getId()+", isActive = "+item.isActive());
            logger.error(e.getMessage());
            throw e;
        }
        return ResponseEntity.ok("Ok");
    }

    /**
     * Method for update information about more than one Item objects;
     * @param update UpdatePresenter object that contains all the Item's IDs and fields to change;
     * @return status 200 if everything is ok.
     */
    @RequestMapping(value = "/item/multi")
    @ResponseBody
    public ResponseEntity<String> updateItems(@RequestBody ItemUpdatePresenter update){
        try{
            itemService.updateItems(update);
        }catch (Exception e){
            logger.error("Error on multi items update. " + update);
            logger.error(e.getMessage());
            throw e;
        }
        return ResponseEntity.ok("ok");
    }

}