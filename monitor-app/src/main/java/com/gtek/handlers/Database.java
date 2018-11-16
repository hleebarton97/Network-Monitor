package com.gtek.handlers;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.gtek.App;
import com.gtek.objects.Device;
import com.gtek.util.Console;
import com.gtek.util.Reference;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoTimeoutException;

public class Database {
	
	public static int TOTAL_DEVICES = 0;
	public static int TOTAL_DEVICES_DOWN = 0;
	
	private MongoClientURI connectionString; // MongoDB connection string
	private MongoClient mongoClient; // MongoDB client
	
	
	
	/**
	 * Create and return database.
	 * @return 
	 * @return 
	 */
	public static Database CreateDatabase() {
		return new Database(Reference.CONNECTION_STRING);
	}
	
	/**
	 * Close the database connection client.
	 */
	public static void Close(Database db) {
		db.getMongoClient().close();
	}
	
	/**
	 * Update a network site with uptime information.
	 * 
	 * @param col
	 * @param tower
	 * @param isUp
	 */
	public static void UpdateDatabase(DBCollection col, Device tower) {
		// Update object with this network site's OID
		BasicDBObject query = new BasicDBObject().append("_id", new ObjectId(tower.getOid()));
		
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
		} catch(IllegalStateException e) { // Closing DB during write process
			//e.printStackTrace();
		}
		
		App.UpdateGUIText();
	}
	
	/**
	 * Get the "towers" MongoDB instance
	 * collection. 
	 * 
	 * @return DBCollection
	 */
	public static DBCollection GetCollectionTowers(Database db) {
		return db
				.getMongoClient()
					.getDB(Reference.DATABASE_NAME)
						.getCollection(Reference.COLLECTION_NAME_TOWERS);
	}
	
	/**
	 * Get the "accesspoints" MongoDB instance
	 * collection. 
	 * 
	 * @return DBCollection
	 */
	public static DBCollection GetCollectionAccessPoints(Database db) {
		return db
				.getMongoClient()
					.getDB(Reference.DATABASE_NAME)
						.getCollection(Reference.COLLECTION_NAME_APS);
	}
	
	/**
	 * Get the total amount of documents from each collection.
	 * @return int
	 */
	public static int GetTotalDocumentCount() {
		Database db = Database.CreateDatabase();
		DBCollection COLLECTION_TOWERS = Database.GetCollectionTowers(db);
		DBCollection COLLECTION_APS = Database.GetCollectionAccessPoints(db);
		
		long count = COLLECTION_TOWERS.count();
		count += COLLECTION_APS.count();
		
		Database.Close(db);
		return (int)count;
	}
	
	public static int GetTotalDevicesDown() {
		
		Database db = Database.CreateDatabase();
		DBCollection COLLECTION_TOWERS = Database.GetCollectionTowers(db);
		DBCollection COLLECTION_APS = Database.GetCollectionAccessPoints(db);
		
		ArrayList<Device> DOWN_DEVICE_LIST = new ArrayList<Device>();
		
		DBObject dbo_tower = new BasicDBObject("down", true);
    	for(DBObject cursor : COLLECTION_TOWERS.find(dbo_tower)) {
    		DOWN_DEVICE_LIST.add(new Device(cursor.get("_id").toString(), 
												  Integer.parseInt(cursor.get("id").toString()), 
												  cursor.get("name").toString(), 
												  cursor.get("router").toString(),
												  COLLECTION_TOWERS));
    	}
    	
    	DBObject dbo_ap = new BasicDBObject("down", true);
    	for(DBObject cursor : COLLECTION_APS.find(dbo_ap)) {
    		DOWN_DEVICE_LIST.add(new Device(cursor.get("_id").toString(),
    											  Integer.parseInt(cursor.get("id").toString()),
    											  cursor.get("name").toString(),
    											  cursor.get("subnet").toString(),
    											  COLLECTION_APS));
    	}
		
    	Database.Close(db);
		return DOWN_DEVICE_LIST.size();
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
