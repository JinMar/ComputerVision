package cz.tul.executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by Marek on 16.08.2016.
 */
@Configuration
@EnableAsync
public class AppConfig implements AsyncConfigurer {

    private ThreadPoolTaskExecutor executor;

    @Bean
    public Task asyncTask() {

        return new Task();
    }

    @Bean
    public ThreadPoolTaskExecutor getExecutor() {

        return (ThreadPoolTaskExecutor) getAsyncExecutor();
    }

    @Override
    public Executor getAsyncExecutor() {
        if (executor == null) {
            executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(7);
            executor.setMaxPoolSize(42);
            executor.setQueueCapacity(11);
            executor.setThreadNamePrefix("MyExecutor-");
            executor.initialize();
        }
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }


}