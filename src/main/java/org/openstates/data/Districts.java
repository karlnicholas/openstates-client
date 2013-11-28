package org.openstates.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a list of {@link District} details.
 *
 */
public class Districts extends ArrayList<Districts.Dist> {
	private static final long serialVersionUID = -6445878527185827758L;

	public static class Dist {
		public String abbr;
		public String boundary_id;
		public String chamber;
		public String id;
		public String name;
		public String num_seats;
		public List<Leg> legislators;

		public static class Leg {
			public String full_name;
			public String leg_id;
		}
	}
}
