package cz.tul.executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by Marek on 16.08.2016.
 */
@Configuration
@EnableScheduling
@EnableAsync
public class AppConfig implements AsyncConfigurer {

    private ThreadPoolTaskExecutor executor;


    @Bean
    public ThreadPoolTaskExecutor getExecutor() {

        return (ThreadPoolTaskExecutor) getAsyncExecutor();
    }

    @Override
    public Executor getAsyncExecutor() {
        if (executor == null) {
            executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(25);
            executor.setMaxPoolSize(50);
            executor.setQueueCapacity(100);
            executor.setThreadNamePrefix("TaskExecutor-");
            executor.initialize();
        }
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }


}