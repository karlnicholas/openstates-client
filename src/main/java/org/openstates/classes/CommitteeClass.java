package org.openstates.classes;

import org.openstates.api.ArgMap;
import org.openstates.api.MethodMap;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;
import org.openstates.data.Committee;
import org.openstates.data.Committees;

/**
 * This class accesses the <a href="http://sunlightlabs.github.io/openstates-api/committees.html">Committees</a> methods of the OpenStates API.
 * 
 *  <pre>
 *  There are two methods available for committee data:
 *  Method	              Description
 *  Committee Search      Search committees by any of their attributes.
 *  Committee Detail      Get full detail for committee, including all members.
 * </pre>
 */
public class CommitteeClass extends ClassesBase {

	/**
	 * Constructor for testing purposes.
	 * 
	 * @param api
	 */
	public CommitteeClass(OpenStatesAPI api) {
		super(api);
	}

	/**
	 * Default constructor
	 */
	public CommitteeClass() throws OpenStatesException {
		super();
	}

	/**
	 * Committee Search
	 * 
	 * @param state
	 * 
	 * @return Committee objects returned by this method do not include the list of members by default.
	 */
	public Committees searchByState(String state) throws OpenStatesException {
		return api.query(
			new MethodMap("committees"), 
			new ArgMap("state", state), 
			Committees.class
		);
	}
	
	/**
	 * Committee Search
	 * This method allows searching by state and chamber.
	 * Committee objects returned by this method do not include the list of members by default.
	 * 
	 * @param state
	 * @param chamber
	 * @return {@link Committees} 
	 */
	public Committees searchByStateChamber(String state, String chamber) throws OpenStatesException {
		return api.query(
			new MethodMap("committees"), 
			new ArgMap("state", state, "chamber", chamber), 
			Committees.class
		);
	}

	/**
	 * Committee Search
	 * This method allows searching by a number of fields:
	 * Committee objects returned by this method do not include the list of members by default.
	 * 
	 * @param state
	 * @param chamber
	 * @param committee
	 * @param subcommittee
	 * 
	 * @return {@link Committees}
	 * @throws OpenStatesException
	 */
	public Committees search(String state, String chamber, String committee, String subcommittee) throws OpenStatesException {
		return api.query(
			new MethodMap("committees"), 
			new ArgMap(
				"state", state, 
				"chamber", chamber, 
				"committee", committee, 
				"subcommittee", subcommittee
			), 
			Committees.class
		);
	}
	
	/**
	 * Committee Detail
	 * This method returns the full committee object given a committee id.
	 * 
	 * @param id
	 * @return {@link Committee} object given a committee id.
	 */
	public Committee detail(String id) throws OpenStatesException {
		return api.query(new MethodMap("committees", id), null, Committee.class);
	}
}
