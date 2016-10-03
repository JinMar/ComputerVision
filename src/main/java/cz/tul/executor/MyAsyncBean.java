package cz.tul.executor;

import org.springframework.scheduling.annotation.Async;

/**
 * Created by Marek on 16.08.2016.
 */
public class MyAsyncBean {
    @Async
    public void asyn(int text) throws InterruptedException {
        System.out.println(text + " Execute method asynchronously. "
                + Thread.currentThread().getName());
        Thread.sleep(3000 * text);

    }
}
