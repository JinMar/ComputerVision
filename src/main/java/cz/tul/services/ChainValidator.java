package cz.tul.services;

import cz.tul.controllers.transferObjects.ChainDTO;
import cz.tul.controllers.transferObjects.MethodAttributeDTO;
import cz.tul.repositories.AllowStepDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 30.10.2016.
 */
@Service
@Transactional
public class ChainValidator {
    private static final Logger logger = LoggerFactory.getLogger(ChainValidator.class);

    @Autowired
    private AllowStepDAO allowStepDAO;

    public boolean validateChain(List<ChainDTO> chainDtos) {
        String prew = null;

        List<String> allowedMethod = null;
        List<String> attr;
        boolean allowed = true;
        for (ChainDTO data : chainDtos) {
            if (prew == null) {
                prew = data.getMethodId();
            } else {
                attr = new ArrayList<>();
                for (MethodAttributeDTO val : data.getAttributes()) {
                    attr.add(val.getValue());
                }
                allowedMethod = allowStepDAO.getAllowedMethod(data.getMethodId(), attr);

                if (!allowedMethod.contains(prew)) {
                    return !allowed;
                }
                prew = data.getMethodId();
            }

        }
        return allowed;
    }
}
