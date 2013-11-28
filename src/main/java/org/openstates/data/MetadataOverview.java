package org.openstates.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Holds Metadata Overview Details.
 * 
 * <pre>
 * lower_chamber_name, 
 * lower_chamber_title, 
 * lower_chamber_term, 
 * upper_chamber_name, 
 * upper_chamber_title, 
 * upper_chamber_term, 
 * name, 
 * abbreviation, 
 * feature_flags, 
 * chambers.
 * </pre> 
 *
 */
public class MetadataOverview extends ArrayList<MetadataOverview.Data> {
	private static final long serialVersionUID = 3508588106112659421L;

	public static class Data extends DataBase {
		public String lower_chamber_name;
		public String lower_chamber_title;
		public String lower_chamber_term;
		public String upper_chamber_name;
		public String upper_chamber_title;
		public String upper_chamber_term;
		public String name;
		public String abbreviation;
		public List<String> feature_flags;
		public Map<String, Ch> chambers;
		
		public static class Ch extends DataBase {
			public String name;
			public String title;
			public String term;
		}
	}
}
