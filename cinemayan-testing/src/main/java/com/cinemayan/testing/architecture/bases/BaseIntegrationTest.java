package com.cinemayan.testing.architecture.bases;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.kafka.KafkaContainer;

@SuppressWarnings ({ "AbstractClassWithoutAbstractMethods" })
@SpringBootTest
@ExtendWith (SpringExtension.class)
public abstract class BaseIntegrationTest implements BaseUnitTest {

    public static final String DISABLED_EUREKA_CONFIG_VALUE = "false";

    @Container
    static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer("apache/kafka:4.3.0");

    static {
        KAFKA_CONTAINER.start();
    }

    @DynamicPropertySource
    static void registerKafkaAndEurekaProperties (DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.producer.bootstrap-servers",
            KAFKA_CONTAINER::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers",
            KAFKA_CONTAINER::getBootstrapServers);

        registry.add("eureka.client.enabled", () -> DISABLED_EUREKA_CONFIG_VALUE);
        registry.add("eureka.client.register-with-eureka", () -> DISABLED_EUREKA_CONFIG_VALUE);
        registry.add("eureka.client.fetch-registry", () -> DISABLED_EUREKA_CONFIG_VALUE);
    }
}
