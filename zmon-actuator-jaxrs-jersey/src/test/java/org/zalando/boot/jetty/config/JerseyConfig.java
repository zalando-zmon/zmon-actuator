package org.zalando.boot.jetty.config;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.zalando.boot.jetty.controller.SimpleController;
import org.zalando.zmon.jaxrs.jersey.BestMatchingPatternFilter;

/**
 * 
 * @author jbellmann
 *
 */
@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
	}

	@PostConstruct
	public void init() {
		register(SimpleController.class);
		register(new BestMatchingPatternFilter("/api"));
	}
}
