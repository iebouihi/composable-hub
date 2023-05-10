package com.sqli.kafka.connect.sap;

import com.google.gson.Gson;
import com.sqli.kafka.connect.sap.api.client.SapClient;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SapSourceTask extends SourceTask {
  static final Logger log = LoggerFactory.getLogger(SapSourceTask.class);
  public static final String URL = "url";
  public static final String LAST_READ = "last_read";
  public static final String LAST_API_OFFSET = "last_api_offset";
  public static final String DEFAULT_FROM_TIME = "1970-01-01T00:00:00";
  private String fromDate = DEFAULT_FROM_TIME;
  private String baseUrl;
  private String topic;
  private String batchSize;
  private long apiOffset;
  private Long interval;
  private String apiVersion;
  private String username;
  private String password;
  private Long last_execution = 0L;

  static boolean isNotNullOrBlank(String str) {
    return str != null && !str.trim().isEmpty();
  }

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public void start(Map<String, String> map) {
    setupTaskConfig(map);

    log.debug("Trying to get persistedMap.");
    Map<String, Object> persistedMap = null;
    if (context != null && context.offsetStorageReader() != null) {
      persistedMap = context.offsetStorageReader().offset(Collections.singletonMap(URL, baseUrl));
    }
    log.info("The persistedMap is {}", persistedMap);
    if (persistedMap != null) {
      String lastRead = (String) persistedMap.get(LAST_READ);
      if (isNotNullOrBlank(lastRead)) {
        fromDate = lastRead;
      }

      Object lastApiOffset = persistedMap.get(LAST_API_OFFSET);
      if (lastApiOffset != null) {
        apiOffset = (Long) lastApiOffset;
      }
    }
  }

  private void setupTaskConfig(Map<String, String> props) {
    baseUrl = props.get(SapSourceConnectorConfig.BASE_URL_CONFIG);
    topic = props.get(SapSourceConnectorConfig.TOPIC_CONFIG);
    batchSize = props.get(SapSourceConnectorConfig.BATCH_SIZE);
    interval = Long.parseLong(props.get(SapSourceConnectorConfig.POLL_INTERVAL));

    apiVersion = props.get(SapSourceConnectorConfig.API_VERSION_CONFIG);
    username = props.get(SapSourceConnectorConfig.USERNAME_CONFIG);
    password = props.get(SapSourceConnectorConfig.PASSWORD_CONFIG);
  }

  @Override
  public List<SourceRecord> poll()  {
    if (System.currentTimeMillis() > (last_execution + interval)) {
      last_execution = System.currentTimeMillis();
      return getSapProductsAsSourceRecords();
    }
    return Collections.emptyList();
  }

  private List<SourceRecord> getSapProductsAsSourceRecords() {

    List<Map> jsonProducts = getSapProducts(fromDate);
    ArrayList<SourceRecord> records = new ArrayList<>();
    for (Map<String, Object> sapProduct : jsonProducts) {

      DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      String newFromDate = df.format(Calendar.getInstance().getTime());
      apiOffset = calculateApiOffset(apiOffset, newFromDate, fromDate);
      fromDate = newFromDate;

      log.info("The fromDate is now {}.", fromDate);
      records.add(buildSourceRecord(sapProduct, fromDate, apiOffset));
    }

    return records;
  }

  private SourceRecord buildSourceRecord(Map<String, Object> sapProduct, String lastRead, Long apiOffset) {
    Map<String, Object> sourceOffset = buildSourceOffset(lastRead, apiOffset);
    Map<String, Object> sourcePartition = buildSourcePartition();
    return new SourceRecord(sourcePartition, sourceOffset, topic, null, sapProduct.get("code"), null, sapProduct);
  }

  private long calculateApiOffset(long currentLoopOffset, String newFromDate, String oldFromDate) {
    if (newFromDate.equals(oldFromDate)) {
      return ++currentLoopOffset;
    }
    return 1L;
  }

  private Map<String, Object> buildSourcePartition() {
    return Collections.singletonMap(URL, baseUrl);
  }

  private Map<String, Object> buildSourceOffset(String lastRead, Long apiOffset) {
    Map<String, Object> sourceOffset = new HashMap<>();
    sourceOffset.put(LAST_READ, lastRead);
    sourceOffset.put(LAST_API_OFFSET, apiOffset);
    return sourceOffset;
  }

  List<Map> getSapProducts(String date) {
    List<Map> sapResponse = null;
    try {
      sapResponse = SapClient.getProducts(date, username, password, baseUrl, apiVersion);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return sapResponse;
  }

  @Override
  public void stop() {
    //TODO: Do whatever is required to stop your task.
  }
}