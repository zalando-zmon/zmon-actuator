package com.zalando.zmon.boot.jetty;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.zalando.zmon.jaxrs.jersey.BaseJerseyConfig;
import org.zalando.zmon.jaxrs.jersey.BestMatchingPatternFilter;

/**
 * Uses den {@link BaseJerseyConfig} to register {@link BestMatchingPatternFilter} by default.
 *
 * @author jbellmann
 */
@RunWith(SpringRunner.class)
@DirtiesContext
public class BaseJerseyConfigIT extends AbstractRunner {

}
