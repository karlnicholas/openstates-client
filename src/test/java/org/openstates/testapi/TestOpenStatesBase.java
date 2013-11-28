package org.openstates.testapi;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openstates.api.ArgMap;
import org.openstates.api.MethodMap;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;

/**
 * Base class for all Test classes.
 *
 */
public class TestOpenStatesBase implements OpenStatesAPI {
	protected static TestOpenStates api;
	
	@BeforeClass
	public static void setup() {
		try {
			api = new TestOpenStates(ResourceBundle.getBundle("openstates"));
		} catch (OpenStatesException e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> T query(MethodMap methodMap, ArgMap argMap, Class<T> responseType) throws OpenStatesException {
		T response = api.query(methodMap, argMap, responseType); 
		return response;
	}

	@AfterClass
	public static void testGeneralInfo() {
		api.destroy();
	}
}
