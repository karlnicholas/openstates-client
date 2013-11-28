package org.openstates.classes;

import java.util.ResourceBundle;

import org.openstates.api.OpenStates;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;

/**
 * Base class for all other classes.
 * 
 * <pre>
 * Base class for all other classes. 
 * 
 * The user has no reason to use this class directly. It
 * is used as the base class for all of the other 
 * 'Classes' classes.
 * </pre>
 */
public class ClassesBase {
	
	public static final String OpenStatesResource = "openstates";
	protected static OpenStatesAPI api = null;
	
	/**
	 * This class is supports the other interface classes. 
	 * Default constructor
	 */
	public ClassesBase() throws OpenStatesException {
		if ( ClassesBase.api == null ) ClassesBase.api = new OpenStates(ResourceBundle.getBundle(OpenStatesResource));
	}
	
	/**
	 * Constructor for testing purposes
	 * 
	 * @param testApi
	 */
	public ClassesBase(OpenStatesAPI testApi) {
		if ( ClassesBase.api == null ) ClassesBase.api = testApi;
	}
}
