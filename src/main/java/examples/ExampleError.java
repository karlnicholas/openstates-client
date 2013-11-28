package examples;

import org.openstates.classes.BillClass;

/**
 * This program is a simple demonstration of errors.
 * 
 * <pre>
 * This program is a very simple demonstration of this RESTful Client API.
 * It requires a ResourceBundle named OpenStates. This means a file
 * named OpenStates.properties must be found in your class path, such
 * as your resources directory. This OpenStates client reads two 
 * keys from the resource bundle, one of which is mandatory.
 * These are in the form of key=value, one on each line.
 * The keys are:
 * 
 * apikey    (Mandatory) get from OpenStates.org
 * cache     (Optional) such as c:/tmp/OpenStatesCache
 * 
 * For Example:
 * apikey=123easyasabcbabyyouandme
 * cache=c:/tmp/openstates
 * </pre>
 *
 */
public class ExampleError {
	
	public static void main(String... args) throws Exception {
		//Bill class
		BillClass billClass = new BillClass();

		// try to get a bad id
		billClass.detail("tx", "833", "bad id");
	}

}
