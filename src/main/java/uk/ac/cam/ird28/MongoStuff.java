package uk.ac.cam.ird28;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoStuff {
	
	
	public static void addUser(String username, User userObject) throws UnknownHostException {
		BasicDBObject document = new BasicDBObject();
		document.put("username", username);
		document.put("passhash",userObject.getPasswordHash());
		document.put("wins",userObject.getWins());
		document.put("draws",userObject.getDraws());
		document.put("losses",userObject.getLosses());
		MongoConnection.getUserTable().insert(document);
	}
	
	public static User getUser(String username) throws UnknownHostException {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		DBCursor cursor = MongoConnection.getUserTable().find(searchQuery);
		DBObject record = cursor.next();
		return new User(username, (int) record.get("passhash"), (int) record.get("wins"), (int) record.get("draws"), (int) record.get("losses"));
	}
	
	public static boolean containsUser(String username) throws UnknownHostException {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		DBCursor cursor = MongoConnection.getUserTable().find(searchQuery);
		assert cursor.count() <= 1;
		return cursor.count() == 1;
	}
	
	public static void incWins(String username) throws UnknownHostException {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("wins", getUser(username).getWins()+1);
	 
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
	 
		MongoConnection.getUserTable().update(searchQuery, updateObj);
	}
	
	public static void incDraws(String username) throws UnknownHostException {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("draws", getUser(username).getDraws()+1);
	 
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
	 
		MongoConnection.getUserTable().update(searchQuery, updateObj);
	}
	
	public static void incLosses(String username) throws UnknownHostException {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("losses", getUser(username).getLosses()+1);
	 
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
	 
		MongoConnection.getUserTable().update(searchQuery, updateObj);
	}

}
