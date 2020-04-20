package io.eventuate.tram.micronaut.consumer.elasticsearch;

import javax.inject.Singleton;
import org.elasticsearch.client.RestHighLevelClient;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.consumer.elasticsearch.ElasticsearchConsumerConfigurationProperties;
import io.eventuate.tram.consumer.elasticsearch.ElasticsearchIndexDuplicateMessageDetector;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;

@Factory
public class TramConsumerElasticsearchFactory {

    @Singleton
    @Requires(missingProperty = "transactional.noop.duplicate.message.detector.factory.enabled")
    public DuplicateMessageDetector duplicateMessageDetector(RestHighLevelClient client, ElasticsearchConsumerConfigurationProperties properties) {
        return new ElasticsearchIndexDuplicateMessageDetector(client, properties);
    }
}
