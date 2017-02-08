package cz.tul.controllers;

import cz.tul.controllers.transferObjects.*;
import cz.tul.services.ChainValidator;
import cz.tul.services.ContentProviderService;
import cz.tul.services.exceptions.ImageNotFoundException;
import cz.tul.utilities.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private ChainValidator chainValidator;

    @RequestMapping(value = "/createChain", method = RequestMethod.POST)
    public
    @ResponseBody
    Message receiveChain(@RequestBody final List<ChainDTO> chainDtos) {
        Message msg = new Message();
        boolean isChainValid = false;
        try {
            isChainValid = chainValidator.validateChain(chainDtos);
            if (isChainValid) {
                msg.setChainId(contentProviderService.createWholeChain(chainDtos));
                msg.setState(true);
                msg.setMessage("Byl vytvořen požadavek na zpracování  vložených dat");
            } else {
                msg.setState(false);
                msg.setMessage("Data nemohou byt zpracována z důvodu nekompaktibilních kroků");
            }
        } catch (ImageNotFoundException e) {
            msg.setState(false);
            msg.setMessage(e.getMessage());
        }
        return msg;
    }

    @RequestMapping(value = "/getFunctions", method = RequestMethod.POST)
    public
    @ResponseBody
    List<ListDataDTO> getFunctions() {
        logger.info("Getting Functions");
        List<ListDataDTO> result = contentProviderService.getAllFunctions();
        return result;
    }

    @RequestMapping(value = "/getMethod", method = RequestMethod.POST)
    public
    @ResponseBody
    List<ListDataDTO> getMethod(@RequestBody final List<RequestData> requestData) {
        String functionId = requestData.get(0).getObjectId();
        logger.info("Getting method for id: " + functionId);
        List<ListDataDTO> result = contentProviderService.getAllMethodByFunctionId(functionId);
        return result;
    }


    @RequestMapping(value = "/getOperation", method = RequestMethod.POST)
    public
    @ResponseBody
    List<ListDataDTO> getOperation(@RequestBody final List<RequestData> requestData) {
        String methodId = requestData.get(0).getObjectId();
        String functionId = requestData.get(0).getObjectId2();
        logger.info("Getting operation for id: " + methodId);
        List<ListDataDTO> result = contentProviderService.getOperationByMethodId(methodId);
        return result;
    }

    @RequestMapping(value = "/getAttributes", method = RequestMethod.POST)
    public
    @ResponseBody
    WrappedAtrDTO getAttributes(@RequestBody final List<RequestData> requestData) {
        String idOperation = requestData.get(0).getObjectId();
        logger.info("getting atributes for operation with id {}", idOperation);
        List<AttributesDTO> result = contentProviderService.getAttributesByOperationId(idOperation);
        WrappedAtrDTO wrappedAtrDTO = new WrappedAtrDTO(Utility.getSortAttributesDTO(result), requestData.get(0).getPageAttributeId());

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
            result.setParts(contentProviderService.getCompletedParts(chainId));
            result.setReady(true);
        } else {
            result.setMessage("Chain is not ready !!!");
            result.setParts(contentProviderService.getCompletedParts(chainId));
            result.setReady(false);
        }


        return result;
    }


}
