package com.sqli.kafka.connect.sap;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.HashMap;
import java.util.Map;


public class SapSourceConnectorConfig extends AbstractConfig {

  public static final String BASE_URL_CONFIG = "sap.base.url";
  private static final String BASE_URL_DOC = "Base URL to SAP API";

  public static final String USERNAME_CONFIG = "sap.username";
  private static final String USERNAME_DOC = "The username to use with the SAP API.";

  public static final String PASSWORD_CONFIG = "sap.password";
  private static final String PASSWORD_DOC = "The password to use with the SAP API.";

  public static final String TOPIC_CONFIG = "sap.topic";
  private static final String TOPIC_DEFAULT = "sap-raw";
  private static final String TOPIC_DOC = "Topic to publish SAP events data to.";

  public static final String API_VERSION_CONFIG = "sap.api.version";
  private static final String API_VERSION_DEFAULT = "v1";
  private static final String API_VERSION_DOC = "SAP Api version to use.";

  public static final String BATCH_SIZE = "sap.batch.size";
  private static final int BATCH_SIZE_DEFAULT = 100;
  private static final String BATCH_SIZE_DOC = "Window of data to pull from SAP API.";

  public static final String POLL_INTERVAL = "sap.poll.interval";
  private static final int POLL_INTERVAL_DEFAULT = 5000;
  private static final String POLL_INTERVAL_DOC = "Poll interval in milliseconds.";

  public static final int MAX_BATCH_SIZE = 10_000;
  public static final int MIN_BATCH_SIZE = 2;

  public SapSourceConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
  }

  public SapSourceConnectorConfig(Map<String, String> parsedConfig) {
    this(conf(), parsedConfig);
  }

  public static ConfigDef conf() {
    return new ConfigDef()
            .define(BASE_URL_CONFIG, ConfigDef.Type.STRING, ConfigDef.NO_DEFAULT_VALUE, new ConfigDef.NonEmptyStringWithoutControlChars(), ConfigDef.Importance.HIGH, BASE_URL_DOC)
            .define(TOPIC_CONFIG, ConfigDef.Type.STRING, TOPIC_DEFAULT, new ConfigDef.NonEmptyStringWithoutControlChars(), ConfigDef.Importance.HIGH, TOPIC_DOC)
            .define(API_VERSION_CONFIG, ConfigDef.Type.STRING, API_VERSION_DEFAULT, new ConfigDef.NonEmptyStringWithoutControlChars(), ConfigDef.Importance.HIGH, API_VERSION_DOC)
            .define(BATCH_SIZE, ConfigDef.Type.INT, BATCH_SIZE_DEFAULT, ConfigDef.Range.between(MIN_BATCH_SIZE, MAX_BATCH_SIZE), ConfigDef.Importance.LOW, BATCH_SIZE_DOC)
            .define(POLL_INTERVAL, ConfigDef.Type.INT, POLL_INTERVAL_DEFAULT
                    , ConfigDef.Importance.LOW, POLL_INTERVAL_DOC)
            .define(USERNAME_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, USERNAME_DOC)
            .define(PASSWORD_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, PASSWORD_DOC);
  }

  Map<String, String> returnPropertiesWithDefaultsValuesIfMissing() {
    Map<String, ?> uncastProperties = this.values();
    Map<String, String> config = new HashMap<>(uncastProperties.size());
    uncastProperties.forEach((key, valueToBeCast) -> config.put(key, valueToBeCast.toString()));

    return config;
  }
}
