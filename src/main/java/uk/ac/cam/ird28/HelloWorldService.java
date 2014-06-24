package uk.ac.cam.ird28;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldService {
	
	@GET
	@Path("/")
	public Response responseMsg() {
		return Response.status(200).entity("Hello world").build();
	}
	
	@POST
	@Path("/first")
	public Response reply(@FormParam("position") String n) {
		return Response.status(200).entity("You chose position " + n).build();
	}
	
}
