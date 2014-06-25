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
		return Response.status(200).build();
	}
	
	@POST
	@Path("/brain")
	public Response processMove(@FormParam("position") String n) { // looks at the move made, makes it, and makes random other
		g.update(Integer.parseInt(n), 1);
		if (g.hasWon(1))
			return Response.status(200).entity("w" + g.toString()).build(); // w for win
		if (g.fullBoard())
			return Response.status(200).entity("d" + g.toString()).build(); // d for draw
		g.update(g.getRandMove(), 2);
		if (g.hasWon(2))
			return Response.status(200).entity("l" + g.toString()).build(); // l for lose
		if (g.fullBoard())
			return Response.status(200).entity("d" + g.toString()).build(); // d for draw
		return Response.status(200).entity("c" + g.toString()).build(); // c for continue
	}
	
}
