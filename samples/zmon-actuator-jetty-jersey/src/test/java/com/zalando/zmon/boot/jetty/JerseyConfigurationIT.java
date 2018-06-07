package com.zalando.zmon.boot.jetty;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.zalando.zmon.boot.jetty.config.JerseyConfigExampleTwo;

/**
 * Uses {@link JerseyConfigExampleTwo} to register everything by-hand.
 *
 * @author jbellmann
 */
@RunWith(SpringRunner.class)
@DirtiesContext
@ActiveProfiles("example2")
public class JerseyConfigurationIT extends AbstractRunner {

}
