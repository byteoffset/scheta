package com.scheta.task;

import com.scheta.configuration.TaskConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SingleJobTest extends JobTest {
    private final SingleJob singleJob = new SingleJob();

    @Test
    @DisplayName("Should trigger single task now even if configuration is null")
    void triggerTaskNowEvenIfConfigurationIsNull() {
        singleJob.lunch(this::setScheduledToTrue, null);
        sleep(1000);

        assertEquals(true, scheduled.get());
    }

    @Test
    @DisplayName("Should trigger single task with delay")
    void triggerTaskWithDelay() {
        final var configuration = mock(TaskConfiguration.class);
        when(configuration.getUnit()).thenReturn(TimeUnit.SECONDS);
        when(configuration.getTime()).thenReturn(Optional.of(LocalTime.now().plusSeconds(2)));

        singleJob.lunch(this::setScheduledToTrue, configuration);

        sleep(4000);

        assertEquals(true, scheduled.get());
    }

    @Test
    @DisplayName("Should trigger single task with now")
    void triggerTaskNow() {
        final var configuration = mock(TaskConfiguration.class);
        when(configuration.getUnit()).thenReturn(TimeUnit.SECONDS);
        when(configuration.getTime()).thenReturn(Optional.empty());

        singleJob.lunch(this::setScheduledToTrue, configuration);

        sleep(1000);

        assertEquals(true, scheduled.get());
    }

    @AfterEach
    void reset() {
        scheduled.set(false);
    }
}