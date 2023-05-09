package com.sqli.kafka.connect.sap;

import org.junit.Test;

public class SapSourceConnectorConfigTest {
  @Test
  public void doc() {
    System.out.println(SapSourceConnectorConfig.conf().toRst());
  }
}