package com.scheta.task;

import com.scheta.configuration.TaskConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PeriodicJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(PeriodicJob.class);

    @Override
    public void lunch(Runnable task, TaskConfiguration configuration) {
        Objects.requireNonNull(configuration, "Configuration must be nons null");
        final var scheduledExecutorService = Executors.newScheduledThreadPool(1);
        final var time = configuration.getTime();
        final var timeUnit = configuration.getUnit();
        final boolean scheduled = time.isPresent();

        if (scheduled) {
            scheduledExecutorService.scheduleAtFixedRate(task, Job.delay(time.get()),
                    timeUnit.toSeconds(configuration.getPeriod()), TimeUnit.SECONDS);
            logger.info("Periodic task will be scheduled at: {}.", time.get());
        } else {
            scheduledExecutorService.scheduleAtFixedRate(task, 0,
                    timeUnit.toSeconds(configuration.getPeriod()), TimeUnit.SECONDS);
            logger.info("Periodic task scheduled now.");
        }
    }
}
