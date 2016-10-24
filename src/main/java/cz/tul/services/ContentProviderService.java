package cz.tul.services;

import cz.tul.controllers.transferObjects.AttributesDTO;
import cz.tul.controllers.transferObjects.ChainDTO;
import cz.tul.controllers.transferObjects.MethodAttributeDTO;
import cz.tul.controllers.transferObjects.MethodsDTO;
import cz.tul.entities.*;
import cz.tul.repositories.*;
import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marek on 02.10.2016.
 */
@Service
@Transactional
public class ContentProviderService {
    private static final Logger logger = LoggerFactory.getLogger(ContentProviderService.class);
    private final String METHOD_WITHOUT_ATTRIBUTE = "-2";
    @Autowired
    private MethodAttributesDAO methodAttributesDAO;
    @Autowired
    private MethodDAO methodDAO;
    @Autowired
    private AttributeDAO attributeDAO;
    @Autowired
    private ChainDAO chainDAO;
    @Autowired
    private PartDAO partDAO;
    @Autowired
    private PartAttributeValueDAO partAttributeValueDAO;

    public ContentProviderService() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Ziska vsechny metody
     *
     * @return List<MethodsDTO>
     */
    public List<MethodsDTO> getAllMethod() {
        return getWrappedMethod(methodDAO.getAllMethods());
    }

    /**
     * Ziska vsechny atributy metody
     *
     * @param id - identifikátor metody
     * @return
     */
    public List<AttributesDTO> getAtributesByMethodId(String id) {
        return getWrappedMethodAttributes(methodAttributesDAO.getMethodAttributesByMethodId(id));
    }

    /**
     * @param methods - List<Method> set  zdrojovych entit
     * @return - Metoda vraci set metod, které jsou owrepovane, tak aby se nepracovalo se zakladni entitou.
     * Pracovat se zdrojovou entitou by se melo jen v nejtutnejsich pripadech.
     */
    private List<MethodsDTO> getWrappedMethod(List<Method> methods) {
        List<MethodsDTO> methodsDTOs = new ArrayList<>();

        for (Method method : methods) {
            MethodsDTO tmp = new MethodsDTO();
            tmp.setName(method.getName());
            tmp.setIdMethod(method.getMethodId());
            methodsDTOs.add(tmp);
        }

        return methodsDTOs;
    }

    /**
     * @param methodAttributes
     * @return
     */
    private List<AttributesDTO> getWrappedMethodAttributes(List<MethodAttributes> methodAttributes) {
        List<AttributesDTO> attributesDTOs = new ArrayList<>();
        for (MethodAttributes attributes : methodAttributes) {
            AttributesDTO tmp = new AttributesDTO();
            if (attributes.getAttribute() == null) {
                tmp.setName("");
                tmp.setDefaultValues(METHOD_WITHOUT_ATTRIBUTE);
            } else {
                tmp.setName(attributes.getAttribute().getName());
                tmp.setDefaultValues(attributes.getDefaultValues());
                tmp.setMethodAttributesId(attributes.getMethodAttributesId());
                switch (attributes.getAttributeType()) {
                    case NUMBER:
                        tmp.setAttributeType(AttributeType.NUMBER);
                        tmp.setMaxValue(attributes.getMaxValue());
                        tmp.setMinValue(attributes.getMinValue());

                        break;
                    case SELECT:
                        tmp.setAttributeType(AttributeType.SELECT);
                        tmp.setOptions(attributes.getOptions());
                        break;
                    case TEXT:
                        tmp.setAttributeType(AttributeType.TEXT);
                        break;
                    default:
                        //// TODO: 09.10.2016 vyvolat vyjímku nějakou
                }
            }


            attributesDTOs.add(tmp);
        }

        return attributesDTOs;
    }

    /**
     * Vytváří samotný řetěz i jeho části
     *
     * @param chainDtos List<ChainDTO>
     * @return Vraci informaci o tom zda se povedlo ulozi retez ci nikoliv
     */
    public String createWholeChain(List<ChainDTO> chainDtos) {
        Chain chain = new Chain();
        chain.setCreateDate(new Date());
        chain.setState(StateEnum.ACTIVE.getState());
        chainDAO.save(chain);
        for (ChainDTO data : chainDtos) {
            Part part = new Part();
            part.setChain(chain);
            part.setPosition(data.getPosition());
            part.setState(StateEnum.ACTIVE.getState());
            logger.info(data.getMethodId());
            part.setMethod(methodDAO.getMethodById(data.getMethodId()));
            partDAO.save(part);
            for (MethodAttributeDTO mAttribute : data.getAttributes()) {
                PartAttributeValue partAttributeValue = new PartAttributeValue();
                partAttributeValue.setValue(mAttribute.getValue());
                partAttributeValue.setMethodAttributes(
                        methodAttributesDAO.getMethodAttributesById(mAttribute.getMethodAttributeId()));
                partAttributeValue.setPart(part);
                partAttributeValueDAO.save(partAttributeValue);
            }

            logger.debug(data.toString());
        }
        return chain.getChainId();
    }

    public boolean isChainReady(String idChain) {
        boolean result = chainDAO.isChainCompleted(idChain);
        logger.info("Chain is ready: " + result);
        return result;
    }


    //SETTERS

    public void setMethodAttributesDAO(MethodAttributesDAO methodAttributesDAO) {
        this.methodAttributesDAO = methodAttributesDAO;
    }

    public void setMethodDAO(MethodDAO methodDAO) {
        this.methodDAO = methodDAO;
    }

    public void setAttributeDAO(AttributeDAO attributeDAO) {
        this.attributeDAO = attributeDAO;
    }

    public void setChainDAO(ChainDAO chainDAO) {
        this.chainDAO = chainDAO;
    }

    public void setPartDAO(PartDAO partDAO) {
        this.partDAO = partDAO;
    }

    public void setPartAttributeValueDAO(PartAttributeValueDAO partAttributeValueDAO) {
        this.partAttributeValueDAO = partAttributeValueDAO;
    }

    //GETTERS


    public MethodAttributesDAO getMethodAttributesDAO() {
        return methodAttributesDAO;
    }

    public MethodDAO getMethodDAO() {
        return methodDAO;
    }

    public AttributeDAO getAttributeDAO() {
        return attributeDAO;
    }

    public ChainDAO getChainDAO() {
        return chainDAO;
    }

    public PartDAO getPartDAO() {
        return partDAO;
    }

    public PartAttributeValueDAO getPartAttributeValueDAO() {
        return partAttributeValueDAO;
    }
}
