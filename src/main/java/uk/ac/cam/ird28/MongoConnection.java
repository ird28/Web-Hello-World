package uk.ac.cam.ird28;

import java.net.UnknownHostException;

import com.mongodb.*;

public class MongoConnection {
	private static MongoClient clientInstance;
	private static DB dbInstance;
	private static DBCollection userTable;
	
	public static MongoClient getClientInstance() throws UnknownHostException {
		if (clientInstance==null) {
			clientInstance = new MongoClient();
			}
		return clientInstance;
	}
	
	public static DB getDBInstance() throws UnknownHostException {
		if (dbInstance==null) {
			dbInstance = getClientInstance().getDB("userDB");
		}
		return dbInstance;
	}
	
	public static DBCollection getUserTable() throws UnknownHostException {
		if (userTable==null) {
			userTable = getDBInstance().getCollection("users");
		}
		return userTable;
	}
	
	

}
