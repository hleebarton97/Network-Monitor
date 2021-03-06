package com.gtek.handlers;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.gtek.objects.NetworkSite;
import com.gtek.util.Console;

public class Ping extends TimerTask {
	
	private ArrayList<NetworkSite> NETWORK_SITE_LIST; // Network Site list
	private int THREAD_COUNT = 10; // How many threads to create
	
	

	/**
	 * Create the timer object, create the ping object,
	 * set the pingable list of network sites, and then
	 * start the timed function.
	 * 
	 * @param list
	 */
	public static void Start(ArrayList<NetworkSite> list) {
		Timer timer = new Timer(); // Create new timer object
    	Ping ping = new Ping(); // Create new ping object (this)
    	ping.setPingable(list); // Set the list
    	timer.schedule(ping, 0, (60000)); // Start timed task
	}
	
	/**
	 * Task that is timed to run every minute.
	 */
	@Override
	public void run() {
		
		// Determine how many lists to create for 10 threads.
		double count = Math.floor(NETWORK_SITE_LIST.size() / THREAD_COUNT);
		Console.log("Size: " + NETWORK_SITE_LIST.size() + " | Count per Thread: " + count);
		// Create a new thread object to ping the appropriate amount of network sites
		// per thread
		for(int i = 0; i < THREAD_COUNT; i++) {
			
			if(i < THREAD_COUNT - 1) {
				Pinger pinger = new Pinger(new ArrayList(NETWORK_SITE_LIST.subList((int)(i * count), (int)((i + 1) * count))));
				pinger.start();
			} else { // Get the rest of the network sites left
				Pinger pinger = new Pinger(new ArrayList(NETWORK_SITE_LIST.subList((int)(i * count), (NETWORK_SITE_LIST.size() - 1))));
				pinger.start();
			}
			
		}
	}
	
	/**
	 * Set the pingable list of network sites
	 * 
	 * @param list
	 */
	public void setPingable(ArrayList<NetworkSite> list) {
		this.NETWORK_SITE_LIST = list;
	}
	
	

}
