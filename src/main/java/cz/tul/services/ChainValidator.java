package cz.tul.services;

import cz.tul.controllers.transferObjects.ChainDTO;
import cz.tul.entities.AllowStep;
import cz.tul.entities.Operation;
import cz.tul.repositories.OperationDAO;
import cz.tul.services.exceptions.ImageNotFoundException;
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
    private OperationDAO operationDAO;

    /**
     * @param chainDtos
     * @return Vráti hodnotu true když je možné řetěz vyhodnotit.
     * @throws ImageNotFoundException - Vyvoláno když nejseou načtena data vstupního obrázku
     */

    public boolean validateChain(List<ChainDTO> chainDtos) throws ImageNotFoundException {
        String prew = null;

        Operation operation = null;
        List<String> allowedOp;
        boolean allowed = true;
        for (ChainDTO data : chainDtos) {
            if (prew == null) {
                prew = operationDAO.getIdOperationByName("Original");
                if (data.getAttributes().size() == 0) {
                    logger.warn("Chain can not be created because input data not contains image data");
                    throw new ImageNotFoundException("Chain can not be created because input data not contains image data");
                }
            } else {
                allowedOp = new ArrayList<>();
                operation = operationDAO.getOperationById(data.getOperationId());
                for (AllowStep as : operation.getAllowSteps()) {
                    allowedOp.add(as.getAllowoperationId().getOperationId());
                }
                if (!allowedOp.contains(prew)) {
                    return !allowed;
                }
                prew = data.getOperationId();
            }

        }
        return allowed;
    }
}
