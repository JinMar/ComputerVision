package cz.tul.executor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;


/**
 * Created by Marek on 16.08.2016.
 */

public class Task {
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    @Async
    public void execute(int text) throws InterruptedException {
        logger.info(" Execute chain with id: " + text);
        //   + Thread.currentThread().getName());
        Thread.sleep(3000 * text);

    }
}
