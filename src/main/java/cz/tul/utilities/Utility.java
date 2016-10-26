package cz.tul.utilities;

import cz.tul.entities.Part;
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
}
