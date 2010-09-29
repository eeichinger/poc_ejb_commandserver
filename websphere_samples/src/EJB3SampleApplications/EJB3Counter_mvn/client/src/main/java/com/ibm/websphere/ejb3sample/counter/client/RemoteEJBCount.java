package com.ibm.websphere.ejb3sample.counter.client;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.Context;

import com.ibm.websphere.ejb3sample.counter.RemoteCounter;

public class RemoteEJBCount {
	public static Context ctx;
//	private static String jndiNameStateless = "EJB3CounterSample/EJB3Beans.jar/StatelessCounterBean/com/ibm/websphere/ejb3sample/counter/RemoteCounter";
	
	@EJB(beanName="StatelessCounterBean")
	private static RemoteCounter statelessCounter;
	
	public static void main(String[] args) {
		
		System.out.println("\nStateless session bean count is " + statelessCounter.getTheValue());
		
		try {
			char option = ' ';
			while (option != '0') {
				System.out.println("\n\nEnter a number for the operation:"
						+ "\n\t0 - Exit"
						+ "\n\t1 - Increment the stateless session bean");
				option = (char) System.in.read();
				switch (option) {
				case '0':
					break;
				case '1':
					System.out.println("\nStateless session bean count is " + statelessCounter.increment());
					break;
				default:
					System.out.println("\nInvalid option - " + option);
					break;
				}
				System.in.skip(4);
			}
		} 
		catch (IOException e) {
			System.out.println("Unexpected exception:");
			e.printStackTrace();
		}
		
	}
}
