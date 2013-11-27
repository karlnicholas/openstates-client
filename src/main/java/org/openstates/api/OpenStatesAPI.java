package org.openstates.api;

public interface OpenStatesAPI {
	
	public <T> T query(MethodMap methodMap, ArgMap argMap, Class<T> responseType) throws OpenStatesException;
}
