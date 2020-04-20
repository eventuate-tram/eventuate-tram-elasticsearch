package io.eventuate.tram.consumer.elasticsearch;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ElasticsearchConsumerConfigurationProperties {

    private static final String DEFAULT_RECEIVED_MESSAGES_INDEX_NAME = "received-messages";
    private static final String DEFAULT_RECEIVED_MESSAGES_TYPE_NAME = "_doc";

    private Map<String, String> properties = new HashMap<>();

    private String receivedMessagesIndexName;
    private String receivedMessagesTypeName;

    public String getReceivedMessagesIndexName() {
        return Optional.ofNullable(receivedMessagesIndexName).orElse(DEFAULT_RECEIVED_MESSAGES_INDEX_NAME);
    }

    public void setReceivedMessagesIndexName(String receivedMessagesIndexName) {
        this.receivedMessagesIndexName = receivedMessagesIndexName;
    }

    public String getReceivedMessagesTypeName() {
        return Optional.ofNullable(receivedMessagesTypeName).orElse(DEFAULT_RECEIVED_MESSAGES_TYPE_NAME);
    }

    public void setReceivedMessagesTypeName(String receivedMessagesTypeName) {
        this.receivedMessagesTypeName = receivedMessagesTypeName;
    }


    public ElasticsearchConsumerConfigurationProperties() {
    }

    public ElasticsearchConsumerConfigurationProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public static ElasticsearchConsumerConfigurationProperties empty() {
        return new ElasticsearchConsumerConfigurationProperties();
    }
}
