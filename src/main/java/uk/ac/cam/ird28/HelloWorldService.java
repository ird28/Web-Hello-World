package uk.ac.cam.ird28;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldService {
	
	@GET
	public Response responseMsg() {
		return Response.status(200).entity("Hello World!").build();
	}
}
