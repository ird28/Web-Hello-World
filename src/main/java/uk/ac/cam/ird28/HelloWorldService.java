package uk.ac.cam.ird28;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

@Path("/test")
public class HelloWorldService {
	
	@GET
	@Path("/form")
	public Response responseMsg() {
		String form = "<html><body><form method=\"POST\">Put a word here:<input type=\"text\" name = \"in\"><input type=\"submit\" value=\"Submit\"></form></body></html>";
		return Response.status(200).entity(form).build();
	}
	
	@POST
	@Path("/form")
	public Response reply(@FormParam("in") String word) {
		return Response.status(200).entity("Your word was " + word).build();
	}
	
}
