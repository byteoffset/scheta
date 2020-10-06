package com.github.okarmusk.scheta.task;

import com.github.okarmusk.scheta.configuration.TaskConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SingleTask implements Task {
    private static final Logger logger = LoggerFactory.getLogger(SingleTask.class);

    @Override
    public void lunch(Runnable runnable, TaskConfiguration configuration) {
        final var scheduledExecutorService = Executors.newScheduledThreadPool(1);

        if (configuration == null) {
            scheduleNow(scheduledExecutorService, runnable);
        } else {
            final var time = configuration.getTime();
            final boolean isScheduled = time.isPresent();

            if (isScheduled) {
                scheduledExecutorService.schedule(runnable, Task.delay(time.get()), configuration.getUnit());
                logger.info("Single task will be scheduled ad: {}", time.get());
            } else {
                scheduleNow(scheduledExecutorService, runnable);
            }
        }
    }

    private void scheduleNow(ScheduledExecutorService scheduledExecutorService, Runnable task) {
        scheduledExecutorService.execute(task);
        logger.info("Configuration not found, single task scheduled now.");
    }
}
