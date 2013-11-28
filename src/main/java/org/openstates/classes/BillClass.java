package org.openstates.classes;

import org.openstates.api.ArgMap;
import org.openstates.api.MethodMap;
import org.openstates.api.OpenStates;
import org.openstates.api.OpenStatesAPI;
import org.openstates.api.OpenStatesException;
import org.openstates.data.Bill;
import org.openstates.data.Bills;

/**
 * The BillClass accesses the <a href="http://sunlightlabs.github.io/openstates-api/bills.html">Bills</a> methods.
 * 
 * <pre>
 * There are two methods available for bill data:
 * Method	 Description
 * Bill Search	 Search bills by (almost) any of their attributes, or full text.
 * Bill Detail	 Get full detail for bill, including any actions, votes, etc. 
 * </pre>
 *
 */
public class BillClass extends ClassesBase {

	/**
	 * Constructor for testing purposes.
	 * 
	 * @param api
	 */
	public BillClass(OpenStatesAPI api) {
		super(api);
	}

	/**
	 * Default constructor
	 */
	public BillClass()  throws OpenStatesException {
		super();
	}
	
	/**
	 * Bill Search
	 * This method returns just a subset of the bill fields by default.
	 * NOTE: *** This method never uses the cache. Use with caution. ***
	 * 
	 * Filter Parameters
	 * 
	 * The following parameters filter the returned set of bills, at least one must be provided.
	 * Use null for any parameter that is not used.
	 * 
	 * @param state - Only return bills from a given state (e.g. 'nc')
	 * @param updated_since - Only bills updated since a provided date (provided in YYYY-MM-DD format)
	 * 
	 * The API will not return exceedingly large responses, so it may be 
	 * necessary to use page and per_page to control the number of results returned:
	 * @param page - Page of results, each of size per_page (defaults to 1)
	 * @param per_page - Number of results per page, is unlimited unless page is set, in which case it defaults to 50.
	 * 
	 * @return {@link Bills} 
	 */
	public Bills searchByDate(
		String state, 
		String updated_since, 
		String page, 
		String per_page
	) throws OpenStatesException {
		OpenStates.suspendCache();
		Bills bills = api.query( 
			new MethodMap("bills"), 
			new ArgMap(
				"state", state, 
				"updated_since", updated_since, 
				"page", page, 
				"per_page", per_page
			), 
		Bills.class);
		return bills;
	}

	/**
	 * Bill Search
	 * This method returns just a subset of the bill fields by default.
	 * NOTE: *** This method never uses the cache. Use with caution. ***
	 * 
	 * Filter Parameters
	 * 
	 * The following parameters filter the returned set of bills, at least one must be provided.
	 * Use null for any parameter that is not used.
	 * 
	 * @param state - Only return bills from a given state (e.g. 'nc')
	 * @param search_window By default all bills are searched, 
	 *     but if a time window is desired the 
	 *     following options can be passed to search_window:
	 * 		  all - Default, include all sessions.
	 * 	      term - Only bills from sessions within the current term.
	 *        session - Only bills from the current session.
	 *        session:2009 - Only bills from the session named 2009.
	 *        term:2009-2011 - Only bills from the sessions in the 2009-2011 session.
	 *        
	 * The API will not return exceedingly large responses, so it may be 
	 * necessary to use page and per_page to control the number of results returned:
	 * @param page - Page of results, each of size per_page (defaults to 1)
	 * @param per_page - Number of results per page, is unlimited unless page is set, in which case it defaults to 50.
	 * 
	 * @return {@link Bills} 
	 */
	public Bills searchByWindow(
		String state, 
		String search_window,  
		String page, 
		String per_page
	) throws OpenStatesException {
		OpenStates.suspendCache();
		Bills bills = api.query( 
			new MethodMap("bills"), 
			new ArgMap(
				"state", state, 
				"search_window", search_window, 
				"page", page, 
				"per_page", per_page
			), 
		Bills.class);
		return bills;
	}

	/**
	 * Bill Search
	 * This method returns just a subset of the bill fields by default.
	 * NOTE: *** This method never uses the cache. Use with caution. ***
	 * 
	 * Filter Parameters
	 * 
	 * The following parameters filter the returned set of bills, at least one must be provided.
	 * Use null for any parameter that is not used.
	 * 
	 * @param state - Only return bills from a given state (e.g. 'nc')
	 * @param q Only return bills matching the provided full text query.
	 * 
	 * The API will not return exceedingly large responses, so it may be 
	 * necessary to use page and per_page to control the number of results returned:
	 * @param page - Page of results, each of size per_page (defaults to 1)
	 * @param per_page - Number of results per page, is unlimited unless page is set, in which case it defaults to 50.
	 * 
	 * @return {@link Bills} 
	 */
	public Bills searchByQuery(
		String state, 
		String q, 
		String page, 
		String per_page
	) throws OpenStatesException {
		OpenStates.suspendCache();
		Bills bills = api.query( 
			new MethodMap("bills"), 
			new ArgMap(
				"state", state, 
				"q", q, 
				"page", page, 
				"per_page", per_page
			), 
		Bills.class);
		return bills;
	}

	/**
	 * Bill Search
	 * This method returns just a subset of the bill fields by default.
	 * NOTE: *** This method never uses the cache. Use with caution. ***
	 * 
	 * Filter Parameters
	 * 
	 * The following parameters filter the returned set of bills, at least one must be provided.
	 * Use null for any parameter that is not used.
	 * 
	 * @param state - Only return bills from a given state (e.g. 'nc')
	 * @param subject - Only bills categorized by Open States as belonging to this subject.
	 * 
	 * The API will not return exceedingly large responses, so it may be 
	 * necessary to use page and per_page to control the number of results returned:
	 * @param page - Page of results, each of size per_page (defaults to 1)
	 * @param per_page - Number of results per page, is unlimited unless page is set, in which case it defaults to 50.
	 * 
	 * @return {@link Bills} 
	 */
	public Bills searchBySubject(
		String state, 
		String subject, 
		String page, 
		String per_page
	) throws OpenStatesException {
		OpenStates.suspendCache();
		Bills bills = api.query( 
			new MethodMap("bills"), 
			new ArgMap(
				"state", state, 
				"subject", subject, 
				"page", page, 
				"per_page", per_page
			), 
		Bills.class);
		return bills;
	}

	/**
	 * Bill Search
	 * This method returns just a subset of the bill fields by default.
	 * NOTE: *** This method never uses the cache. Use with caution. ***
	 * 
	 * Filter Parameters
	 * 
	 * The following parameters filter the returned set of bills, at least one must be provided.
	 * Use null for any parameter that is not used.
	 * 
	 * @param state - Only return bills from a given state (e.g. 'nc')
	 * @param search_window By default all bills are searched, 
	 *     but if a time window is desired the 
	 *     following options can be passed to search_window:
	 * 		  all - Default, include all sessions.
	 * 	      term - Only bills from sessions within the current term.
	 *        session - Only bills from the current session.
	 *        session:2009 - Only bills from the session named 2009.
	 *        term:2009-2011 - Only bills from the sessions in the 2009-2011 session.
	 * @param subject - Only bills categorized by Open States as belonging to this subject.
	 * 
	 * See the above action_dates, created_at, and updated_at documentation for the meaning of these dates.
	 * 
	 * The API will not return exceedingly large responses, so it may be 
	 * necessary to use page and per_page to control the number of results returned:
	 * @param page - Page of results, each of size per_page (defaults to 1)
	 * @param per_page - Number of results per page, is unlimited unless page is set, in which case it defaults to 50.
	 * 
	 * @return {@link Bills} 
	 */
	public Bills searchByWindowSubject(
		String state, 
		String search_window,  
		String subject, 
		String page, 
		String per_page
	) throws OpenStatesException {
		OpenStates.suspendCache();
		Bills bills = api.query( 
			new MethodMap("bills"), 
			new ArgMap(
				"state", state, 
				"search_window", search_window, 
				"subject", subject, 
				"page", page, 
				"per_page", per_page
			), 
		Bills.class);
		return bills;
	}

	/**
	 * Bill Search
	 * This method returns just a subset of the bill fields by default.
	 * NOTE: *** This method never uses the cache. Use with caution. ***
	 * 
	 * Filter Parameters
	 * 
	 * The following parameters filter the returned set of bills, at least one must be provided.
	 * Use null for any parameter that is not used.
	 * 
	 * @param state - Only return bills from a given state (e.g. 'nc')
	 * @param q Only return bills matching the provided full text query.
	 * @param search_window By default all bills are searched, 
	 *     but if a time window is desired the 
	 *     following options can be passed to search_window:
	 * 		  all - Default, include all sessions.
	 * 	      term - Only bills from sessions within the current term.
	 *        session - Only bills from the current session.
	 *        session:2009 - Only bills from the session named 2009.
	 *        term:2009-2011 - Only bills from the sessions in the 2009-2011 session.
	 * 
	 * The API will not return exceedingly large responses, so it may be 
	 * necessary to use page and per_page to control the number of results returned:
	 * @param page - Page of results, each of size per_page (defaults to 1)
	 * @param per_page - Number of results per page, is unlimited unless page is set, in which case it defaults to 50.
	 * 
	 * @return {@link Bills} 
	 */
	public Bills searchByQueryWindow(
		String state, 
		String q, 
		String search_window,  
		String page, 
		String per_page
	) throws OpenStatesException {
		OpenStates.suspendCache();
		Bills bills = api.query( 
			new MethodMap("bills"), 
			new ArgMap(
				"state", state, 
				"q", q, 
				"search_window", search_window, 
				"page", page, 
				"per_page", per_page
			), 
		Bills.class);
		return bills;
	}

	/**
	 * Bill Search
	 * This method returns just a subset of the bill fields by default.
	 * NOTE: *** This method never uses the cache. Use with caution. ***
	 * 
	 * Filter Parameters
	 * 
	 * The following parameters filter the returned set of bills, at least one must be provided.
	 * Use null for any parameter that is not used.
	 * 
	 * @param state - Only return bills from a given state (e.g. 'nc')
	 * @param chamber - Only return bills matching the provided chamber ('upper' or 'lower')
	 * @param bill_id - Only return bills with a given bill_id.
	 * @param bill_id__in - Accepts a pipe (|) delimited list of bill ids.
	 * @param q Only return bills matching the provided full text query.
	 * @param search_window By default all bills are searched, 
	 *     but if a time window is desired the 
	 *     following options can be passed to search_window:
	 * 		  all - Default, include all sessions.
	 * 	      term - Only bills from sessions within the current term.
	 *        session - Only bills from the current session.
	 *        session:2009 - Only bills from the session named 2009.
	 *        term:2009-2011 - Only bills from the sessions in the 2009-2011 session.
	 * @param updated_since - Only bills updated since a provided date (provided in YYYY-MM-DD format)
	 * @param sponsor_id - Only bills sponsored by a given legislator id (e.g. 'ILL000555')
	 * @param subject - Only bills categorized by Open States as belonging to this subject.
	 * @param type - Only bills of a given type (e.g. 'bill', 'resolution', etc.)
	 * 
	 * Additional Parameters
	 * @param sort - Sort-order of results, defaults to 'last', options are:
	 *        'first', 'last', 'signed', 'passed_lower', 'passed_upper', 'updated_at', 'created_at'.
	 * 
	 * See the above action_dates, created_at, and updated_at documentation for the meaning of these dates.
	 * 
	 * The API will not return exceedingly large responses, so it may be 
	 * necessary to use page and per_page to control the number of results returned:
	 * @param page - Page of results, each of size per_page (defaults to 1)
	 * @param per_page - Number of results per page, is unlimited unless page is set, in which case it defaults to 50.
	 * 
	 * @return {@link Bills} 
	 */
	public Bills search(
		String state, 
		String chamber, 
		String bill_id, 
		String bill_id__in,
		String q, 
		String search_window,  
		String updated_since, 
		String sponsor_id, 
		String subject, 
		String type, 
		String sort, 
		String page, 
		String per_page
	) throws OpenStatesException {
		OpenStates.suspendCache();
		Bills bills = api.query( 
			new MethodMap("bills"), 
			new ArgMap(
				"state", state, 
				"chamber", chamber, 
				"bill_id", bill_id, 
				"bill_id__in", bill_id__in, 
				"q", q, 
				"search_window", search_window, 
				"updated_since", updated_since, 
				"sponsor_id", sponsor_id, 
				"subject", subject, 
				"type", type, 
				"sort", sort, 
				"page", page, 
				"per_page", per_page
			), 
		Bills.class);
		return bills;
	}

	/**
	 * Bill Detail
	 * This method returns the full detail object for a bill.
	 * 
	 * @param state
	 * @param session
	 * @param number
	 * @return {@link Bill}
	 */
	public Bill detail( String state, String session, String number) throws OpenStatesException {
		return api.query( new MethodMap("bills", state, session, number), null, Bill.class);
	}

	/**
	 * Bill Detail
	 * This method returns the full detail object for a bill.
	 * 
	 * @param bill_id
	 * @return {@link Bill}
	 */
	public Bill detailById( String bill_id) throws OpenStatesException {
		return api.query( new MethodMap("bills", bill_id), null, Bill.class);
	}

}
