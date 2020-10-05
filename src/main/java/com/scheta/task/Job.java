package com.scheta.task;

import com.scheta.configuration.TaskConfiguration;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface Job {
    void lunch(Runnable task, TaskConfiguration configuration);

    static long delay(LocalTime time) {
        final var now = ZonedDateTime.now(ZoneId.systemDefault());
        var nextRun = now.withHour(time.getHour())
                .withMinute(time.getMinute()).withSecond(time.getSecond());

        if(now.compareTo(nextRun) > 0) {
            nextRun = nextRun.plusDays(1);
        }

        final var duration = Duration.between(now, nextRun);
        return duration.getSeconds();
    }
}
