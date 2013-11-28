package org.openstates.classes;

import org.openstates.api.MethodMap;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;
import org.openstates.data.Metadata;
import org.openstates.data.MetadataOverview;

/**
 * Accesses the State <a href="http://sunlightlabs.github.io/openstates-api/metadata.html">Metadata</a> methods.
 * 
 * <pre>
 * There are two methods available for access to general metadata:
 * Method                Description
 * Metadata Overview     Get list of all states with data available and basic metadata about their status.
 * State Metadata        Get detailed metadata for a particular state.
 * </pre>
 */
public class MetadataClass extends ClassesBase {

	/**
	 * Constructor for testing purposes.
	 * 
	 * @param api
	 */
	public MetadataClass(OpenStatesAPI api) {
		super(api);
	}

	/**
	 * Default constructor
	 */
	public MetadataClass() throws OpenStatesException {
		super();
	}

	/**
	 * Get list of all states with data available and basic metadata about their status.
	 * 
	 * @return {@link MetadataOverview}
	 */
	public MetadataOverview overview() throws OpenStatesException {
		return api.query(new MethodMap("metadata"), null, MetadataOverview.class);
	}

	/**
	 * Get detailed metadata for a particular state.
	 * 
	 * @param state
	 * @return {@link Metadata}
	 */
	public Metadata state(String state) throws OpenStatesException {
		return api.query(new MethodMap("metadata", state), null, Metadata.class);
	}

}
