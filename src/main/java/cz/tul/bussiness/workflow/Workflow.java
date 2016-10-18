package cz.tul.bussiness.workflow;

import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import cz.tul.bussiness.workflow.exceptions.NoDataFound;
import cz.tul.entities.Chain;
import cz.tul.entities.Part;
import cz.tul.entities.StateEnum;
import cz.tul.repositories.ChainDAO;
import cz.tul.repositories.PartDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by Bc. Marek Jindrák on 13.10.2016.
 */

public class Workflow {
    private static final Logger logger = LoggerFactory.getLogger(Workflow.class);
    private ChainDAO chainDAO;
    private PartDAO partDAO;

    private Chain chain;

    private List<Part> sortedParts;


    public Workflow(Chain chin, ChainDAO chainDAO, PartDAO partDAO) {
        this.chain = chin;
        this.chainDAO = chainDAO;
        this.partDAO = partDAO;
        sortedParts = getSortPart(chain.getChainParts());
        startWorkFlow();
        //  finishWorkflow();
    }


    private void startWorkFlow() {
        BufferedImage data = null;
        boolean firstStep = true;
        for (Part part : sortedParts) {
            try {
                data = processStep(data, part, firstStep);
            } catch (NoDataFound noDataFound) {
                noDataFound.printStackTrace();
            } catch (SelectionLayerException e) {
                e.printStackTrace();
            }
            firstStep = false;

        }

    }

    private BufferedImage processStep(BufferedImage data, Part part, boolean firstStep) throws NoDataFound, SelectionLayerException {
        WorkflowStep processedStep = new WorkflowStep(data, part, firstStep);
        return processedStep.getData();

    }

    private void finishWorkflow() {
        boolean hasError = false;
        boolean finalState = true;
        for (Part part : sortedParts) {
            if (part.getState() == StateEnum.ERROR) {
                hasError = true;
            } else if (part.getState() == StateEnum.PROCESSING || part.getState() == StateEnum.ACTIVE) {
                finalState = false;
            }
            if (!hasError && finalState) {
                partDAO.update(sortedParts);
                chain.setState(StateEnum.ACTIVE);
                chainDAO.save(chain);
            }
        }
    }


    private List<Part> getSortPart(Set<Part> chainParts) {
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
