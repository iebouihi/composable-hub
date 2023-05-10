package com.sqli.kafka.connect.sap;

import org.apache.kafka.common.config.ConfigException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.sqli.kafka.connect.sap.SapSourceConnectorConfig.*;
import static org.junit.jupiter.api.Assertions.*;

public class SapSourceConnectorConfigTest {
  public static final String EMPTY_STRING = "";

  @Test
  public void emptyConfig() {
    Map<String, String> props = Collections.emptyMap();
    ConfigException exception = assertThrows(ConfigException.class, () -> new SapSourceConnectorConfig(props));

    assertEquals("Missing required configuration \"sap.base.url\" which has no default value.", exception.getMessage());
  }

  @Test
  public void minimalConfig() {
    Map<String, String> props = new HashMap<>();
    props.put(BASE_URL_CONFIG, "https://localhost:443");
    props.put(USERNAME_CONFIG, "placeholder_username");
    props.put(PASSWORD_CONFIG, "placeholder_password");
    assertDoesNotThrow(() -> {
      new SapSourceConnectorConfig(props);
    });
  }

  @Test
  public void noUrl() {
    Map<String, String> props = new HashMap<>();
    props.put(USERNAME_CONFIG, "placeholder_username");
    props.put(PASSWORD_CONFIG, "placeholder_password");
    ConfigException exception = assertThrows(ConfigException.class, () -> {
      new SapSourceConnectorConfig(props);
    });

    assertEquals("Missing required configuration \"sap.base.url\" which has no default value.", exception.getMessage());
  }

  @Test
  public void noUsername() {
    Map<String, String> props = new HashMap<>();
    props.put(BASE_URL_CONFIG, "https://localhost:443");
    props.put(PASSWORD_CONFIG, "placeholder_password");
    ConfigException exception = assertThrows(ConfigException.class, () -> {
      new SapSourceConnectorConfig(props);
    });

    assertEquals("Missing required configuration \"sap.username\" which has no default value.", exception.getMessage());
  }

  @Test
  public void noPassword() {
    Map<String, String> props = new HashMap<>();
    props.put(BASE_URL_CONFIG, "https://localhost:443");
    props.put(USERNAME_CONFIG, "placeholder_username");
    ConfigException exception = assertThrows(ConfigException.class, () -> {
      new SapSourceConnectorConfig(props);
    });

    assertEquals("Missing required configuration \"sap.password\" which has no default value.", exception.getMessage());
  }

  @Test
  public void invalidUrl() {
    Map<String, String> props = new HashMap<>();
    props.put(BASE_URL_CONFIG, EMPTY_STRING);
    props.put(USERNAME_CONFIG, "placeholder_username");
    props.put(PASSWORD_CONFIG, "placeholder_password");
    ConfigException exception = assertThrows(ConfigException.class, () -> {
      new SapSourceConnectorConfig(props);
    });

    assertEquals("Invalid value  for configuration sap.base.url: String may not be empty", exception.getMessage());
  }

  @Test
  public void invalidTopic() {
    Map<String, String> props = new HashMap<>();
    props.put(BASE_URL_CONFIG, "https://localhost:443");
    props.put(USERNAME_CONFIG, "placeholder_username");
    props.put(PASSWORD_CONFIG, "placeholder_password");
    props.put(TOPIC_CONFIG, EMPTY_STRING);
    ConfigException exception = assertThrows(ConfigException.class, () -> {
      new SapSourceConnectorConfig(props);
    });

    assertEquals("Invalid value  for configuration sap.topic: String may not be empty", exception.getMessage());
  }

}