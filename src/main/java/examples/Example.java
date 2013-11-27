package examples;

import org.openstates.classes.MetadataClass;
import org.openstates.data.MetadataOverview;

public class Example {
	
	public static void main(String... args) throws Exception {
		// create a class to query
		MetadataClass metadataClass = new MetadataClass();
		
		// if you want to turn caching off, uncomment this line
//		OpenStates.checkCacheFirst(false);
		
		// Metadata overview ... for all states
		MetadataOverview overview = metadataClass.overview();
		
		// print the abbreviation for the first state 
		System.out.println("The abbreviation for " + overview.get(0).name + " is " + overview.get(0).abbreviation);
	}

}
