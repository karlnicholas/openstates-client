package org.openstates.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Holds a list of {@link Committee} details.
 *
 */
public class Committees extends ArrayList<Committees.Cmte> {
	private static final long serialVersionUID = -3782946454018623648L;
	public static class Cmte extends DataBase {
		public String country;
		public String level;
		public String id; 
		public String chamber;
		public Date created_at; 
		public Date updated_at; 
		public String parent_id; 
		public String state; 
		public String subcommittee; 
		public String committee;
		public String votesmart_id;
	}
}
