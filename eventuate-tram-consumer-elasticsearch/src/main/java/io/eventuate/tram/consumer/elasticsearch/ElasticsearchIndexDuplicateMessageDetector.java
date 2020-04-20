package io.eventuate.tram.consumer.elasticsearch;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.consumer.common.SubscriberIdAndMessage;

public class ElasticsearchIndexDuplicateMessageDetector implements DuplicateMessageDetector {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RestHighLevelClient client;
    private final ElasticsearchConsumerConfigurationProperties properties;

    public ElasticsearchIndexDuplicateMessageDetector(RestHighLevelClient client, ElasticsearchConsumerConfigurationProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    public boolean isDuplicate(String consumerId, String messageId) {
        try {
            String id = id(consumerId, messageId);
            String receivedMessagesIndexName = properties.getReceivedMessagesIndexName();
            String receivedMessagesTypeName = properties.getReceivedMessagesTypeName();
            GetRequest request = new GetRequest(receivedMessagesIndexName)
                                     .type(receivedMessagesTypeName)
                                     .id(id)
                                     .routing(messageId);
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            return response.isExists();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback) {
        String subscriberId = subscriberIdAndMessage.getSubscriberId();
        String messageId = subscriberIdAndMessage.getMessage().getId();
        if (isDuplicate(subscriberId, messageId)) {
            logger.info("Message duplicate: consumerId = {}, messageId = {}", subscriberId, messageId);
        } else {
            callback.run();
            saveReceivedMessage(subscriberId, messageId);
        }
    }

    private void saveReceivedMessage(String subscriberId, String messageId) {
        try {
            String id = id(subscriberId, messageId);
            String receivedMessagesIndexName = properties.getReceivedMessagesIndexName();
            String receivedMessagesTypeName = properties.getReceivedMessagesTypeName();
            IndexRequest indexRequest = new IndexRequest(receivedMessagesIndexName)
                                            .type(receivedMessagesTypeName)
                                            .id(id)
                                            .source(Collections.emptyMap())
                                            .routing(messageId);
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String id(String consumerId, String messageId) {
        return String.format("%s-%s", consumerId, messageId);
    }
}
