package ca.ualberta.cs.elakenotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class DateAggregationModel {

	private Map<String, Integer> aggregatedDate;
	private ArrayList<String> printableResult;
	
	private String DateCategorizer (Date date, int depth) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String[] values = new String[4];
		String[] titles = new String[] {"month", "week", "day", "hour"};
		values[0] = Integer.toString(cal.get(Calendar.MONTH));
		values[1] = Integer.toString(cal.get(Calendar.WEEK_OF_MONTH));
		values[2] = Integer.toString(cal.get(Calendar.DAY_OF_WEEK));
		values[3] = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
		String dc = "Year ";
		dc = dc + Integer.toString(cal.get(Calendar.YEAR));
		for (int i = 0; i < depth; i++) {
			dc = dc + ", ";
			dc = dc + titles[i] + " " + values[i];
		}
		dc = dc + ": ";
		return dc;
	}
	
	public ArrayList<String> getPrintableResult() {
		return printableResult;
	}

	public void setPrintableResult(ArrayList<String> printableResult) {
		this.printableResult = printableResult;
	}

	private void GenerateAggregation (ArrayList<Date> dates, int depth) {
		for (Date date: dates) {
			String category = DateCategorizer(date, depth).toString();
			if (aggregatedDate.containsKey(category)) {
				System.out.println("yo");
				int newcount = (Integer) aggregatedDate.get(category);
				newcount = newcount + 1;
				aggregatedDate.remove(category);
				aggregatedDate.put(category, newcount);
			}
			else {
				aggregatedDate.put(category, 1);
			}
		}
		for (Entry<String, Integer> entry : aggregatedDate.entrySet()) {
			printableResult.add((entry.getKey() + entry.getValue().toString()));
		}
		Collections.sort(printableResult);
		
	}
	
	public DateAggregationModel (ArrayList<Date> dates, int depth) {
		super();
		printableResult = new ArrayList<String>();
		aggregatedDate = new HashMap<String, Integer>();
		GenerateAggregation(dates, depth);
	}
	
}

