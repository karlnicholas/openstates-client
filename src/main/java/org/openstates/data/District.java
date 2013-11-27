package org.openstates.data;

public class District extends Districts.Dist {
	public Float[][] bbox;
	public Region region;
	public Float[][][][] shape;
	
	public static class Region {
		public String lon_delta;
		public String center_lon;
		public String lat_delta;
		public String center_lat;
	}
}