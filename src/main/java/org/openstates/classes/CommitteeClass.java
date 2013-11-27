package org.openstates.classes;

import org.openstates.api.ArgMap;
import org.openstates.api.MethodMap;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;
import org.openstates.data.Committee;
import org.openstates.data.Committees;

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
	 * This method allows searching by a number of fields:
	 * Committee objects returned by this method do not include the list of members by default.
	 * 
	 * @param state
	 * @param chamber
	 * @return {@link Committees} 
	 */
	public Committees search(String state, String chamber) throws OpenStatesException {
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
