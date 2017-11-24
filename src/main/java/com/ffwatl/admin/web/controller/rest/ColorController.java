package com.ffwatl.admin.web.controller.rest;

import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.dto.response.ControllerResponse;
import com.ffwatl.admin.catalog.service.ColorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mmed 11/16/17
 */
@RestController
public class ColorController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "color_service")
    private ColorService colorService;

    @RequestMapping(name = "/api/public/colors", method = RequestMethod.GET)
    public ResponseEntity<List<Color>> get(@RequestParam boolean onlyUsed) {
        List<Color> colorList;

        if (onlyUsed) {
            colorList = colorService.findAllUsed();
        }else {
            colorList = colorService.findAll();
        }
        LOGGER.debug("get --> url='/api/public/colors'\n{}", colorList);

        return ResponseEntity.ok(colorList);
    }


    @RequestMapping(name = "/api/private/colors/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ControllerResponse> delete(@PathVariable long id) {
        LOGGER.debug("delete --> url='/api/private/colors/{}'", id);

        try {
            colorService.removeById(id);
        } catch (Exception e) {
            LOGGER.error("delete --> url='/api/private/colors/{}', exception: {}", id, e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ControllerResponse()
                    .setException(e)
                    .setMessage("Can't remove 'Color' entity by id="+id));
        }

        return ResponseEntity.ok(ControllerResponse.OK);
    }
}
