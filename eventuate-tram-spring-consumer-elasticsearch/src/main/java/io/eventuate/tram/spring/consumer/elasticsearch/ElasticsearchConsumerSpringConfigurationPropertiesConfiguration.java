package io.eventuate.tram.spring.consumer.elasticsearch;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import io.eventuate.tram.consumer.elasticsearch.ElasticsearchConsumerConfigurationProperties;

@EnableConfigurationProperties(ElasticsearchConsumerSpringConfigurationProperties.class)
public class ElasticsearchConsumerSpringConfigurationPropertiesConfiguration {

    @Bean
    ElasticsearchConsumerConfigurationProperties elasticsearchConsumerSpringConfigurationProperties(ElasticsearchConsumerSpringConfigurationProperties elasticsearchConsumerSpringConfigurationProperties) {
        ElasticsearchConsumerConfigurationProperties properties = new ElasticsearchConsumerConfigurationProperties(elasticsearchConsumerSpringConfigurationProperties.getProperties());
        properties.setReceivedMessagesIndexName(elasticsearchConsumerSpringConfigurationProperties.getReceivedMessagesIndexName());
        properties.setReceivedMessagesTypeName(elasticsearchConsumerSpringConfigurationProperties.getReceivedMessagesTypeName());
        return properties;
    }
}
