package cz.tul.bussiness.workflow;

import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import cz.tul.bussiness.workflow.exceptions.NoDataFound;
import cz.tul.entities.Chain;
import cz.tul.entities.Part;
import cz.tul.entities.StateEnum;
import cz.tul.repositories.ChainDAO;
import cz.tul.repositories.PartDAO;
import cz.tul.utilities.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.List;

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
        sortedParts = Utility.getSortPart(chain.getChainParts(), partDAO);
        startWorkFlow();
        finishWorkflow();
    }


    private void startWorkFlow() {
        logger.info("Workflow strated");
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
        WorkflowStep processedStep = new WorkflowStep(data, part, firstStep, partDAO);
        BufferedImage result = processedStep.getData();
        if (result != null) {

            part.setState(StateEnum.COMPLETE.getState());
        } else {
            if (firstStep) {
                part.setState(StateEnum.COMPLETE.getState());
            } else {
                part.setState(StateEnum.ERROR.getState());
            }
        }
        partDAO.update(part);
        return result;

    }

    private void finishWorkflow() {
        switch (chainDAO.isChainState(chain.getChainId())) {
            case ACTIVE:
                logger.error("Some steps have been not processed ---> part in state " + StateEnum.ACTIVE.getState());
                break;
            case PROCESSING:
                logger.error("Some steps have been not processed ---> part in state " + StateEnum.PROCESSING.getState());
            case ERROR:
                chain.setState(StateEnum.ERROR.getState());
                logger.error("Error occurred");
            case COMPLETE:
                logger.info("Chain with id: " + chain.getChainId() + " is completed");
                chain.setState(StateEnum.COMPLETE.getState());
        }


        chainDAO.update(chain);

    }




}
