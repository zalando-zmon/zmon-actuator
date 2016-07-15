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

import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.mockito.invocation.InvocationOnMock;

import org.mockito.stubbing.Answer;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import org.zalando.zmon.actuator.metrics.MetricsWrapper;

import com.google.common.base.Stopwatch;

public class ZmonRestResponseBackendMetricsInterceptorTest {

    public static final int PREFERRED_SLEEP_TIME = 200;
    MetricsWrapper mockedWrapper = Mockito.mock(MetricsWrapper.class);
    ClientHttpRequestExecution execution = Mockito.mock(ClientHttpRequestExecution.class);

    @Test
    public void testTimeElapsing() throws IOException {
        ZmonRestResponseBackendMetricsInterceptor interceptor = new ZmonRestResponseBackendMetricsInterceptor(
                mockedWrapper);

        Mockito.when(execution.execute(Mockito.any(HttpRequest.class), Mockito.any(byte[].class))).then(
            new Answer<ClientHttpResponse>() {
                @Override
                public ClientHttpResponse answer(final InvocationOnMock invocationOnMock) throws Throwable {
                    Thread.sleep(PREFERRED_SLEEP_TIME);
                    return null;
                }
            });

        ArgumentCaptor<Stopwatch> stopwatchArgumentCaptor = ArgumentCaptor.forClass(Stopwatch.class);
        interceptor.intercept(null, null, execution);

        Mockito.verify(mockedWrapper, times(1)).recordBackendRoundTripMetrics(Mockito.any(HttpRequest.class),
            Mockito.any(ClientHttpResponse.class), stopwatchArgumentCaptor.capture());

        Assert.assertTrue("We have set thread sleep at 200", takesAtLeastSleepTime(stopwatchArgumentCaptor));

    }

    private boolean takesAtLeastSleepTime(final ArgumentCaptor<Stopwatch> stopwatchArgumentCaptor) {
        return stopwatchArgumentCaptor.getValue().elapsed(TimeUnit.MILLISECONDS) >= PREFERRED_SLEEP_TIME;
    }
}
