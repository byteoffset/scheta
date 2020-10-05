package com.scheta.configuraiton;

import com.scheta.configuration.TaskConfiguration;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskConfigurationTest {
    @Test
    @DisplayName("Should return string in json format")
    void verifyIfToStringIsInJSONFormat() {
        final var configuration = new TaskConfiguration();
        configuration.time = "23:59:59";
        configuration.unit = TaskConfiguration.Unit.HOURS;
        configuration.period = 1;
        final var json = configuration.toString();

        try {
            new JSONObject(json);
        } catch (JSONException exception) {
            fail("Unable to convert string to object");
        }

        assertTrue(true);
    }

    @Test
    @DisplayName("Should be able to convert string time to usable object")
    void convertStringTimeToUsableObject() {
        final var configuration = new TaskConfiguration();
        configuration.time = "23:59:59";
        configuration.unit = TaskConfiguration.Unit.HOURS;
        final var optional = configuration.getTime();

        if (optional.isEmpty()) {
            fail("Unable to convert string to time.");
        }

        final var time = optional.get();

        assertEquals(23, time.getHour());
    }
}
