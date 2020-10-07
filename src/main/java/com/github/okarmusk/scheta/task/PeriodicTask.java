package com.github.okarmusk.scheta.task;

import com.github.okarmusk.scheta.configuration.TaskConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PeriodicTask implements Task {
    private static final Logger logger = LoggerFactory.getLogger(PeriodicTask.class);

    @Override
    public void lunch(Runnable runnable, TaskConfiguration configuration) {
        final var scheduledExecutorService = Executors.newScheduledThreadPool(1);
        final var time = configuration.getTime();
        final var timeUnit = configuration.getUnit();
        final boolean scheduled = time.isPresent();

        if (scheduled) {
            scheduledExecutorService.scheduleAtFixedRate(runnable, Task.delay(time.get()),
                    timeUnit.toSeconds(configuration.getPeriod()), TimeUnit.SECONDS);
            logger.info("Periodic task will be scheduled at: {}.", time.get());
        } else {
            scheduledExecutorService.scheduleAtFixedRate(runnable, 0,
                    timeUnit.toSeconds(configuration.getPeriod()), TimeUnit.SECONDS);
            logger.info("Periodic task scheduled now.");
        }
    }
}
