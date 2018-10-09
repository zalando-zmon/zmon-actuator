package org.zalando.zmon.actuator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;
import org.zalando.zmon.actuator.metrics.MetricsWrapper;

import java.io.IOException;

import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ZmonRestResponseBackendMetricsInterceptorTest {

    private static final int PREFERRED_SLEEP_TIME = 200;
    private MetricsWrapper mockedWrapper = Mockito.mock(MetricsWrapper.class);
    private ClientHttpRequestExecution execution = Mockito.mock(ClientHttpRequestExecution.class);

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

        ArgumentCaptor<StopWatch> stopwatchArgumentCaptor = ArgumentCaptor.forClass(StopWatch.class);
        interceptor.intercept(null, null, execution);

        Mockito.verify(mockedWrapper, times(1)).recordBackendRoundTripMetrics(Mockito.any(HttpRequest.class),
                Mockito.any(ClientHttpResponse.class), stopwatchArgumentCaptor.capture());

        Assert.assertTrue("We have set thread sleep at 200", takesAtLeastSleepTime(stopwatchArgumentCaptor));

    }

    private boolean takesAtLeastSleepTime(final ArgumentCaptor<StopWatch> stopwatchArgumentCaptor) {
        return stopwatchArgumentCaptor.getValue().getTotalTimeMillis() >= PREFERRED_SLEEP_TIME;
    }
}
