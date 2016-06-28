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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ZmonRestFilterBeanPostProcessor implements BeanPostProcessor {
    private static final Log logger = LogFactory.getLog(ZmonRestFilterBeanPostProcessor.class);

    private final ZmonRestResponseBackendMetricsFilter zmonRestResponseFilter;
    private final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

    @Autowired
    public ZmonRestFilterBeanPostProcessor(
            final ZmonRestResponseBackendMetricsFilter zmonRestResponseBackendMetricsFilter) {
        this.zmonRestResponseFilter = zmonRestResponseBackendMetricsFilter;
        interceptors.add(zmonRestResponseBackendMetricsFilter);
    }

    @Override
    public Object postProcessBeforeInitialization(final Object possiblyRestTemplateBean, final String beanName)
        throws BeansException {

        if (possiblyRestTemplateBean instanceof RestTemplate) {

            RestTemplate restTemplateBean = (RestTemplate) possiblyRestTemplateBean;

            restTemplateBean.getInterceptors().addAll(interceptors);
            logger.info("Added " + ZmonRestFilterBeanPostProcessor.class.getCanonicalName() + " instance to "
                    + beanName);
        }

        return possiblyRestTemplateBean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object o, final String s) throws BeansException {
        return o;
    }

}
