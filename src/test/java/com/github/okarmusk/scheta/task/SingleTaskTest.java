package com.github.okarmusk.scheta.task;

import com.github.okarmusk.scheta.configuration.TaskConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SingleTaskTest extends TaskTest {
    private final SingleTask singleTask = new SingleTask();

    @Test
    @DisplayName("Should lunch single task now even if configuration is null")
    void lunchTaskNowEvenIfConfigurationIsNull() {
        singleTask.lunch(this::setScheduledToTrue, null);
        sleep(1000);

        assertEquals(true, scheduled.get());
    }

    @Test
    @DisplayName("Should lunch single task with delay")
    void lunchTaskWithDelay() {
        final var configuration = mock(TaskConfiguration.class);
        when(configuration.getUnit()).thenReturn(TimeUnit.SECONDS);
        when(configuration.getTime()).thenReturn(Optional.of(LocalTime.now().plusSeconds(2)));

        singleTask.lunch(this::setScheduledToTrue, configuration);

        sleep(4000);

        assertEquals(true, scheduled.get());
    }

    @Test
    @DisplayName("Should lunch single task with now")
    void lunchTaskNow() {
        final var configuration = mock(TaskConfiguration.class);
        when(configuration.getUnit()).thenReturn(TimeUnit.SECONDS);
        when(configuration.getTime()).thenReturn(Optional.empty());

        singleTask.lunch(this::setScheduledToTrue, configuration);

        sleep(1000);

        assertEquals(true, scheduled.get());
    }

    @AfterEach
    void reset() {
        scheduled.set(false);
    }
}