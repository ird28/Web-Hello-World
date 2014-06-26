package uk.ac.cam.ird28;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldService {
	
	private static Game g;
	
	@GET
	@Path("/restart")
	public Response responseMsg() {
		g = new Game();
		System.out.println("restart");
		return Response.status(200).entity(g).build();
	}
	
	@POST
	@Path("/brain")
	public Response processMove(@FormParam("position") String position, @FormParam("difficulty") String diff) { // looks at the move made, makes it, and makes random other
		int pos = Integer.parseInt(position);
		g.update(pos, 1);
		if (g.getStatus() != "c") {
			return Response.status(200).entity(g).build();
		}
		if (diff.startsWith("e")) {
			g.update(g.computeRandMove(), 2);
			System.out.println("Easy move made");
		} else if (diff.startsWith("m")) {
			g.update(g.computeOkayMove(), 2);
			System.out.println("Medium move made");
		} else if (diff.startsWith("h")) {
			g.update(g.computeBestMove(), 2);
			System.out.println("Hard move made");
		} else {
			assert false;
		}
		return Response.status(200).entity(g).build();
	}
	
}
