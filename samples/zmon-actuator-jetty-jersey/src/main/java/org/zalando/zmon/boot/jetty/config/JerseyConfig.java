package org.zalando.zmon.boot.jetty.config;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.zalando.zmon.boot.jetty.controller.SimpleController;

/**
 * 
 * @author jbellmann
 *
 */
@Configuration
public class JerseyConfig extends ResourceConfig {

	@Autowired
	private JerseyProperties jerseyProperties;

	public JerseyConfig() {
	}

	@PostConstruct
	public void init() {
		register(SimpleController.class);
		register(new RequestFilter(jerseyProperties.getApplicationPath()));
	}

	static class RequestFilter implements ContainerRequestFilter {

		private final String prefix;

		public RequestFilter(String prefix) {
			this.prefix = prefix;
		}

		public RequestFilter() {
			this("");
		}

		public void filter(ContainerRequestContext requestContext) throws IOException {
			ContainerRequest containerRequest = (ContainerRequest) requestContext;
			ExtendedUriInfo extendedUriInfo = containerRequest.getUriInfo();
			UriRoutingContext uriRoutingContext = (UriRoutingContext) extendedUriInfo;
			Endpoint endpoint = uriRoutingContext.getEndpoint();
			ResourceMethodInvoker invoker = (ResourceMethodInvoker) endpoint;
			ResourceInfo info = getResourceInfo(invoker);
			String matchedPath = info.getMatchedPath();
			containerRequest.setProperty("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern",
					matchedPath);
			System.out.println("MATCHED PATH : " + matchedPath);
		}

		protected ResourceInfo getResourceInfo(ResourceMethodInvoker invoker) {
			return new ResourceInfo(invoker.getResourceClass(), invoker.getResourceMethod(), prefix);
		}

	}

	static class ResourceInfo {

		private final Method method;
		private final Class<?> clazz;
		private final String prefix;

		public ResourceInfo(Class<?> clazz, Method method, String prefix) {
			this.clazz = clazz;
			this.method = method;
			this.prefix = prefix;
		}

		public Method getMethod() {
			return method;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public String getMatchedPath() {
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
