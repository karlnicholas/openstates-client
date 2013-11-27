package examples;

import org.openstates.classes.BillClass;
import org.openstates.classes.CommitteeClass;
import org.openstates.classes.DistrictClass;
import org.openstates.classes.EventClass;
import org.openstates.classes.LegislatorClass;
import org.openstates.classes.MetadataClass;
import org.openstates.data.Bill;
import org.openstates.data.Bills;
import org.openstates.data.Committee;
import org.openstates.data.Committees;
import org.openstates.data.Districts;
import org.openstates.data.Event;
import org.openstates.data.Events;
import org.openstates.data.Legislator;
import org.openstates.data.Legislators;
import org.openstates.data.Metadata;
import org.openstates.data.MetadataOverview;

public class AllThingsTexas {

	public static void main(String... args) throws Exception {
		// create a class to query
		MetadataClass metadataClass = new MetadataClass();

		// if you want to turn caching off, uncomment this line
		// OpenStates.checkCacheFirst(false);
		
		// Metadata Overview ... for all states
		MetadataOverview overview = metadataClass.overview();

		// get the metadata for state
		Metadata metadata = null;
		for (MetadataOverview.Data overviewState : overview) {
			if (overviewState.name.equals("Texas")) {
				metadata = metadataClass.state(overviewState.abbreviation);
				break;
			}
		}
		System.out.println("The abbreviation for Timezone for " + metadata.abbreviation + " is " + metadata.capitol_timezone);
		
		// LegislatorsClass
		LegislatorClass legislatorClass = new LegislatorClass();
				
		// legislators by state
		Legislators legislators = legislatorClass.searchByState(metadata.abbreviation);
		System.out.println("" + legislators.size() + " legislators found.");
		
		// get active legislators for state
		legislators = legislatorClass.search(metadata.abbreviation, null, null, "upper", null, null, null, null);
		System.out.println("" + legislators.size() + " legislators found in upper chamber.");

		// active legislators by state
		legislators = legislatorClass.searchByStateActive(metadata.abbreviation, true);
		System.out.println("" + legislators.size() + " active legislators found.");

		// a specific legislator
		Legislator legislator = legislatorClass.detail(legislators.get(0).id);
		System.out.println("First Legislator is " + legislator.full_name );
		
		// committee class
		CommitteeClass committeeClass = new CommitteeClass();
		
		// get committees for upper chamber for state
		Committees committees = committeeClass.search(metadata.abbreviation, "upper", null, null);
		System.out.println("" + committees.size() + " committees found in upper chamber.");
		
		// get committees for for state
		committees = committeeClass.searchByState(metadata.abbreviation);
		System.out.println("" + committees.size() + " committees found.");

		// specific committee
		Committee committee = committeeClass.detail(committees.get(0).id);
		System.out.println("First committee called " + committee.committee );
		
		// District class
		DistrictClass districtClass = new DistrictClass();
		
		// get committees for upper chamber for state
		Districts districts = districtClass.search(metadata.abbreviation, "upper");
		System.out.println("" + districts.size() + " districts found in upper chamber.");
		
		// get committees for for state
		districts = districtClass.searchByState(metadata.abbreviation);
		System.out.println("" + districts.size() + " districts found.");
		
		// District class
		EventClass eventClass = new EventClass();
		
		// get committees for for state
		Events events = eventClass.searchByState(metadata.abbreviation);
		System.out.println("" + events.size() + " events found.");
		
		// specific event
		Event event = eventClass.detail(events.get(0).id);
		System.out.println("First event is " + event.description );
		
		//Bill class
		BillClass billClass = new BillClass();
		Bills bills = billClass.searchByQueryWindow(metadata.abbreviation, "abortion", "term", null, null);
		System.out.println("" + bills.size() + " bills found.");

		// bill detail
		Bill bill = billClass.detail(bills.get(0).state, bills.get(0).session, bills.get(0).bill_id);
		System.out.println("First bill title: " + bill.title);
		
	}

}
