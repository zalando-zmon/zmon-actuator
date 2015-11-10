package org.zalando.boot.jetty.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

@Component
@Path("/simple")
public class SimpleController {

	@GET
	@Path("/{id}/complex")
	public String invoke(@PathParam("id") String id){
		return id;
	}
}
