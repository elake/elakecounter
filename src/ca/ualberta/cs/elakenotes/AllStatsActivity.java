package ca.ualberta.cs.elakenotes;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.ualberta.cs.elakecounter.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class AllStatsActivity extends Activity {
	/**
	 * AllStatsActivity displays the stats for all of the users counters,
	 * allowing you to aggregate by Year, month, week, day, and hour.
	 */
	
	private ArrayList<Date> dateList;
	private ArrayAdapter<String> adapter;
	private Map<String, Integer> spinnerValues;
	private String item;
	private ArrayList<String> stats;
	private ArrayAdapter<String> statadapter;
	private ListView statlist;
	private Spinner spinner;
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singlestats);
		Intent i = getIntent();
		String count = i.getStringExtra("alldates"); // Get the dates from the main activity's intent
		Gson gson = new Gson();
		Type dateType = new TypeToken<ArrayList<Date>>(){}.getType();
		dateList = gson.fromJson(count, dateType);
		spinner = (Spinner) findViewById(R.id.singlespinner); // Create a spinner for choosing type of aggregation
		statlist = (ListView) findViewById(R.id.singlestatlist);
		String[] spinnerOptions = new String[] {"Year", "Month", "Week", "Day", "Hour"}; // Options for aggregating
		spinnerValues = new HashMap<String, Integer>();
		for (int j = 0; j < 5; j++) { // Create a map for converting spinner choices to depth for DateAggregationModel
			spinnerValues.put(spinnerOptions[j], j);
		}
		adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, spinnerOptions);
		adapter.setDropDownViewResource(R.layout.list_item);
		spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        	// User has selected an aggregation type
        	
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
 
            }

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// Generate the aggregated dates and update the listview
				
				setResult(RESULT_OK);
				item = adapter.getItem(arg2).toString();
				DateAggregationModel newDates = new DateAggregationModel(dateList, spinnerValues.get(item).intValue());
				stats.clear(); // Clear and add instead of replacing so that the adapter doesn't get confused and cry
				stats.addAll(newDates.getPrintableResult());
				statadapter.notifyDataSetChanged();
			}
        });

		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// Start with displaying years to avoid a null reference exception that I can't seem to shake
		// Note that when resuming years will display regardless of choice. Choose again to refresh view.
		stats = new DateAggregationModel(dateList, 0).getPrintableResult();
		statadapter = new ArrayAdapter<String>(this,
				R.layout.list_item, stats);
		statlist.setAdapter(statadapter);
		statadapter.notifyDataSetChanged();
	}

}