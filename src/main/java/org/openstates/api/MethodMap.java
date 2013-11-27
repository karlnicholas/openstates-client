package org.openstates.api;

import java.util.ArrayList;

/**
 * You can either add arguments in pairs in the constructor or you
 * can add them manually after you construct an empty map.
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
