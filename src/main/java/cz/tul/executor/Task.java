package cz.tul.executor;


import cz.tul.bussiness.workflow.Workflow;
import cz.tul.entities.Chain;
import cz.tul.repositories.ChainDAO;
import cz.tul.repositories.PartDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;


/**
 * Created by Marek on 16.08.2016.
 */

public class Task {
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    @Async
    public void execute(Chain chin, ChainDAO chainDAO, PartDAO partDAO) throws InterruptedException {
        logger.info("Start executing:" + new Date());
        Workflow workflow = new Workflow(chin, chainDAO, partDAO);

        logger.info("End executing:" + new Date());
    }
}
