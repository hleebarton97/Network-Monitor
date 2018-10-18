package com.gtek.handlers;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.gtek.objects.NetworkSite;
import com.gtek.util.Console;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoTimeoutException;

public class Database {
	
	private MongoClientURI connectionString; // MongoDB connection string
	private MongoClient mongoClient; // MongoDB client
	
	
	
	/**
	 * Update a network site with uptime information.
	 * 
	 * @param col
	 * @param tower
	 * @param isUp
	 */
	public static void UpdateDatabase(DBCollection col, NetworkSite tower) {
		// Update object with this network site's OID
		BasicDBObject query = new BasicDBObject().append("_id", new ObjectId(tower.getOid()));
		
		Console.log("Updating DB...");
		
		try {
			// Update status
			col.update(
					query,
					new BasicDBObject().append("$set", new BasicDBObject().append("down", tower.isDown()))
			);
			// Update down time
			col.update(
					query,
					new BasicDBObject().append("$set", new BasicDBObject().append("downTime", tower.getDownTime()))
			);
			
		} catch(MongoTimeoutException e) { // Timeout - Loss of internet connection
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * CONSTRUCTOR
	 * Initialize the mongoDB client instance.
	 */
	public Database() {
		this.mongoClient = this.createMongoClient();
	}
	
	/**
	 * CONSTRUCTOR
	 * Initialize the mongoDB client instance
	 * with a connection string.
	 * 
	 * @param conn_str
	 */
	public Database(String conn_str) {
		this.createConnection(conn_str);
		this.mongoClient = this.createMongoClient();
	}
	
	
	
	/**
	 * Create an instance of the mongoD
	 * client. 
	 * 
	 * @return -> new instance of mongo client
	 * @throws UnknownHostException
	 */
	private MongoClient createMongoClient() {
		try {
			return new MongoClient(this.getConnectionString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * Set the connection string of the mongoDB
	 * instance.
	 * 
	 * @param conn_str
	 */
	public void createConnection(String conn_str) {
		this.connectionString = new MongoClientURI(conn_str);
	}
	
	/**
	 * Return the mongoDB instance collection 
	 * string.
	 * 
	 * @return -> mongoDB connection string
	 */
	public MongoClientURI getConnectionString() {
		return this.connectionString;
	}
	
	/**
	 * Return the mongoDB instance client.
	 * 
	 * @return -> mongoDB client
	 */
	public MongoClient getMongoClient() {
		return this.mongoClient;
	}
	
}
