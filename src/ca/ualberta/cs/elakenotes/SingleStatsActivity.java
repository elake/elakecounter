package ca.ualberta.cs.elakenotes;

import java.lang.reflect.Type;
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

public class SingleStatsActivity extends Activity {
	// Shows the statistics for a single counter
	
	private CounterModel activeCounter;
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
		String count = i.getStringExtra("count"); // Get the counter from the previous activity
		Gson gson = new Gson();
		Type counter = new TypeToken<CounterModel>(){}.getType();
		activeCounter = gson.fromJson(count, counter);
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
		// Country Item Selected Listener
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
				stats.clear(); // Clear and add instead of replacing so that the adapter doesn't get confused and cry
				stats.addAll(activeCounter.getPrintableAggregation(spinnerValues.get(item).intValue()));
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
		stats = activeCounter.getPrintableAggregation(0);
		statadapter = new ArrayAdapter<String>(this,
				R.layout.list_item, stats);
		statlist.setAdapter(statadapter);
		statadapter.notifyDataSetChanged();
	}

}