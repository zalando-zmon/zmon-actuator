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
package com.zalando.zmon.boot.jetty;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zalando.zmon.boot.jetty.JettyApplication;
import org.zalando.zmon.jaxrs.jersey.BaseJerseyConfig;
import org.zalando.zmon.jaxrs.jersey.BestMatchingPatternFilter;

/**
 * Uses den {@link BaseJerseyConfig} to register {@link BestMatchingPatternFilter} by default.
 * 
 * @author jbellmann
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { JettyApplication.class })
@WebIntegrationTest
@DirtiesContext
public class BaseJerseyConfigIT extends AbstractRunner {
	
}
