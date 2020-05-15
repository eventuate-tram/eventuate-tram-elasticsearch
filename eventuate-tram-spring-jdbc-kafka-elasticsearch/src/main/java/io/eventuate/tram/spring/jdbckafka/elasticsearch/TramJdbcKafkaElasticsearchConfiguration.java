package io.eventuate.tram.spring.jdbckafka.elasticsearch;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.eventuate.tram.consumer.kafka.elasticsearch.ElasticsearchKafkaConsumerFactorySpringConfiguration;
import io.eventuate.tram.consumer.kafka.elasticsearch.EventuateKafkaConsumerElasticsearchSpringConfigurationPropertiesConfiguration;
import io.eventuate.tram.spring.consumer.elasticsearch.ElasticsearchConsumerSpringConfigurationPropertiesConfiguration;
import io.eventuate.tram.spring.consumer.elasticsearch.TramConsumerElasticsearchAutoConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;

@Configuration
@Import({
    TramConsumerElasticsearchAutoConfiguration.class,
    TramMessageProducerJdbcConfiguration.class,
    EventuateTramKafkaMessageConsumerConfiguration.class,
    ElasticsearchKafkaConsumerFactorySpringConfiguration.class,
    ElasticsearchConsumerSpringConfigurationPropertiesConfiguration.class,
    EventuateKafkaConsumerElasticsearchSpringConfigurationPropertiesConfiguration.class,
})
public class TramJdbcKafkaElasticsearchConfiguration {
}
