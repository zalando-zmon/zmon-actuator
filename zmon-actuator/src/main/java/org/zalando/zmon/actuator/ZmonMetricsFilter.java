/**
 * Copyright (C) 2015 Zalando SE (http://tech.zalando.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zalando.zmon.actuator;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;

import org.springframework.util.StopWatch;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import org.zalando.zmon.actuator.metrics.MetricsWrapper;

/**
 * Absolute the same as in the original. But instead of using CounterService and GaugeService a Timer fom Codahale is
 * used.
 *
 * @author  jbellmann
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ZmonMetricsFilter extends OncePerRequestFilter {
    private static final int UNDEFINED_HTTP_STATUS = 999;

    private final MetricsWrapper metricsWrapper;

    public ZmonMetricsFilter(final MetricsWrapper metricsWrapper) {
        this.metricsWrapper = metricsWrapper;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String path = new UrlPathHelper().getPathWithinApplication(request);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        try {
            chain.doFilter(request, response);
            status = getStatus(response);
        } finally {
            stopWatch.stop();
            metricsWrapper.recordClientRequestMetrics(request, path, status, stopWatch.getTotalTimeMillis());
        }
    }

    private int getStatus(final HttpServletResponse response) {
        try {
            return response.getStatus();
        } catch (Exception ex) {
            return UNDEFINED_HTTP_STATUS;
        }
    }

}
