package org.openstates.test;


import static org.junit.Assert.*;

import org.junit.Test;
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
import org.openstates.testapi.TestOpenStatesBase;

/**
 * The goal here is just to make (most) every possible call.
 *
 */
public class TestAllInterfaces extends TestOpenStatesBase {

	@Test
	public void test() throws Exception {
		// create a class to query
		MetadataClass metadataClass = new MetadataClass(this);

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
		assertEquals( metadata.abbreviation, "tx" );
		assertEquals( metadata.capitol_timezone, "America/Chicago" );
		
		// LegislatorsClass
		LegislatorClass legislatorClass = new LegislatorClass(this);
				
		// legislators by state
		Legislators legislators = legislatorClass.searchByState(metadata.abbreviation);
		assertEquals( legislators.size(), 181 );
		
		// get active legislators for state
		legislators = legislatorClass.search(metadata.abbreviation, null, null, "upper", null, null, null, null);
		assertEquals( legislators.size(), 31 );

		// active legislators by state
		legislators = legislatorClass.searchByStateActive(metadata.abbreviation, true);
		assertEquals( legislators.size(), 181 );

		// a specific legislator
		Legislator legislator = legislatorClass.detail(legislators.get(0).id);
		assertEquals( legislator.full_name, "Paul Workman" );
		
		// committee class
		CommitteeClass committeeClass = new CommitteeClass(this);
		
		// get committees for upper chamber for state
		Committees committees = committeeClass.search(metadata.abbreviation, "upper", null, null);
		assertEquals( committees.size(), 24 );
		
		// get committees for for state
		committees = committeeClass.searchByState(metadata.abbreviation);
		assertEquals( committees.size(), 74 );

		// specific committee
		Committee committee = committeeClass.detail(committees.get(0).id);
		assertEquals( committee.committee, "Appropriations - S/C on Articles VI, VII, & VIII" );
		
		// District class
		DistrictClass districtClass = new DistrictClass(this);
		
		// get committees for upper chamber for state
		Districts districts = districtClass.search(metadata.abbreviation, "upper");
		assertEquals( districts.size(), 31 );
		
		// get committees for for state
		districts = districtClass.searchByState(metadata.abbreviation);
		assertEquals( districts.size(), 181 );
		
		// District class
		EventClass eventClass = new EventClass(this);
		
		// get committees for for state
		Events events = eventClass.searchByState(metadata.abbreviation);
		assertEquals( events.size(), 4 );
		
		// specific event
		Event event = eventClass.detail(events.get(0).id);
		assertEquals( event.description, "Defense & Veterans' Affairs" );
		
		//Bill class
		BillClass billClass = new BillClass(this);
		Bills bills = billClass.searchByQueryWindow(metadata.abbreviation, "abortion", "term", null, null);
		assertEquals( bills.size(), 69 );

		// bill detail
		Bill bill = billClass.detail(bills.get(0).state, bills.get(0).session, bills.get(0).bill_id);
		assertEquals( bill.title, "Relating to a pregnant woman's completion of a resource awareness session before performance or inducement of an abortion; providing penalties." );
		
	}
}
