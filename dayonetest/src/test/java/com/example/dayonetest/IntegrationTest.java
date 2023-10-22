package com.example.dayonetest;

import com.redis.testcontainers.RedisContainer;
import jakarta.transaction.Transactional;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Ignore //상속시킯 부모클래스를 만들거고 이건 테스트가 불필요
@Transactional //테스트 모두 동장 이후 롤백 -> db에 반영 안됨
@SpringBootTest //스프링 부트 환경
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestInitializer.class)
public class IntegrationTest {

    static DockerComposeContainer rdbms;
    static RedisContainer redis;

    static {
        rdbms = new DockerComposeContainer(new File("infra/test/docker-compose.yaml"))
                .withExposedService("local-db",
                        3306,
                        Wait.forLogMessage(".*ready for connection.*", 1)
                                .withStartupTimeout(Duration.ofSeconds(3000))
                )
                .withExposedService(
                        "local-db-migrate",
                        0,
                        Wait.forLogMessage("(.*Successfully applied.*)|(.Successfully validated.*)", 1)
                                .withStartupTimeout(Duration.ofSeconds(300))
                );
        rdbms.start();

        redis = new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag("6"));
        redis.start();
    }

    static class IntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            Map<String, String> properties = new HashMap<>();

            var rdbmsHost = rdbms.getServiceHost("local-db", 3306);
            var rdbmsPort = rdbms.getServicePort("local-db", 3306);

            properties.put("spring.datasource.url", "jdbc:mysql://" + rdbmsHost + ":" + rdbmsPort + "/score");

            var redisHost = redis.getHost();
            var redisPort = redis.getFirstMappedPort();
            properties.put("spring.data.redis.host", redisHost);
            properties.put("spring.data.redis.port", redisPort.toString());


            TestPropertyValues.of(properties)
                    .applyTo(applicationContext);
        }
    }
}
