package com.gtek.handlers;

import java.net.UnknownHostException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Database {
	
	private MongoClientURI connectionString; 
	private MongoClient mongoClient;
	

	
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
