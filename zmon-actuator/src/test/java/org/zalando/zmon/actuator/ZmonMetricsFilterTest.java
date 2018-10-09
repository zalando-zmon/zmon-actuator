package org.zalando.zmon.actuator;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
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
@SpringBootTest(classes = ExampleApplication.class, webEnvironment = RANDOM_PORT, properties = {"debug=false"})
public class ZmonMetricsFilterTest {

    private final Logger logger = LoggerFactory.getLogger(ZmonMetricsFilterTest.class);

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private MetricRegistry metricRegistry;

    private TestRestTemplate restTemplate = new TestRestTemplate();


    private final Random random = new Random(System.currentTimeMillis());

    @Before
    public void setUp() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(2, TimeUnit.SECONDS);
    }

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {

            restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);
            TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
        }

        assertThat(metricRegistry.getTimers().get("zmon.response.200.GET.hello")).isNotNull();
        assertThat(metricRegistry.getTimers().get("zmon.response.503.GET.hello")).isNotNull();

        String metricsEndpointResponse = restTemplate.getForObject("http://localhost:" + port + "/metrics",
                String.class);

        logger.info(metricsEndpointResponse);
    }
}
