package com.sqli.kafka.connect.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SapSourceConnector extends SourceConnector {
  private static final Logger log = LoggerFactory.getLogger(SapSourceConnector.class);
  private Map<String, String> configProperties;

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public void start(Map<String, String> map) {
    log.info("Starting up SAP Source connector");
    try {
      configProperties = setupSourcePropertiesWithDefaultsIfMissing(map);
    } catch (ConfigException e) {
      throw new ConnectException("Couldn't start SapSourceConnector due to configuration error", e);
    }
  }

  private Map<String, String> setupSourcePropertiesWithDefaultsIfMissing(Map<String, String> props) throws ConfigException {
    return new SapSourceConnectorConfig(props).returnPropertiesWithDefaultsValuesIfMissing();
  }

  @Override
  public Class<? extends Task> taskClass() {
    return SapSourceTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    if (maxTasks != 1) {
      log.info("Ignoring maxTasks as there can only be one.");
    }
    List<Map<String, String>> configs = new ArrayList<>(maxTasks);
    Map<String, String> taskConfig = new HashMap<>();
    taskConfig.putAll(configProperties);
    configs.add(taskConfig);
    return configs;
  }

  @Override
  public void stop() {
    //TODO: Do things that are necessary to stop your connector.
  }

  @Override
  public ConfigDef config() {
    return SapSourceConnectorConfig.conf();
  }
}
