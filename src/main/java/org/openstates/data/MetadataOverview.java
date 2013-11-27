package org.openstates.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetadataOverview extends ArrayList<MetadataOverview.Data> {
	private static final long serialVersionUID = 3508588106112659421L;

	public static class Data {
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
		
		public static class Ch {
			public String name;
			public String title;
			public String term;
		}
	}
}
