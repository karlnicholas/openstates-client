package org.openstates.data;

import java.util.Date;
import java.util.List;

/**
 * Holds all of the detail of a specific bill.
 * Implements Comparable.
 * 
 * <pre>
 * chamber, 
 * bill_id, 
 * title, 
 * type, 
 * alternate_titles, 
 * alternate_bill_ids, 
 * created_at, 
 * updated_at, 
 * versions, 
 * documents, 
 * sponsors, 
 * actions, 
 * votes, 
 * sources, 
 * id, 
 * companions, 
 * state, 
 * subjects, 
 * action_dates, 
 * level, 
 * country, 
 * scraped_subjects, 
 * summary.
 * </pre>
 */
public class Bill extends DataBase implements Comparable<Bill>{	

	public String session;
	public String chamber;
	public String bill_id;
	public String title;
	public List<String> type;
	public List<String> alternate_titles;
	public List<String> alternate_bill_ids;
	public Date created_at;
	public Date updated_at;
	public List<Version> versions;
	public List<Document> documents;
	public List<Sponsor> sponsors;
	public List<Action> actions;
	public List<Vote> votes;
	public List<Source> sources;
	public String id;
	public List<Companion> companions;
	public String state;
	public List<String> subjects;
	public ActionDate action_dates;
	public String level;
	public String country;
	public List<String> scraped_subjects;
	public String summary;
	

	public static class ActionDate extends DataBase {
		public Date passed_upper;
		public Date passed_lower;
		public Date first;
		public Date last;
		public Date signed;
	}

	public static class Companion extends DataBase {
		public String chamber;
		public String session;
		public String internal_id;
		public String bill_id;
	}

	public static class Source extends DataBase {
		public String url;
		public Date retrieved;
	}

	public static class Version extends DataBase {
		public String name;
		public String url;
		public String title;
		public String mimetype;
		public String doc_id;
	}
	public static class Document extends DataBase {
		public String name;
		public String url;
		public String mimetype;
		public String doc_id;
	}

	public static class Sponsor extends DataBase {
		public String type;
		public String name;
		public String chamber;
		public String leg_id;
		public String committee_id;
		public String official_type;
	}
	public static class Action extends DataBase {
		public String action;
		public String actor;
		public Date date;
		public List<String> type;
		public List<RelatedEntity> related_entities;
		public String committee;
		public List<String> related_votes;

		public static class RelatedEntity extends DataBase {
			public String id;
			public String type;
			public String name;
		}
	}
	public static class Vote extends DataBase {
		public String chamber;
		public String committee;
		public Date date;
		public String motion;
		public boolean passed;
		public int yes_count;
		public List<Leg> yes_votes;
		public int no_count;
		public List<Leg> no_votes;
		public int other_count;
		public List<Leg> other_votes;
		public List<Source> sources;
		public String id;
		public String state;
		public String session;
		public String vote_id;
		public String type;
		public String bill_id;
		public String committee_id;
		public String bill_chamber;
		public String record;
		public String method;

		public static class Leg extends DataBase {
			public String leg_id;
			public String name;
		}
		
		public static class Source extends DataBase {
			public String url;
			public Date retrieved;
		}
	}

	@Override
	public int compareTo(Bill o) {
		return id.compareTo(o.id); 
	}
}