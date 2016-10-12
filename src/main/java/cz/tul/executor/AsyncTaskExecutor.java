package cz.tul.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    ThreadPoolTaskExecutor executor;

    @Scheduled(fixedDelay = 2000)
    public void execute() {
        logger.info("Start executing:" + new Date());
        logger.info("Vyuzitych" + executor.getActiveCount());
        logger.info("vse" + executor.getMaxPoolSize());
        int temp = executor.getMaxPoolSize() - executor.getActiveCount() - 1;

        if (temp > 0) {
            try {
                for (int i = 0; i < temp; i++) {
                    asyncTask.execute(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        logger.info("End executing:" + new Date());
    }
}
