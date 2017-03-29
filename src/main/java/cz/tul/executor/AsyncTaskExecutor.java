package cz.tul.executor;

import cz.tul.entities.Chain;
import cz.tul.entities.StateEnum;
import cz.tul.repositories.ChainDAO;
import cz.tul.repositories.PartDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 12.10.2016.
 */
@Service
@EnableAsync
public class AsyncTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskExecutor.class);
    private final int maxTasks = 50;

    @Autowired
    PartDAO partDAO;

    @Autowired
    ThreadPoolTaskExecutor executor;

    @Autowired
    ChainDAO chainDAO;


    @Scheduled(fixedDelay = 4000)
    public void execute() {

        int activeTasks = executor.getActiveCount();

        if (activeTasks <= maxTasks) {
            List<Chain> chains = changeState(chainDAO.getLastActiveChains(maxTasks - activeTasks));
            logger.info("Coutnt of chain to processes: " + chains.size());
            if (chains.size() > 0) {
                logger.info("Count of task:" + chains.size());
                logger.info("Processing:" + chains.size());
                chainDAO.update(chains);
                for (Chain chain : chains) {

                    Task2 asyncTask = new Task2(chain, chainDAO, partDAO);

                    executor.execute(asyncTask);
                }
            }
        }


    }

    private List<Chain> changeState(List<Chain> chains) {

        List<Chain> result = new ArrayList<>();
        if (chains.size() > 0) {
            for (Chain chain : chains) {
                chain.setState(StateEnum.PROCESSING.getState());
                result.add(chain);
            }
            chainDAO.update(result);
        }
        return result;
    }

}
