package org.zalando.zmon.jaxrs.jersey;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;

/**
 * 
 * @author jbellmann
 *
 */
public class BaseJerseyConfig extends ResourceConfig {

	@Autowired
	private JerseyProperties jerseyProperties;

	public BaseJerseyConfig() {
	}

	@PostConstruct
	public void init() {
		//register(new BestMatchingPatternFilter(jerseyProperties.getApplicationPath()));
	}

}
