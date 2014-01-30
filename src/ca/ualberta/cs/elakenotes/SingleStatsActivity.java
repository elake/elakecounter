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
		String count = i.getStringExtra("count");
		Gson gson = new Gson();
		Type counter = new TypeToken<CounterModel>(){}.getType();
		activeCounter = gson.fromJson(count, counter);
		spinner = (Spinner) findViewById(R.id.singlespinner);
		statlist = (ListView) findViewById(R.id.singlestatlist);
		String[] spinnerOptions = new String[] {"Year", "Month", "Week", "Day", "Hour"};
		spinnerValues = new HashMap<String, Integer>();
		for (int j = 0; j < 5; j++) { //should probably address hardcoded 5 later
			spinnerValues.put(spinnerOptions[j], j);
		}
		adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, spinnerOptions);
		adapter.setDropDownViewResource(R.layout.list_item);
		spinner.setAdapter(adapter);
		// Country Item Selected Listener
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
 
            }

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				setResult(RESULT_OK);
				item = adapter.getItem(arg2).toString();
				stats = activeCounter.getPrintableAggregation(spinnerValues.get(item).intValue());
				stats = new ArrayList<String>();
				stats.add("Hey");
				System.out.println(spinnerValues.get(item));
				statlist.setAdapter(statadapter);
				statadapter.notifyDataSetChanged();
			}
        });

		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		stats = activeCounter.getPrintableAggregation(3);
		statadapter = new ArrayAdapter<String>(this,
				R.layout.list_item, stats);
		statlist.setAdapter(statadapter);
		statadapter.notifyDataSetChanged();
	}

}