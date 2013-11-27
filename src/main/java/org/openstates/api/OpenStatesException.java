package org.openstates.api;

public class OpenStatesException extends Exception {
	private static final long serialVersionUID = 6179623710020364382L;

	public OpenStatesException (Exception e ) {
		super(e);
	}
	
	public OpenStatesException (String msg) {
		super(msg);
	}
	
	public OpenStatesException(String msg, Exception e ) {
		super(msg, e);
	}
}
