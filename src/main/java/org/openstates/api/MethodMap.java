package org.openstates.api;

import java.util.ArrayList;

/**
 * Not for user consumption.
 * 
 */
public class MethodMap extends ArrayList<String> {
	private static final long serialVersionUID = 2708047616695672571L;

	/**
	 * Instantiates a new arg map.
	 *
	 * @param args the args
	 */
	public MethodMap(String... args ) {
		for ( int i=0; i<args.length; i++) {
			this.add(args[i]);
		}
	}
}
