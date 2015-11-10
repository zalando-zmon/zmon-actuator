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
package org.zalando.zmon.jaxrs.jersey;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * Filter that tries to fetch the matched path.
 * 
 * @author jbellmann
 *
 */
public class BestMatchingPatternFilter implements ContainerRequestFilter {

	private static final String ATTRIBUTE_NAME = "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern";

	private final Logger logger = LoggerFactory.getLogger(BestMatchingPatternFilter.class);

	private final String prefix;

	public BestMatchingPatternFilter(String prefix) {
		this.prefix = prefix;
	}

	public BestMatchingPatternFilter() {
		this("");
	}

	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {
			ContainerRequest containerRequest = (ContainerRequest) requestContext;
			ExtendedUriInfo extendedUriInfo = containerRequest.getUriInfo();
			UriRoutingContext uriRoutingContext = (UriRoutingContext) extendedUriInfo;
			Endpoint endpoint = uriRoutingContext.getEndpoint();
			ResourceMethodInvoker invoker = (ResourceMethodInvoker) endpoint;

			ResourceInfo info = getResourceInfo(invoker);

			String matchedPath = info.getMatchedPath();

			//
			containerRequest.setProperty(ATTRIBUTE_NAME, matchedPath);

		} catch (Exception e) {
			logger.warn("Exception resolving 'bestMatchingPattern'", e);
		}
	}

	protected ResourceInfo getResourceInfo(ResourceMethodInvoker invoker) {
		return new ResourceInfo(invoker.getResourceClass(), invoker.getResourceMethod(), prefix);
	}

	static class ResourceInfo {

		private final Method method;
		private final Class<?> clazz;
		private final String prefix;

		ResourceInfo(Class<?> clazz, Method method, String prefix) {
			this.clazz = clazz;
			this.method = method;
			this.prefix = prefix;
		}

		String getMatchedPath() {
			StringBuilder sb = new StringBuilder();
			sb.append(prefix);
			sb.append(getClassPathAnnotation());
			sb.append(getMethodPathAnnotation());
			return sb.toString();
		}

		private String getClassPathAnnotation() {
			Path path = AnnotationUtils.findAnnotation(clazz, Path.class);
			return path != null ? path.value() : "";
		}

		private String getMethodPathAnnotation() {
			Path path = AnnotationUtils.findAnnotation(method, Path.class);
			return path != null ? path.value() : "";
		}
	}
}
