package com.github.okarmusk.scheta.task;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.fail;

public class TaskTest {
    final AtomicBoolean scheduled = new AtomicBoolean(false);

    void setScheduledToTrue() {
        scheduled.set(true);
    }

    void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            fail("Cannot wait till task will be executed.");
        }
    }
}
