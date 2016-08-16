package cz.tul.controllers;

import cz.tul.config.view.ViewConst;
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
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        System.out.println("index");
        return ViewConst.INDEX;
    }

    @RequestMapping(value = "/application", method = RequestMethod.GET)
    public String application(Model model) {

        System.out.println("index");
        return ViewConst.APPLICATION;
    }
}
