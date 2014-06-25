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
	public Response processMove(@FormParam("position") String n, @FormParam("difficulty") String diff) { // looks at the move made, makes it, and makes random other
		try {
			int position = Integer.parseInt(n);
			if (g.getSpace(position) != 0)
				return Response.status(200).entity("c" + g.toString()).build(); // c for continue - space full
			g.update(position, 1);
		} catch (NumberFormatException nfe) {
			return Response.status(200).entity("c" + g.toString()).build(); // c for continue - not a number
		}
		if (g.hasWon(1))
			return Response.status(200).entity("w" + g.toString()).build(); // w for win
		if (g.fullBoard())
			return Response.status(200).entity("d" + g.toString()).build(); // d for draw
		System.out.println(diff);
		if (diff.startsWith("e")) {
			g.update(g.getRandMove(), 2);
			System.out.println("Easy move made");
		} else if (diff.startsWith("m")) {
			g.update(g.getOkayMove(), 2);
			System.out.println("Medium move made");
		} else {
			assert false;
		}
		if (g.hasWon(2))
			return Response.status(200).entity("l" + g.toString()).build(); // l for lose
		if (g.fullBoard())
			return Response.status(200).entity("d" + g.toString()).build(); // d for draw
		return Response.status(200).entity("c" + g.toString()).build(); // c for continue
	}
	
}
