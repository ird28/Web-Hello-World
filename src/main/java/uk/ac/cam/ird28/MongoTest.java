package uk.ac.cam.ird28;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoTest {
	
	private static boolean usersAreSame(User u1, User u2) {
		boolean a = u1.getName().equals(u2.getName());
		boolean b = u1.getPasswordHash() == u2.getPasswordHash();
		boolean c = u1.getWins() == u2.getWins();
		boolean d = u1.getDraws() == u2.getDraws();
		boolean e = u1.getLosses() == u2.getLosses();
		return (a&&b&&c&&d&&e);
	}

	@Test
	public void testMongoStuff() {
		User u = new User("myName", "a_password");
		try {
			assert !MongoStuff.containsUser("myName");
			MongoStuff.addUser("myName", u);
			assertTrue(MongoStuff.containsUser("myName"));
			assertTrue(usersAreSame(u, MongoStuff.getUser("myName")));
			MongoStuff.incWins("myName");;
			assertFalse(usersAreSame (u, MongoStuff.getUser("myName")));
			u.incWins();
			assertTrue(usersAreSame(u, MongoStuff.getUser("myName")));
			MongoStuff.removeUser("myName");
			assertFalse(MongoStuff.containsUser("myName"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMongoConnection() {
		try {
			MongoClient A = MongoConnection.getClientInstance();
			MongoClient B = MongoConnection.getClientInstance();
			assertTrue(A==B);
			DB C = MongoConnection.getDBInstance();
			DB D = MongoConnection.getDBInstance();
			assertTrue(C==D);
			DBCollection E = MongoConnection.getUserTable();
			DBCollection F = MongoConnection.getUserTable();
			assertTrue(E==F);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
