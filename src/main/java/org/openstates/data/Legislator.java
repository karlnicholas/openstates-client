package org.openstates.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Holds Legislator details.
 * Implements Comparable.
 * 
 * <pre>
 * id, 
 * leg_id, 
 * country, 
 * level, 
 * transparencydata_id, 
 * csrfmiddlewaretoken, 
 * nimsp_id, 
 * full_name, 
 * first_name, 
 * middle_name, 
 * last_name, 
 * suffixes, 
 * nickname, 
 * active, 
 * created_at, 
 * updated_at, 
 * roles, 
 * old_roles, 
 * offices, 
 * sources, 
 * votesmart_id, 
 * nimsp_candidate_id, 
 * state, 
 * district, 
 * chamber, 
 * party, 
 * photo_url, 
 * office_phone, 
 * office_address, 
 * email, 
 * url, 
 * suffix, 
 * notice, 
 * all_ids, 
 * occupation.
 * </pre>
 *
 */
public class Legislator extends DataBase implements Comparable<Legislator> {
	public String id;
	public String leg_id;
	public String country;
	public String level;
	public String transparencydata_id;
	public String csrfmiddlewaretoken;
	public String nimsp_id;
	public String full_name;
	public String first_name;
	public String middle_name;
	public String last_name;
	public String suffixes;
	public String nickname;
	public boolean active;
	public Date created_at;
	public Date updated_at;
	public List<Role> roles;
	public Map<String, List<Role>> old_roles;
	public List<Office> offices;
	public List<Source> sources;
	public String votesmart_id;
	public String nimsp_candidate_id;
	public String state;
	public String district;
	public String chamber;
	public String party;
	public String photo_url;
	public String office_phone;
	public String office_address;
	public String email;
	public String url;
	public String suffix;
	public String notice;
	public List<String> all_ids;
	public String occupation;
	
	public static class Office extends DataBase {
		public String type;
		public String name;
		public String address;
		public String phone;
		public String fax;
		public String email;
	}

	public static class Role extends DataBase {
		public String chamber;
		public String state;
		public String country;
		public String term;
		public String type;
		public String committee;
		public String committee_id;		
		public String subcommittee;
		public String district;
		public String party;
		public Date start_date;
		public Date end_date;
		public String level;
		public String position;
		public List<String> other_parties;
	}
	
	public static class Source extends DataBase {
		public String url;
		public Date retrieved;
	}

	@Override
	public int compareTo(Legislator o) {
		return id.compareTo(o.id);
	}

}