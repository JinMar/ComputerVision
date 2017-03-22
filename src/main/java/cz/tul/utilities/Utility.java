package cz.tul.utilities;

import cz.tul.controllers.transferObjects.AllowStepDTO;
import cz.tul.controllers.transferObjects.AttributesDTO;
import cz.tul.entities.Part;
import cz.tul.entities.PartAttributeValue;
import cz.tul.entities.StateEnum;
import cz.tul.repositories.PartDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Bc. Marek Jindrák on 26.10.2016.
 */
public class Utility {
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    public static List<Part> getSortPart(Set<Part> chainParts, PartDAO partDAO) {
        List<Part> result = getSortPart(chainParts);


        for (Part part : result) {
            part.setState(StateEnum.PROCESSING.getState());
        }
        partDAO.update(result);
        return result;
    }

    public static List<Part> getSortPart(Set<Part> chainParts) {
        List<Part> result = new ArrayList(chainParts);

        Collections.sort(result, new Comparator<Part>() {
            @Override
            public int compare(Part p1, Part p2) {
                if (p1.getPosition() > p2.getPosition())
                    return 1;
                if (p1.getPosition() < p2.getPosition())
                    return -1;
                return 0;
            }
        });
        return result;
    }

    public static List<AllowStepDTO> getSortSteps(List<AllowStepDTO> chainParts) {
        List<AllowStepDTO> result = new ArrayList(chainParts);

        Collections.sort(result, new Comparator<AllowStepDTO>() {
            @Override
            public int compare(AllowStepDTO p1, AllowStepDTO p2) {
                return p1.getMethodName().compareTo(p2.getMethodName());
            }
        });
        return result;
    }

    public static List<AttributesDTO> getSortAttributesDTO(List<AttributesDTO> input) {
        List<AttributesDTO> result = new ArrayList(input);

        Collections.sort(result, new Comparator<AttributesDTO>() {
            @Override
            public int compare(AttributesDTO p1, AttributesDTO p2) {

                return p2.getName().compareTo(p1.getName());


            }
        });
        return result;
    }


    public static byte[] getImageByte(String base64) {
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(base64);
    }

    public static List<PartAttributeValue> getSortPartAttributeValue(Set<PartAttributeValue> atributes) {
        List<PartAttributeValue> result = new ArrayList(atributes);

        Collections.sort(result, new Comparator<PartAttributeValue>() {
            public int compare(PartAttributeValue o1, PartAttributeValue o2) {
                return o2.getOperationAttributes().getAttribute().getName().compareTo(o1.getOperationAttributes().getAttribute().getName());
            }
        });
        return result;
    }
}