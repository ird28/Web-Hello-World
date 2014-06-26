package uk.ac.cam.ird28;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldService {
	
	private static Game g;
	private static HashMap<String,User> users = new HashMap<String,User>();
	
	@POST
	@Path("/login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		users.put(username, new User(username, password));
		System.out.println(users.size());
		return Response.status(200).build();
	}
	
	@POST
	@Path("/checkpassword")
	public Response check(@FormParam("word") String word, @CookieParam("username") String username) {
		System.out.println(users.size());
		System.out.println("Username from cookie: " + username);
		System.out.println("Password submitted: " + word);
		System.out.println("Should be username: " + users.get(username).getName());
		return Response.status(200).entity(users.get(username).checkPassword(word) ? "correct":"incorrect").build();
	}	
	
	
	@GET
	@Path("/restart")
	public Response responseMsg() {
		g = new Game();
		System.out.println("restart");
		return Response.status(200).entity(g).build();
	}
	
	@POST
	@Path("/updatecounters")
	public Response done(@FormParam("state") String state, @CookieParam("username") String username) {
		if (state=="w") users.get(username).incWins();
		if (state=="d") users.get(username).incDraws();
		if (state=="l") users.get(username).incLosses();
		return Response.status(200).build();
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
