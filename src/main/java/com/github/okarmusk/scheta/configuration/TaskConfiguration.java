package com.github.okarmusk.scheta.configuration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class TaskConfiguration {
    public String time;
    public Unit unit;
    public int period;

    public Optional<LocalTime> getTime() {
        return Optional.of(LocalTime.parse(time));
    }

    public TimeUnit getUnit() {
        return unit.get();
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public enum Unit {
        SECONDS(TimeUnit.SECONDS),
        MINUTES(TimeUnit.MINUTES),
        HOURS(TimeUnit.HOURS);

        Unit(TimeUnit unit) {
            this.unit = unit;
        }

        private final TimeUnit unit;

        public TimeUnit get() {
            return unit;
        }
    }
}
