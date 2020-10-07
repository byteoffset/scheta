package com.github.okarmusk.scheta;

import com.github.okarmusk.scheta.configuration.TaskConfiguration;
import com.github.okarmusk.scheta.task.PeriodicTask;
import com.github.okarmusk.scheta.task.SingleTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SchetaTest {
    private final Runnable runnable = () -> {};

    @Test
    @DisplayName("Should lunch single task once")
    public void lunchSingleTaskOnce() {
        final var singleTask = spy(new SingleTask());
        final var periodicTask = mock(PeriodicTask.class);

        final var scheta = new Scheta(singleTask, periodicTask);
        scheta.lunchSingleTask(runnable, null);
        verify(singleTask, times(1)).lunch(runnable, null);
    }

    @Test
    @DisplayName("Should lunch periodic task once")
    public void lunchPeriodicTaskOnce() {
        final var singleTask = mock(SingleTask.class);
        final var periodicTask = spy(new PeriodicTask());
        final var configuration = mock(TaskConfiguration.class);
        when(configuration.getPeriod()).thenReturn(1);
        when(configuration.getUnit()).thenReturn(TimeUnit.SECONDS);
        when(configuration.getTime()).thenReturn(Optional.of(LocalTime.now().plusSeconds(2)));

        final var scheta = new Scheta(singleTask, periodicTask);
        scheta.lunchPeriodicTask(runnable, configuration);
        verify(periodicTask, times(1)).lunch(runnable, configuration);
    }

    @Test
    @DisplayName("Should throw exception if configuration for periodic task is null")
    void exceptionIfConfigurationIsNull() {
        final var singleTask = mock(SingleTask.class);
        final var periodicTask = mock(PeriodicTask.class);
        final var scheta = new Scheta(singleTask, periodicTask);

        final var exception = assertThrows(NullPointerException.class,
                () -> scheta.lunchPeriodicTask(null, null));
        assertEquals("Configuration must be non null", exception.getMessage());
    }
}
