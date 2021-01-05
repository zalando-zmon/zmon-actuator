package org.zalando.zmon.actuator.config;

import com.codahale.metrics.MetricRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.zmon.actuator.ZmonMetricsFilter;
import org.zalando.zmon.actuator.ZmonRestResponseBackendMetricsInterceptor;
import org.zalando.zmon.actuator.metrics.MetricsWrapper;

/**
 * @author jbellmann
 */

@Configuration
@ConditionalOnClass(MetricRegistry.class)
@AutoConfigureAfter(name = "MetricsDropwizardAutoConfiguration")
public class ZmonMetricsAutoConfiguration {

    @Bean(name = "zmonMetricsWrapper")
    public MetricsWrapper zmonMetricsWrapper(final MeterRegistry meterRegistry) {
        return new MetricsWrapper(meterRegistry);
    }

    @Bean
    public ZmonMetricsFilter zmonMetricsFilter(final MetricsWrapper metricsWrapper) {
        return new ZmonMetricsFilter(metricsWrapper);
    }

    @Bean
    public ZmonRestResponseBackendMetricsInterceptor zmonRestResponseBackendMetricsInterceptor(
            final MetricsWrapper metricsWrapper) {
        return new ZmonRestResponseBackendMetricsInterceptor(metricsWrapper);
    }

}
