package org.openstates.data;

/**
 * Holds District details.
 * Implements Comparable.
 * 
 * <pre>
 * abbr, 
 * boundary_id, 
 * chamber, 
 * id, 
 * name, 
 * num_seats, 
 * legislators, 
 * bbox, 
 * region, 
 * shape.
 * </pre> 
 *
 */
public class District extends Districts.Dist implements Comparable<District> {
	public Float[][] bbox;
	public Region region;
	public Float[][][][] shape;
	
	public static class Region extends DataBase {
		public String lon_delta;
		public String center_lon;
		public String lat_delta;
		public String center_lat;
	}

	@Override
	public int compareTo(District o) {
		return id.compareTo(o.id);
	}
}