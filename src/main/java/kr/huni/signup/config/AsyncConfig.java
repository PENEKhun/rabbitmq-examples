package kr.huni.signup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration for async task executors.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Creates an async task executor for event processing.
     * This executor is used for sending events to Kafka after the transaction commits.
     */
    @Bean(name = "EVENT_ASYNC_TASK_EXECUTOR")
    public Executor eventAsyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("EventAsync-");
        executor.initialize();
        return executor;
    }
}
