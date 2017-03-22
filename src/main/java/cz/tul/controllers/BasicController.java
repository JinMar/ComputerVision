package cz.tul.controllers;

import cz.tul.bussiness.register.OperationRegister;
import cz.tul.config.ViewConst;
import cz.tul.controllers.transferObjects.AllowStepsDTO;
import cz.tul.services.ContentProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Marek on 21.07.2016.
 */
@Controller
@RequestMapping("/")
public class BasicController {
    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);
    @Autowired
    private ContentProviderService contentProviderService;


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

    @RequestMapping(value = "/workAssignment", method = RequestMethod.GET)
    public String workAssignment(Model model) {

        logger.info("Stránka se zadáním byla načtena");
        return ViewConst.WORKASSIGMENT;
    }

    @RequestMapping(value = "/devdoc", method = RequestMethod.GET)
    public String devDoc(Model model) {
        List<AllowStepsDTO> as = contentProviderService.getAllowSteps();
        model.addAttribute("allowSteps", as);
        logger.info("Stránka se zadáním byla načtena");
        return ViewConst.DEVDOC;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(Model model) {
        OperationRegister operationRegister = OperationRegister.getInstance();
        logger.info("test bezi");
    }


}
