package org.openstates.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Holds Metadata details.
 * 
 * <pre>
 * abbreviation, 
 * capitol_timezone, 
 * capitol_maps, 
 * chambers, 
 * feature_flags, 
 * id, 
 * latest_update, 
 * latest_csv_date, 
 * latest_csv_url, 
 * latest_json_date, 
 * latest_json_url, 
 * legislature_name, 
 * legislature_url, 
 * name, 
 * session_details, 
 * terms, 
 * lower_chamber_name, 
 * lower_chamber_title, 
 * lower_chamber_term, 
 * upper_chamber_name, 
 * upper_chamber_title, 
 * upper_chamber_term.
 * </pre>
 *
 */
public class Metadata {
	public String abbreviation;
	public String capitol_timezone;
	public List<Maps> capitol_maps;
	public Map<String, Chamber> chambers;
	public List<String> feature_flags;
	public String id;
	public Date latest_update;
	public Date latest_csv_date;
	public String latest_csv_url;
	public Date latest_json_date;
	public String latest_json_url;
	public String legislature_name;
	public String legislature_url;
	public String name;
	public Map<String, Session> session_details;
	public List<Term> terms;
	public String lower_chamber_name;
	public String lower_chamber_title;
	public String lower_chamber_term;
	public String upper_chamber_name;
	public String upper_chamber_title;
	public String upper_chamber_term;

	public static class Maps {
		public String url;
		public String name;
	}
	public static class Chamber {
		public String name;
		public String title;
	}

	public static class Session {
		public String type;
		public String display_name;
		public Date start_date;
		public Date end_date;
		public String end_year;
		public String start_year;
	}

	public static class Term {
		public String type;
		public String end_year;
		public String start_year;
		public Date start_date;
		public String name;
		public List<String> sessions;
	}
}
