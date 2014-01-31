package ca.ualberta.cs.elakenotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class DateAggregationModel {
	/**
	 * DateAggregationModel produces an object that can return a printable list of the
	 * aggregated statistics of a list of dates, as well as a hashmap of said statistics.
	 */
	
	private Map<String, Integer> aggregatedDate;
	private ArrayList<String> printableResult;
	
	private String DateCategorizer (Date date, int depth) {
		// Private function for determining how dates should be categorized.
		// Intentionally creates strings whose lexicographical order matches the
		// chronological order of the dates used to generate them.
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String[] values = new String[4]; // Filled with information about the date
		String[] titles = new String[] {"month", "week", "day", "hour"}; // For producing the string
		values[0] = Integer.toString(cal.get(Calendar.MONTH));
		values[1] = Integer.toString(cal.get(Calendar.WEEK_OF_MONTH));
		values[2] = Integer.toString(cal.get(Calendar.DAY_OF_WEEK));
		values[3] = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
		String dc = "Year "; // Year is always going to be used
		dc = dc + Integer.toString(cal.get(Calendar.YEAR));
		for (int i = 0; i < depth; i++) { // Create string
			dc = dc + ", ";
			dc = dc + titles[i] + " " + values[i];
		}
		dc = dc + ": "; // Every string should end with a colon
		return dc;
	}
	
	public ArrayList<String> getPrintableResult() {
		return printableResult;
	}

	public void setPrintableResult(ArrayList<String> printableResult) {
		this.printableResult = printableResult;
	}

	private void GenerateAggregation (ArrayList<Date> dates, int depth) {
		// GenerateAggregation creates the map of the aggregated statistics,
		// and uses that map to create the printable result (a list of strings).
		
		for (Date date: dates) {
			String category = DateCategorizer(date, depth);
			if (aggregatedDate.containsKey(category)) { // If the map key exists, increment the count
				int newcount = (Integer) aggregatedDate.get(category);
				newcount = newcount + 1;
				aggregatedDate.put(category, newcount);
			}
			else { // Otherwise it is the first, add i with a count of 1
				aggregatedDate.put(category, 1);
			}
		}
		for (Entry<String, Integer> entry : aggregatedDate.entrySet()) { // The printable result is just concatenating the entry values
			printableResult.add((entry.getKey() + entry.getValue().toString()));
		}
		Collections.sort(printableResult); // Because the lexicographical order is the chronological order, this works.
		
	}
	
	public DateAggregationModel (ArrayList<Date> dates, int depth) {
		super();
		printableResult = new ArrayList<String>();
		aggregatedDate = new HashMap<String, Integer>();
		GenerateAggregation(dates, depth);
	}
	
}

