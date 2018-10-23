package com.gtek;

import java.util.ArrayList;
import com.gtek.handlers.Database;
import com.gtek.handlers.Ping;
import com.gtek.handlers.State;
import com.gtek.objects.NetworkSite;
import com.gtek.util.Reference;
import com.mongodb.*;

public class App {
	
    public static void main( String[] args ) {
    	
    	/**
    	 * INITIALIZATION
    	 * 
    	 * Initialize the state handler, Create our network site object list,
    	 * Initialize the MongoDB instance and get our collection of 
    	 * documents.
    	 */
    	
    	State STATE = new State(); // Create our state handler
    	// Initialize our list of network site objects
    	ArrayList<NetworkSite> NETWORK_SITE_LIST = new ArrayList<NetworkSite>();
    	
    	// Get our collection instance from the MongoDB instance
    	STATE.setStateProcess("Retrieving network site information");
    	DBCollection COLLECTION_TOWERS = Database.GetCollectionTowers();
    	
    	// Get our collection instance from the MongoDB instance
    	DBCollection COLLECTION_APS = Database.GetCollectionAccessPoints();
    	
    	// Get network site information
    	// And create each network site object
    	STATE.setStateProcess("Creating network site objects");
    	DBObject dbo_tower = new BasicDBObject("router", new BasicDBObject("$exists", true));
    	for(DBObject cursor : COLLECTION_TOWERS.find(dbo_tower)) {
			NETWORK_SITE_LIST.add(new NetworkSite(cursor.get("_id").toString(), 
												  Integer.parseInt(cursor.get("id").toString()), 
												  cursor.get("name").toString(), 
												  cursor.get("router").toString(),
												  COLLECTION_TOWERS));
    	}
    	
    	// Add access points to network site objects
    	DBObject dbo_ap = new BasicDBObject("subnet", new BasicDBObject("$exists", true));
    	for(DBObject cursor : COLLECTION_APS.find(dbo_ap)) {
    		NETWORK_SITE_LIST.add(new NetworkSite(cursor.get("_id").toString(),
    											  Integer.parseInt(cursor.get("id").toString()),
    											  cursor.get("name").toString(),
    											  cursor.get("subnet").toString(),
    											  COLLECTION_APS));
    	}
    	
    	
    	
    	/**
    	 * BEGIN PINGING PROCESS
    	 * 
    	 * Update the state to running and utilize the ping object
    	 * to start a scheduled process for every minute.
    	 * Ping each network site and update their MongoDB
    	 * document utilizing multiple threads.
    	 */
    	
    	// Update system state
    	STATE.setState(State.STATE_RUNNING);
    	STATE.setStateProcess("Running");
    	// Begin ping process
    	Ping.Start(NETWORK_SITE_LIST);
    	
    }
    
}
