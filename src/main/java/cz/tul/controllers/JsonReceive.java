package cz.tul.controllers;

import cz.tul.controllers.transferObjects.DTO;
import cz.tul.services.ContentProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Marek on 02.10.2016.
 */
@Controller
@RequestMapping("/rest")
public class JsonReceive {
    private static final Logger logger = LoggerFactory.getLogger(JsonReceive.class);
    @Autowired
    private ContentProviderService contentProviderService;

    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    public
    @ResponseBody
    String receiveChain(@RequestBody final List<DTO> dtos, HttpServletRequest request) {
        System.out.println("Velikost " + dtos.size());
        for (DTO dto : dtos) {
            logger.info(dto.toString());
        }

        return "aaa";
    }
/*
    @RequestMapping(value = "/getMethods", method = RequestMethod.POST)
    public
    @ResponseBody
    Set<MethodDTO> getMethods() {
        System.out.println("getmethod");
        contentProviderService.getAllMethod();
        return "aaa";
    }
*/

}
