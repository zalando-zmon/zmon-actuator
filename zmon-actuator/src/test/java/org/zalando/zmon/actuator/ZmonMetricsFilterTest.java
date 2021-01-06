package org.zalando.zmon.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author jbellmann
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class, webEnvironment = RANDOM_PORT,
        properties = {"debug=false",
        "management.endpoints.web.exposure.include=prometheus,health,info,metrics"})
public class ZmonMetricsFilterTest {

    private final Logger logger = LoggerFactory.getLogger(ZmonMetricsFilterTest.class);

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private MeterRegistry meterRegistry;

    private TestRestTemplate restTemplate = new TestRestTemplate();


    private final Random random = new Random(System.currentTimeMillis());

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {

            restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);
            TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
        }

        assertThat(meterRegistry.get("zmon.response.200.GET.hello").timers()).isNotNull();
        assertThat(meterRegistry.get("zmon.response.503.GET.hello").timers()).isNotNull();

        String metricsEndpointResponse = restTemplate.getForObject("http://localhost:" + port + "/actuator/metrics/zmon.response.200.GET.hello",
                String.class);

        logger.info(metricsEndpointResponse);
    }
}
