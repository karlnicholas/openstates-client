package org.openstates.data;

import java.util.Date;
import java.util.List;

public final class Event {
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

	public static class Participant {
		public String id;
		public String type;
		public String participant_type;
		public String participant;
		public String chamber;
	}
	
	public static class Source {
		public String url;
	}
}
