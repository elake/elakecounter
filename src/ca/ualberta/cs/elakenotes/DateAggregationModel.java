package ca.ualberta.cs.elakenotes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;


public class DateAggregationModel {

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
		dc = dc + ":";
		return "dc";
	}
	
	
	}
	
	
	
	
	
	
	
	
	
}
