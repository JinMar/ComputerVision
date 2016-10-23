package cz.tul.controllers;

import cz.tul.bussiness.register.MethodRegister;
import cz.tul.config.ViewConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Marek on 21.07.2016.
 */
@Controller
@RequestMapping("/")
public class BasicController {
    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView view = new ModelAndView(ViewConst.INDEX);
        logger.info("Index načten");
        return view;
    }

    @RequestMapping(value = "/application", method = RequestMethod.GET)
    public String application(Model model) {

        logger.info("Aplikační stránka načtena");
        return ViewConst.APPLICATION;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(Model model) {
        MethodRegister methodRegister = MethodRegister.getInstance();

        logger.info("test bezi");

    }
}
