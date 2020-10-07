package com.github.okarmusk.scheta;

import com.github.okarmusk.scheta.configuration.TaskConfiguration;
import com.github.okarmusk.scheta.task.PeriodicTask;
import com.github.okarmusk.scheta.task.SingleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Scheta {
    private final static Logger logger = LoggerFactory.getLogger(Scheta.class);
    private final SingleTask singleTask;
    private final PeriodicTask periodicTask;
    private static volatile Scheta instance;

    Scheta(SingleTask singleTask, PeriodicTask periodicTask) {
        this.singleTask = singleTask;
        this.periodicTask = periodicTask;
    }

    public static Scheta getInstance() {
        if (instance == null) {
            synchronized (Scheta.class) {
                if (instance == null) {
                    instance = new Scheta(new SingleTask(), new PeriodicTask());
                    logger.info("Scheta initialized.");

                    return instance;
                }
            }
        }

        return instance;
    }

    public void lunchPeriodicTask(Runnable runnable, TaskConfiguration configuration) {
        Objects.requireNonNull(configuration, "Configuration must be non null");
        periodicTask.lunch(runnable, configuration);
    }

    public void lunchSingleTask(Runnable runnable, TaskConfiguration configuration) {
        singleTask.lunch(runnable, configuration);
    }
}
