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
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/getFunctions", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ListDataDTO> getFunctions() {
        logger.info("Getting Functions");
        List<ListDataDTO> result = contentProviderService.getAllFunctions();
        return result;
    }

    @RequestMapping(value = "/getMethod/{methodId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ListDataDTO> getMethod(@PathVariable("methodId") String methodId) {

        logger.info("Getting method for id: " + methodId);
        List<ListDataDTO> result = contentProviderService.getAllMethodByFunctionId(methodId);
        return result;
    }


    @RequestMapping(value = "/getOperation/{methodId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ListDataDTO> getOperation(@PathVariable("methodId") String methodId) {

        logger.info("Getting operation for id: " + methodId);
        List<ListDataDTO> result = contentProviderService.getOperationByMethodId(methodId);
        return result;
    }

    @RequestMapping(value = "/getAttributes/{operationId}/{pageAttributeId}", method = RequestMethod.GET)
    public
    @ResponseBody
    WrappedAtrDTO getAttributes(@PathVariable("operationId") String operationId, @PathVariable("pageAttributeId") String pageAttributeId) {

        logger.info("getting atributes for operation with id {}", operationId);
        List<AttributesDTO> result = contentProviderService.getAttributesByOperationId(operationId);
        WrappedAtrDTO wrappedAtrDTO = new WrappedAtrDTO(Utility.getSortAttributesDTO(result), pageAttributeId);

        return wrappedAtrDTO;
    }

    @RequestMapping(value = "/isChainReady/{chainId}", method = RequestMethod.GET)
    public
    @ResponseBody
    WrappedChainDTO isChainReady(@PathVariable("chainId") String chainId) {

        logger.info("Chain screening {}", chainId);
        WrappedChainDTO result = new WrappedChainDTO();
        ChainInfoDTO info = contentProviderService.isChainReady(chainId);
        switch (info.getState()) {
            case COMPLETE:
                result.setMessage("Chain is ready !!!");
                result.setParts(contentProviderService.getCompletedParts(chainId));
                result.setReady(true);
                result.setError(false);
                break;
            case ACTIVE:
            case PROCESSING:
                result.setMessage("Chain is not ready !!!");
                result.setParts(contentProviderService.getCompletedParts(chainId));
                result.setReady(false);
                result.setError(false);
                break;
            case ERROR:
                result.setMessage("Failed -" + info.getMessage());
                result.setParts(contentProviderService.getCompletedParts(chainId));
                result.setReady(true);
                result.setError(true);
        }

        return result;
    }


}
