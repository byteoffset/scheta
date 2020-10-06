package com.github.okarmusk.scheta.task;

import com.github.okarmusk.scheta.configuration.TaskConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PeriodicTaskTest extends TaskTest {
    private final PeriodicTask periodicTask = new PeriodicTask();

    @Test
    @DisplayName("Should throw exception if configuration is null")
    void exceptionIfConfigurationIsNull() {
        final var exception = assertThrows(NullPointerException.class,
                () -> periodicTask.lunch(null, null));
        assertEquals("Configuration must be nons null", exception.getMessage());
    }

    @Test
    @DisplayName("Should trigger periodic task with delay")
    void triggerTaskWithDelay() {
        final var configuration = mock(TaskConfiguration.class);
        when(configuration.getPeriod()).thenReturn(1);
        when(configuration.getUnit()).thenReturn(TimeUnit.SECONDS);
        when(configuration.getTime()).thenReturn(Optional.of(LocalTime.now().plusSeconds(2)));

        periodicTask.lunch(this::setScheduledToTrue, configuration);

        sleep(4000);

        assertEquals(true, scheduled.get());
    }

    @Test
    @DisplayName("Should trigger periodic task now")
    void triggerTaskNow() {
        final var configuration = mock(TaskConfiguration.class);
        when(configuration.getPeriod()).thenReturn(1);
        when(configuration.getUnit()).thenReturn(TimeUnit.SECONDS);
        when(configuration.getTime()).thenReturn(Optional.empty());

        periodicTask.lunch(this::setScheduledToTrue, configuration);

        sleep(4000);

        assertEquals(true, scheduled.get());
    }

    @AfterEach
    void reset() {
        scheduled.set(false);
    }
}
