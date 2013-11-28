package org.openstates.classes;

import org.openstates.api.MethodMap;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;
import org.openstates.data.District;
import org.openstates.data.Districts;

/**
 * Accesses the <a href="http://sunlightlabs.github.io/openstates-api/districts.html">Districts</a> methods of the OpenStates.api
 *  
 *  <pre>
 *  Open States makes it possible to get a listing of all districts or retrieve the boundary of a given district.
 *  There are two methods available for district data:
 *  Method                      Description
 *  District Search             List districts for state (and optionally filtered by chamber).
 *  District Boundary Lookup    Get geographic boundary for a district.
 *  </pre>
 *
 */
public class DistrictClass extends ClassesBase {

	/**
	 * Constructor for testing purposes.
	 * 
	 * @param api
	 */
	public DistrictClass(OpenStatesAPI api) {
		super(api);
	}

	/**
	 * Default constructor
	 */
	public DistrictClass()  throws OpenStatesException {
		super();
	}

	/**
	 * List districts for state.
	 * 
	 * @param state
	 * @return {@link Districts}
	 */
	public Districts searchByState(String state) throws OpenStatesException {
		return api.query(new MethodMap("districts", state), null, Districts.class);
	}

	/**
	 * List districts for state and chamber.
	 * 
	 * @param state
	 * @param chamber
	 * @return {@link Districts}
	 */
	public Districts search(String state, String chamber) throws OpenStatesException {
		return api.query(new MethodMap("districts", state, chamber), null, Districts.class);
	}

	/**
	 * Get geographic boundary for a district.
	 * 
	 * @param boundary_id
	 * @return {@link District} 
	 */
	public District boundaryLookup(String boundary_id) throws OpenStatesException {
		return api.query(new MethodMap("districts", "boundary", boundary_id ), null, District.class);
	}

}
