package com.scheta.task;

import com.scheta.configuration.TaskConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SingleJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(SingleJob.class);

    @Override
    public void lunch(Runnable task, TaskConfiguration configuration) {
        final var scheduledExecutorService = Executors.newScheduledThreadPool(1);

        if (configuration == null) {
            scheduleNow(scheduledExecutorService, task);
        } else {
            final var time = configuration.getTime();
            final boolean isScheduled = time.isPresent();

            if (isScheduled) {
                scheduledExecutorService.schedule(task, Job.delay(time.get()), configuration.getUnit());
                logger.info("Single task will be scheduled ad: {}", time.get());
            } else {
                scheduleNow(scheduledExecutorService, task);
            }
        }
    }

    private void scheduleNow(ScheduledExecutorService scheduledExecutorService, Runnable task) {
        scheduledExecutorService.execute(task);
        logger.info("Configuration not found, single task scheduled now.");
    }
}
