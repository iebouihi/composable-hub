package com.sqli.kafka.connect.sap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by yassire.
 */
class VersionUtil {
  private static final Logger log = LoggerFactory.getLogger(VersionUtil.class);
  public static String getVersion() {
    final Properties properties = new Properties();
    try {
      properties.load(VersionUtil.class.getClassLoader().getResourceAsStream("application.properties"));
    } catch (IOException e) {
      log.error("Unable to load Project version from application.properties.",e);
    }
    return properties.getProperty("project.version","0.0.1");
  }
}
