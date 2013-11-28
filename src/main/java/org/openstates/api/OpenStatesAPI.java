package org.openstates.api;

/**
 * Interface for the query method. Used by testing.  
 */
public interface OpenStatesAPI {
	public <T> T query(MethodMap methodMap, ArgMap argMap, Class<T> responseType) throws OpenStatesException;
}
