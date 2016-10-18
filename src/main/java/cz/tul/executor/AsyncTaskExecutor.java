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

    @Autowired
    Task asyncTask;

    @Autowired
    PartDAO partDAO;

    @Autowired
    ThreadPoolTaskExecutor executor;

    @Autowired
    ChainDAO chainDAO;

    @Scheduled(fixedDelay = 2000)
    public void execute() {

        int temp = executor.getMaxPoolSize() - executor.getActiveCount() - 1;

        logger.info("pool: " + temp);
        if (temp > 0) {
            List<Chain> chains = changeState(chainDAO.getLastActiveChains(temp));

            if (chains.size() > 0) {
                logger.info("Count of task:" + chains.size());
                chainDAO.update(chains);
                try {
                    for (Chain chain : chains) {
                        asyncTask.execute(chain, chainDAO, partDAO);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private List<Chain> changeState(List<Chain> chains) {
        List<Chain> result = new ArrayList<>();
        for (Chain chain : chains) {
            chain.setState(StateEnum.PROCESSING);
            result.add(chain);
        }
        return result;
    }

}
