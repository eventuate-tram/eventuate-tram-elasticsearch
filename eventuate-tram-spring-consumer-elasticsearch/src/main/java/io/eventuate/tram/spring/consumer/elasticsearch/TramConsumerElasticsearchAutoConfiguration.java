package io.eventuate.tram.spring.consumer.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.consumer.elasticsearch.ElasticsearchConsumerConfigurationProperties;
import io.eventuate.tram.consumer.elasticsearch.ElasticsearchIndexDuplicateMessageDetector;

@Configuration
@ConditionalOnMissingBean(DuplicateMessageDetector.class)
public class TramConsumerElasticsearchAutoConfiguration {

    @Bean
    public DuplicateMessageDetector duplicateMessageDetector(RestHighLevelClient client, ElasticsearchConsumerConfigurationProperties properties) {
        return new ElasticsearchIndexDuplicateMessageDetector(client, properties);
    }
}
