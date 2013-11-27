package org.openstates.classes;

import org.openstates.api.ArgMap;
import org.openstates.api.MethodMap;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;
import org.openstates.data.Legislator;
import org.openstates.data.Legislators;

public class LegislatorClass extends ClassesBase {

	/**
	 * Constructor for testing purposes.
	 * 
	 * @param api
	 */
	public LegislatorClass(OpenStatesAPI api) {
		super(api);
	}

	/**
	 * Default constructor
	 */
	public LegislatorClass()  throws OpenStatesException {
		super();
	}

	/**
	 * @param state Filter by state.
	 * 
	 */
	public Legislators searchByState(
			String state 
	) throws OpenStatesException {
		return api.query(
			new MethodMap("legislators"), 
			new ArgMap( "state", state ), 
			Legislators.class
		);
	}
	
	/**
	 * @param state Filter by state.
	 * @param active 'true' (default) to only include current legislators, 'false' will include all legislators
	 * 
	 */
	public Legislators searchByStateActive(
			String state, 
			Boolean active 
	) throws OpenStatesException {
		return api.query(
			new MethodMap("legislators"), 
			new ArgMap(
				"state", state, 
				"active", active.toString()
			), 
			Legislators.class
		);
	}
	

	/**
	 * @param state Filter by state.
	 * @param term Only legislators that have a role in a certain term.
	 * 
	 */
	public Legislators searchByStateTerm(
			String state, 
			String term 
	) throws OpenStatesException {
		return api.query(
			new MethodMap("legislators"), 
			new ArgMap(
				"state", state, 
				"term", term 
			), 
			Legislators.class
		);
	}
	
	/**
	 * Pass null for parameters that are not being filtered.
	 * 
	 * @param state Filter by state.
	 * @param active 'true' (default) to only include current legislators, 'false' will include all legislators
	 * @param term Only legislators that have a role in a certain term.
	 * @param chamber Only legislators with a role in the specified chamber.
	 * @param district Only legislators that have represented the specified district.
	 * @param party Only legislators that have been associated with a specified party
	 * @param first_name Filter by first name.
	 * @param last_name Filter by last name.
	 * 
	 */
	public Legislators search(
			String state, 
			Boolean active, 
			String term, 
			String chamber,
			String district, 
			String party, 
			String first_name, 
			String last_name 
	) throws OpenStatesException {
		return api.query(
			new MethodMap("legislators"), 
			new ArgMap(
				"state", state, 
				"first_name", first_name, 
				"last_name", last_name, 
				"chamber", chamber, 
				"active", active==null?null:active.toString(), 
				"term", term, 
				"party", party, 
				"district", district
			), 
			Legislators.class
		);
	}
	
	public Legislators geoLookup(String longitude, String latitude) throws OpenStatesException {
		return api.query(new MethodMap("legislators"), new ArgMap("long", longitude, "lat", latitude), Legislators.class);
	}

	public Legislator detail(String id) throws OpenStatesException {
		return api.query(new MethodMap("legislators", id), null, Legislator.class);
	}

}
