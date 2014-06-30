package uk.ac.cam.ird28;

import java.net.UnknownHostException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldService {
	
	private static Game g;
	
	@POST
	@Path("/login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		try {
			if (MongoStuff.containsUser(username)) {
				if (MongoStuff.getUser(username).checkPassword(password)) {
					System.out.println("Successful log in by "+username);
					return Response.status(200).entity("accepted").build();
				} else {
					System.out.println("Failed attempt to log in by "+username);
					return Response.status(200).entity("rejected").build();
				}
			} else {
				return Response.status(200).entity("unrecognised").build();
			}
		} catch (UnknownHostException uhe) {
			return Response.status(503).build();
		}
	}
	
	@POST
	@Path("/signup")
	public Response signup(@FormParam("username") String username, @FormParam("passwordA") String passwordA, @FormParam("passwordB") String passwordB) {
		try {
			if (MongoStuff.containsUser(username)) {
				return Response.status(200).entity("taken").build();
			}
			if (!passwordA.equals(passwordB)) {
				return Response.status(200).entity("inconsistent").build();
			}
			MongoStuff.addUser(username, new User(username, passwordA));
			System.out.println("Adding user \""+username+"\" with password \""+passwordA+"\"");
			return Response.status(200).entity("success").build();
		} catch (UnknownHostException uhe) {
			return Response.status(503).build();
		}
	}
	
	@POST
	@Path("/checkpassword")
	public Response check(@FormParam("word") String word, @CookieParam("username") String username) {
		try {
			return Response.status(200).entity(MongoStuff.getUser(username).checkPassword(word) ? "correct":"incorrect").build();
		} catch (UnknownHostException uhe) {
			return Response.status(503).build();
		}
	}	
	
	
	@GET
	@Path("/restart")
	public Response responseMsg() {
		g = new Game();
		if (g.getWhoFirst() == 2)
			g.update(g.computeRandMove(),2);
		else assert g.getWhoFirst() == 1;
		return Response.status(200).entity(g).build();
	}
	

	@POST
	@Path("/updatecounters")
	public Response done(@FormParam("state") String state, @FormParam("difficulty") String diff, @CookieParam("username") String username) {
		try {
			if (g.easyUsed && state.startsWith("w")) {
				return Response.status(200).entity(MongoStuff.getUser(username)).build();
			}
			if (state.startsWith("w")) MongoStuff.incWins(username);
			if (state.startsWith("d")) MongoStuff.incDraws(username);
			if (state.startsWith("l")) MongoStuff.incLosses(username);
			return Response.status(200).entity(MongoStuff.getUser(username)).build();
		} catch (UnknownHostException uhe) {
			return Response.status(503).build();
		}
	}
	
	@GET
	@Path("/checkname")
	public Response checkName(@CookieParam("username") String name) {
		try {
			return Response.status(200).entity(MongoStuff.containsUser(name)?"valid":"invalid").build();
		} catch (UnknownHostException uhe) {
				return Response.status(503).build();
		}
	}
	@POST
	@Path("/brain")
	public Response processMove(@FormParam("position") String position, @FormParam("difficulty") String diff) { // looks at the move made, makes it, and makes random other
		int pos = Integer.parseInt(position);
		if (g.getSpace(pos) != 0)
			return Response.status(200).entity(g).build();
		g.update(pos, 1);
		if (g.getStatus() != "c") {
			return Response.status(200).entity(g).build();
		}
		if (diff.startsWith("e")) {
			g.easyUsed = true;
			g.update(g.computeRandMove(), 2);
		} else if (diff.startsWith("m")) {
			g.update(g.computeOkayMove(), 2);
		} else if (diff.startsWith("h")) {
			g.update(g.computeBestMove(), 2);
		} else {
			assert false;
		}
		
			
		
		return Response.status(200).entity(g).build();
	}
	
}
