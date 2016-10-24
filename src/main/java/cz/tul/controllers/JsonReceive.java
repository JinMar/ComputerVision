package cz.tul.controllers;

import cz.tul.controllers.transferObjects.*;
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

    @RequestMapping(value = "/createChain", method = RequestMethod.POST)
    public
    @ResponseBody
    Message receiveChain(@RequestBody final List<ChainDTO> chainDtos, HttpServletRequest request) {
        Message msg = new Message();
        msg.setChainId(contentProviderService.createWholeChain(chainDtos));
        msg.setMessage("OK");
        return msg;
    }

    @RequestMapping(value = "/getMethods", method = RequestMethod.POST)
    public
    @ResponseBody
    List<MethodsDTO> getMethods() {
        logger.info("getting methods");
        List<MethodsDTO> result = contentProviderService.getAllMethod();
        return result;
    }


    @RequestMapping(value = "/getAttributes", method = RequestMethod.POST)
    public
    @ResponseBody
    WrappedAtrDTO getAttributes(@RequestBody final List<RequestData> requestData) {
        String idMethod = requestData.get(0).getIdMethod();
        logger.info("getting atributes for method with id {}", idMethod);
        List<AttributesDTO> result = contentProviderService.getAtributesByMethodId(idMethod);
        WrappedAtrDTO wrappedAtrDTO = new WrappedAtrDTO(result, requestData.get(0).getPageAttributeId());

        return wrappedAtrDTO;
    }

    @RequestMapping(value = "/isChainReady", method = RequestMethod.POST)
    public
    @ResponseBody
    WrappedChainDTO isChainReady(@RequestBody final List<Message> requestData) {
        String chainId = requestData.get(0).getChainId();
        logger.info("Chain screening {}", chainId);
        WrappedChainDTO result = new WrappedChainDTO();

        if (contentProviderService.isChainReady(chainId)) {
            result.setMessage("Chain is ready !!!");
            result.setReady(true);
        } else {
            result.setMessage("Chain is not ready !!!");
            result.setReady(false);
        }


        return result;
    }


}
