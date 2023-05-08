package com.sqli.composablehub.kafka.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EnableAutoConfiguration
public class RetryConfigTest extends RetryConfigData {

    @Test
    void readInitialIntervalMs() {
        assertEquals(1000, this.getInitialIntervalMs());
    }

    @Test
    void readMaxAttempts() {
        assertEquals(3, this.getMaxAttempts());
    }

    @Test
    void readMaxIntervalMs() {
        assertEquals(10000, this.getMaxIntervalMs());
    }

    @Test
    void readMultiplier() {
        assertEquals(2.0, this.getMultiplier());
    }
}
