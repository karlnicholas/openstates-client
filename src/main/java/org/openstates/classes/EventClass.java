package org.openstates.classes;

import org.openstates.api.ArgMap;
import org.openstates.api.MethodMap;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;
import org.openstates.data.Event;
import org.openstates.data.Events;

/**
 * Accesses the <a href="http://sunlightlabs.github.io/openstates-api/events.html">Events</a> methods.
 * 
 * <pre>
 * Events are not available in all states, to ensure that events are available check the feature_flags list in a states' metadata.
 * 
 * There are two methods available for event data:
 * Method        Description
 * Event Search  Search events by state and type.
 * Event Detail  Get full detail for event.
 * </pre>
 * 
 */
public class EventClass extends ClassesBase {

	/**
	 * Constructor for testing purposes.
	 * 
	 * @param api
	 */
	public EventClass(OpenStatesAPI api) {
		super(api);
	}

	/**
	 * Default constructor
	 */
	public EventClass()  throws OpenStatesException {
		super();
	}

	/**
	 * Event Search
	 * This method allows searching by state:
	 * 
	 * @param state
	 * @return {@link Events}
	 */
	public Events searchByState(String state) throws OpenStatesException {
		return api.query(
			new MethodMap("events"), 
			new ArgMap("state", state), 
			Events.class
		);
	}
	
	/**
	 * Event Search
	 * This method allows searching by a number of fields:
	 * 
	 * @param state
	 * @param type
	 * @return {@link Events}
	 */
	public Events search(String state, String type) throws OpenStatesException {
		return api.query(
			new MethodMap("events"), 
			new ArgMap("state", state, "type", type), 
			Events.class
		);
	}

	/**
	 * 
	 * @param id
	 * @return {@link Event}
	 */
	public Event detail(String id) throws OpenStatesException {
		return api.query(new MethodMap("events", id), null, Event.class);
	}
}
