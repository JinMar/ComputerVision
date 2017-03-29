package cz.tul.executor;

import cz.tul.bussiness.workflow.Workflow;
import cz.tul.entities.Chain;
import cz.tul.repositories.ChainDAO;
import cz.tul.repositories.PartDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Bc. Marek Jindrák on 22.03.2017.
 */
public class Task2 implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Task2.class);
    private Chain chin;
    private ChainDAO chainDAO;
    private PartDAO partDAO;

    public Task2(Chain chin, ChainDAO chainDAO, PartDAO partDAO) {
        this.chin = chin;
        this.chainDAO = chainDAO;
        this.partDAO = partDAO;
    }

    @Override
    public void run() {
        logger.info("Start executing:" + new Date());
        Workflow workflow = new Workflow(chin, chainDAO, partDAO);

        logger.info("End executing:" + new Date());
    }
}
