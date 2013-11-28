package org.openstates.data;

import java.util.Date;
import java.util.List;

/**
 * Holds Committee specific details.
 *
 * <pre>
 * id, 
 * state, 
 * committee, 
 * subcommittee, 
 * chamber, 
 * type, 
 * parent_id, 
 * country, 
 * created_at, 
 * updated_at, 
 * level, 
 * votesmart_id, 
 * contact_info, 
 * aide, 
 * comm_type, 
 * members, 
 * sources. 
 * </pre>
 */
public class Committee {
	public String id;
	public String state;
	public String committee;
	public String subcommittee;
	public String chamber;
	public String type;
	public String parent_id;
	public String country;
	public Date created_at;
	public Date updated_at;
	public String level;
	public String votesmart_id;
	public String contact_info;
	public String aide;
	public String comm_type;
	public List<Member> members;
	public List<Source> sources;

	public static class Member {
		public String role;
		public String name;
		public String leg_id;
	}
	
	public static class Source {
		public String url;
	}
}
