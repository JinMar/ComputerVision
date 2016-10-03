package cz.tul.controllers;

import cz.tul.config.ViewConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Marek on 21.07.2016.
 */
@Controller
@RequestMapping("/")

public class BasicController {
    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        logger.info("Index načten");
        return ViewConst.INDEX;
    }

    @RequestMapping(value = "/application", method = RequestMethod.GET)
    public String application(Model model) {

        logger.info("Aplikační stránka načtena");
        return ViewConst.APPLICATION;
    }
}
