package org.openstates.api;

import java.util.TreeMap;

/**
 * Not for user consumption.
 * 
 */
public class ArgMap extends TreeMap<String, String> {

	private static final long serialVersionUID = -2664255408377082118L;
	
	/**
	 * Instantiates a new arg map.
	 *
	 * @param args the args
	 */
	public ArgMap(String... args) {
		for ( int i=0; i<args.length; i+=2) {
			this.put(args[i], args[i+1]);
		}
	}
}
