package com.gtek.handlers;

import java.net.InetAddress;
import java.util.ArrayList;
import com.gtek.objects.Device;
import com.gtek.util.Console;

public class Pinger extends Thread {
	
	private ArrayList<Device> NETWORK_SITE_LIST; // Network Site list
	
	
	
	/**
	 * CONSTRUCTOR
	 * Set the list of network sites to be monitored
	 * by the thread.
	 * 
	 * @param list
	 */
	public Pinger(ArrayList<Device> list) {
		this.setList(list);
	}

	/**
	 * Thread run
	 */
	@Override
	public void run() {
		
		try {
			// Check status by ICMP ECHO
			//Console.log("THREAD: " + Thread.currentThread().getId() + " | SITE SIZE: " + NETWORK_SITE_LIST.size());
			for(Device dev : NETWORK_SITE_LIST) {
				
				boolean isUp = this.isReachable(dev.getSubnet());
				//if(!isUp) Console.log(tower.getName() + " is down!");
				// Set status and time down in minutes
				dev.setStatus(!isUp);
				if(isUp) {
					dev.resetDownTime();
				} else {
					dev.updateDownTime();
				}
				// Update DB with the result
				Database.UpdateDatabase(dev.getCollection(), dev);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Use inet address to use ICMP 
	 * ECHO Requests to get the boolean
	 * status for an address.
	 * 
	 * @param ip
	 * @return boolean
	 */
	private boolean isReachable(String ip) {
		
		try {
			
			InetAddress address = InetAddress.getByName(ip); // Create inet address
			return address.isReachable(250); // Ping address
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Set the array list of network site
	 * objects.
	 * 
	 * @param list
	 */
	public void setList(ArrayList<Device> list) {
		this.NETWORK_SITE_LIST = list;
	}
	
}
