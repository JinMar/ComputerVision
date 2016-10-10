package cz.tul.services;

import cz.tul.controllers.transferObjects.AttributesDTO;
import cz.tul.controllers.transferObjects.MethodsDTO;
import cz.tul.entities.AttributeType;
import cz.tul.entities.Method;
import cz.tul.entities.MethodAttributes;
import cz.tul.repositories.MethodAttributesDAO;
import cz.tul.repositories.MethodDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 02.10.2016.
 */
@Service
@Transactional
public class ContentProviderService {
    @Autowired
    private MethodAttributesDAO methodAttributesDAO;
    @Autowired
    private MethodDAO methodDAO;

    private final String METHOD_WITHOUT_ATTRIBUTE = "-2";

    /**
     * Ziska vsechny metody
     *
     * @return List<MethodsDTO>
     */
    public List<MethodsDTO> getAllMethod() {
        return getWrappedMethod(methodDAO.getAllMethods());
    }

    /**
     * Ziska vechyn atributy metody
     *
     * @param id - identifikátor metody
     * @return
     */
    public List<AttributesDTO> getAtributesByMethodId(String id) {
        return getWrappedMethodAttributes(methodAttributesDAO.getMethodAttributesById(id));
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
                    default:
                        //// TODO: 09.10.2016 vyvolat vyjímku nějakou
                }
            }


            attributesDTOs.add(tmp);
        }

        return attributesDTOs;
    }


}
