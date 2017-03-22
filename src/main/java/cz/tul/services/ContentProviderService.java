package cz.tul.services;

import cz.tul.bussiness.register.OperationRegister;
import cz.tul.bussiness.register.exceptions.IllegalInputException;
import cz.tul.controllers.transferObjects.*;
import cz.tul.entities.*;
import cz.tul.repositories.*;
import cz.tul.utilities.Utility;
import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Marek on 02.10.2016.
 */
@Service
@Transactional
public class ContentProviderService {
    private static final Logger logger = LoggerFactory.getLogger(ContentProviderService.class);
    private final String METHOD_WITHOUT_ATTRIBUTE = "-2";

    @Autowired
    AllowStepDAO allowStepDAO;
    @Autowired
    private MethodDAO methodDAO;
    @Autowired
    private AttributeDAO attributeDAO;
    @Autowired
    private ChainDAO chainDAO;
    @Autowired
    private PartDAO partDAO;
    @Autowired
    private FunctionDAO functionDAO;
    @Autowired
    private OperationDAO operationDAO;
    @Autowired
    private OperationAttributesDAO operationAttributesDAO;
    @Autowired
    private PartAttributeValueDAO partAttributeValueDAO;

    public ContentProviderService() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Ziska vsechny metody
     *
     * @return List<ListDataDTO>
     */
    public List<ListDataDTO> getAllFunctions() {
        return getWrappedFunctions(functionDAO.getAllFunctions());
    }

    /**
     * Ziska vsechny metody
     *
     * @param functionId
     * @return List<ListDataDTO>
     */
    public List<ListDataDTO> getAllMethodByFunctionId(String functionId) {
        return getWrappedMethods(methodDAO.getMethodsById(functionId));
    }

    private List<ListDataDTO> getWrappedMethods(List<Method> methods) {
        List<ListDataDTO> listDataDTOs = new ArrayList<>();

        for (Method method : methods) {
            ListDataDTO tmp = new ListDataDTO();
            tmp.setName(method.getName());
            tmp.setIdMethod(method.getMethodId());
            listDataDTOs.add(tmp);
        }
        return listDataDTOs;
    }

    public List<ListDataDTO> getOperationByMethodId(String methodId) {
        return getWrappedOperations(operationDAO.getOperationsById(methodId));
    }

    private List<ListDataDTO> getWrappedOperations(List<Operation> operationsByIds) {
        List<ListDataDTO> listDataDTOs = new ArrayList<>();

        for (Operation operation : operationsByIds) {
            ListDataDTO tmp = new ListDataDTO();
            tmp.setName(operation.getName());
            tmp.setIdMethod(operation.getOperationId());
            listDataDTOs.add(tmp);
        }

        return listDataDTOs;
    }

    public List<AttributesDTO> getAttributesByOperationId(String idOperation) {
        List<AttributesDTO> result = getWrappedMethodAttributes(operationAttributesDAO.getAttributesById(idOperation));
        return result;
    }

    /**
     * Ziska vsechny atributy metody
     *
     * @param id - identifikátor metody
     * @return
     *//*
    public List<AttributesDTO> getAtributesByMethodId(String id) {
        return getWrappedMethodAttributes(methodAttributesDAO.getMethodAttributesByMethodId(id));
    }
*/

    /**
     * @param functions - List<Function> list  zdrojovych entit
     * @return - Metoda vraci set metod, které jsou owrepovane, tak aby se nepracovalo se zakladni entitou.
     * Pracovat se zdrojovou entitou by se melo jen v nejtutnejsich pripadech.
     */
    private List<ListDataDTO> getWrappedFunctions(List<Function> functions) {
        List<ListDataDTO> listDataDTOs = new ArrayList<>();

        for (Function function : functions) {
            ListDataDTO tmp = new ListDataDTO();
            tmp.setName(function.getName());
            tmp.setIdMethod(function.getFunctionId());
            listDataDTOs.add(tmp);
        }

        return listDataDTOs;
    }

    /**
     * @param methodAttributes
     * @return
     */
    private List<AttributesDTO> getWrappedMethodAttributes(List<OperationAttributes> methodAttributes) {
        List<AttributesDTO> attributesDTOs = new ArrayList<>();
        for (OperationAttributes attributes : methodAttributes) {
            AttributesDTO tmp = new AttributesDTO();
            if (attributes.getAttribute() == null) {
                tmp.setName("");
                tmp.setDefaultValues(METHOD_WITHOUT_ATTRIBUTE);
            } else {
                tmp.setName(attributes.getAttribute().getName());
                tmp.setDefaultValues(attributes.getDefaultValues());
                tmp.setOperationAttributesId(attributes.getOperationAttributesId());
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
                    case IMAGE:
                        tmp.setAttributeType(AttributeType.IMAGE);
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
     * Vrátí podporované kroky
     */
    public List<AllowStepsDTO> getAllowSteps() {
        List<AllowStep> as = allowStepDAO.getAllAllowedSteps();
        List<AllowStepsDTO> result = new ArrayList<>();
        AllowStepDTO asd = null;

        List<AllowStepDTO> asds = new ArrayList<>();
        String fName = "", mName = "", oName = "";
        String fNameFollow = "", mNameFollow = "", oNameFollow = "";
        for (AllowStep a : as) {
            if (a != null) {

                fName = a.getAllowoperationId().getMethod().getFunction().getName();
                mName = a.getAllowoperationId().getMethod().getName();
                oName = a.getAllowoperationId().getName();
                if (asd == null) {
                    asd = new AllowStepDTO(fName, mName, oName);
                    fNameFollow = a.getOperation().getMethod().getFunction().getName();
                    mNameFollow = a.getOperation().getMethod().getName();
                    oNameFollow = a.getOperation().getName();
                    asds.add(new AllowStepDTO(fNameFollow, mNameFollow, oNameFollow));
                    continue;
                }
                if (asd.getFunctionName().equals(fName) && asd.getMethodName().equals(mName) && asd.getOperationName().equals(oName)) {
                    fNameFollow = a.getOperation().getMethod().getFunction().getName();
                    mNameFollow = a.getOperation().getMethod().getName();
                    oNameFollow = a.getOperation().getName();
                    asds.add(new AllowStepDTO(fNameFollow, mNameFollow, oNameFollow));
                } else {
                    asds = Utility.getSortSteps(asds);
                    result.add(new AllowStepsDTO(asd, asds));
                    asd = new AllowStepDTO(fName, mName, oName);
                    fNameFollow = a.getOperation().getMethod().getFunction().getName();
                    mNameFollow = a.getOperation().getMethod().getName();
                    oNameFollow = a.getOperation().getName();
                    asds = new ArrayList<>();
                    asds.add(new AllowStepDTO(fNameFollow, mNameFollow, oNameFollow));

                }

            }
        }


        return result;
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
        boolean firstItem = true;
        for (ChainDTO data : chainDtos) {
            Part part = new Part();
            part.setChain(chain);
            part.setPosition(data.getPosition());
            part.setState(StateEnum.ACTIVE.getState());
            logger.info(data.getOperationId());
            if (firstItem) {
                OperationRegister or = OperationRegister.getInstance();

                try {
                    part.setOperation(operationDAO.getOperationById(or.getOriginal()));
                } catch (IllegalInputException e) {
                    e.printStackTrace();
                }
                firstItem = false;
            } else {
                part.setOperation(operationDAO.getOperationById(data.getOperationId()));
            }
            part.setMethodId(data.getMethodId());
            part.setFunctionId(data.getFunctionId());
            partDAO.save(part);
            for (OperationAttributeDTO mAttribute : data.getAttributes()) {
                PartAttributeValue partAttributeValue = new PartAttributeValue();
                partAttributeValue.setValue(mAttribute.getValue());
                partAttributeValue.setOperationAttributes(operationAttributesDAO.getAttributById(mAttribute.getOperationAttributeId()));
                partAttributeValue.setPart(part);
                partAttributeValueDAO.save(partAttributeValue);
            }

            logger.debug(data.toString());
        }
        return chain.getChainId();
    }

    public ChainInfoDTO isChainReady(String chainId) {
        ChainInfoDTO result = chainDAO.isChainCompleted(chainId);
        logger.info("Chain is ready: " + result);
        return result;
    }


    public List<PartData> getCompletedParts(String chainId) {
        List<PartData> result = new ArrayList<>();
        Chain chain = chainDAO.getChainById(chainId);
        List<Part> sortedParts = Utility.getSortPart(chain.getChainParts());


        for (Part part : sortedParts) {
            if (part.getPosition() != 0) {
                PartData partData = new PartData();
                partData.setPosition(part.getPosition());
                partData.setURL(part.getUrl());
                partData.setmURL(part.getMagnitudeUrl());
                partData.sethURL(part.getHistogramUrl());
                partData.setOperationId(part.getOperation().getOperationId());
                partData.setMethodId(part.getMethodId());
                partData.setFunctionId(part.getFunctionId());
                partData.setMethods(getAllMethodByFunctionId(part.getFunctionId()));
                partData.setOperations(getOperationByMethodId(part.getMethodId()));
                List<PartValue> partValueList = new ArrayList<>();
                Set<PartAttributeValue> partSet = part.getPartAttributeValues();
                List<PartAttributeValue> list = Utility.getSortPartAttributeValue(partSet);

                for (PartAttributeValue pv : list) {
                    PartValue partValue = new PartValue();


                    partValue.setValue(pv.getValue());
                    partValue.setOperationAttributesId(pv.getOperationAttributes().getOperationAttributesId());
                    partValue.setOptions(pv.getOperationAttributes().getOptions());
                    partValue.setType(pv.getOperationAttributes().getAttributeType());
                    partValue.setMax(pv.getOperationAttributes().getMaxValue());
                    partValue.setName(pv.getOperationAttributes().getAttribute().getName());
                    partValue.setMin(pv.getOperationAttributes().getMinValue());
                    partValueList.add(partValue);


                }
                partData.setPartValueList(partValueList);

                result.add(partData);
            }

        }
        return result;
    }


    //SETTERS


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

    public void setAllowStepDAO(AllowStepDAO allowStepDAO) {
        this.allowStepDAO = allowStepDAO;
    }

    public void setFunctionDAO(FunctionDAO functionDAO) {
        this.functionDAO = functionDAO;
    }

    public void setOperationDAO(OperationDAO operationDAO) {
        this.operationDAO = operationDAO;
    }

    public void setOperationAttributesDAO(OperationAttributesDAO operationAttributesDAO) {
        this.operationAttributesDAO = operationAttributesDAO;
    }
    //GETTERS


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

    public AllowStepDAO getAllowStepDAO() {
        return allowStepDAO;
    }

    public FunctionDAO getFunctionDAO() {
        return functionDAO;
    }

    public OperationDAO getOperationDAO() {
        return operationDAO;
    }

    public OperationAttributesDAO getOperationAttributesDAO() {
        return operationAttributesDAO;
    }
}
