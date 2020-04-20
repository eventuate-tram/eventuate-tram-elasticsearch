package io.eventuate.tram.spring.consumer.elasticsearch;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("eventuate.local.consumer.elasticsearch")
public class ElasticsearchConsumerSpringConfigurationProperties {
  private Map<String, String> properties = new HashMap<>();

  private String receivedMessagesIndexName;
  private String receivedMessagesTypeName;

  public String getReceivedMessagesIndexName() {
    return receivedMessagesIndexName;
  }

  public void setReceivedMessagesIndexName(String receivedMessagesIndexName) {
    this.receivedMessagesIndexName = receivedMessagesIndexName;
  }

  public String getReceivedMessagesTypeName() {
    return receivedMessagesTypeName;
  }

  public void setReceivedMessagesTypeName(String receivedMessagesTypeName) {
    this.receivedMessagesTypeName = receivedMessagesTypeName;
  }

  public Map<String, String> getProperties() {
    return properties;
  }
}
