package org.openstates.data;

import java.util.Date;
import java.util.List;

/**
 * Holds Event details.
 * Implements Comparable.
 * 
 * <pre>
 * id, 
 * created_at, 
 * updated_at, 
 * description, 
 * when, 
 * end, 
 * location, 
 * type, 
 * state, 
 * session, 
 * participants, 
 * sources, 
 * documents, 
 * related_bills, 
 * timezone.
 * </pre>
 *
 */
public final class Event extends DataBase implements Comparable<Event> {
	public String id;
	public Date created_at;
	public Date updated_at;
	public String description;
	public Date when;
	public Date end;
	public String location;
	public String type;
	public String state;
	public String session;
	public List<Participant> participants;
	public List<Source> sources;
	public List<String> documents;
	public List<String> related_bills;
	public String timezone;

	public static class Participant extends DataBase {
		public String id;
		public String type;
		public String participant_type;
		public String participant;
		public String chamber;
	}
	
	public static class Source extends DataBase {
		public String url;
	}

	@Override
	public int compareTo(Event o) {
		return id.compareTo(o.id);
	}
}
