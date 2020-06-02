package io.eventuate.tram.spring.consumer.elasticsearch;

import java.io.IOException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.consumer.common.SubscriberIdAndMessage;
import io.eventuate.tram.consumer.elasticsearch.ElasticsearchConsumerConfigurationProperties;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.producer.MessageBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EventuateSpringElasticsearchIndexBasedDuplicateMessageDetectorTest.DuplicateMessageDetectorTestConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EventuateSpringElasticsearchIndexBasedDuplicateMessageDetectorTest {

  @Autowired
  private DuplicateMessageDetector duplicateMessageDetector;

  @Autowired
  private RestHighLevelClient elasticsearchClient;

  @Autowired
  private ElasticsearchConsumerConfigurationProperties properties;

  @Configuration
  @EnableAutoConfiguration
  @Import({
      TramConsumerElasticsearchAutoConfiguration.class,
      ElasticsearchConsumerSpringConfigurationPropertiesConfiguration.class
  })
  static public class DuplicateMessageDetectorTestConfiguration {
  }

  @Test
  public void shouldDetectDuplicate() throws IOException {

    createReceivedMessagesIndexIfNotExists();

    String consumerId = getClass().getName();
    String messageId = Long.toString(System.currentTimeMillis());

    assertFalse(duplicateMessageDetector.isDuplicate(consumerId, messageId));

    duplicateMessageDetector.doWithMessage(new SubscriberIdAndMessage(consumerId, buildMessage(messageId)), () -> {});

    assertTrue(duplicateMessageDetector.isDuplicate(consumerId, messageId));
  }

  private static Message buildMessage(String messageId) {
    return MessageBuilder.withPayload("{}").withHeader(Message.ID, messageId).build();
  }

  private void createReceivedMessagesIndexIfNotExists() throws IOException {
    String receivedMessagesIndexName = properties.getReceivedMessagesIndexName();
    boolean exists = elasticsearchClient.indices().exists(new GetIndexRequest(receivedMessagesIndexName), RequestOptions.DEFAULT);
    if (!exists) {
      CreateIndexRequest createIndexRequest = new CreateIndexRequest(receivedMessagesIndexName);
      elasticsearchClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }
  }
}