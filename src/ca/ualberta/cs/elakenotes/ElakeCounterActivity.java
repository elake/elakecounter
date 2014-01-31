package ca.ualberta.cs.elakenotes;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.lang.reflect.Type;

import ca.ualberta.cs.elakecounter.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ElakeCounterActivity extends Activity {
	/**
	 * ElakeCounterActivity is the main activity. It displays a listview of the
	 * counters, as well as an interface for creating new counters. Counters
	 * are in descending order of count totals.
	 */

	private EditText bodyText;
	private ListView counterList;
	private ArrayAdapter<CounterModel> adapter;
	private ArrayList<CounterModel> allCounters;
	private File dir;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dir = new File(getFilesDir().toString()); // Get the working directory
		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.createCounter);
		Button clearButton = (Button) findViewById(R.id.clear);
		Button statsButton = (Button) findViewById(R.id.viewstats);
		counterList = (ListView) findViewById(R.id.currentCounterList);
		
		statsButton.setOnClickListener(new View.OnClickListener() {
			// View the stats for all of the counters combined

			public void onClick(View v) {
				setResult(RESULT_OK); 
				ArrayList<Date> allDates = new ArrayList<Date>();
				for (CounterModel element : allCounters) { // Create a list of all of the dates from every counter
					allDates.addAll(element.getCountList().getCountList());
				}
				Intent i = new Intent(getBaseContext(), AllStatsActivity.class);
				Gson gson = new Gson();
				String json = gson.toJson(allDates); // Send this list of dates to the AllStatsActivity
				i.putExtra("alldates", json);
				startActivity(i); // Open the AllStatsActivity
			}
		});
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			// Create a new counter with the user-provided name
			
			public void onClick(View v) {
				setResult(RESULT_OK);
				String name = bodyText.getText().toString();
				CounterModel newCounter = new CounterModel(name);
				allCounters.add(newCounter);
				saveInFile(newCounter , newCounter.getFilename()); // Every counter gets a new file
				adapter.notifyDataSetChanged();
				bodyText.setText(null);
			}
		});
		clearButton.setOnClickListener(new View.OnClickListener() {
			// Clear the text inputted by the user
			
			public void onClick(View v) {
				bodyText.setText(null);	
			}
		});
		
		counterList.setOnItemClickListener(new OnItemClickListener() {
			// The user has chosen a counter, go to the activity for that specific counter
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Intent i = new Intent(getBaseContext(), SingleCounterActivity.class);
				CounterModel count = allCounters.get(position); // Get the counter that the user chose
				Gson gson = new Gson();
				String json = gson.toJson(count);
				i.putExtra("count", json);
				startActivity(i);
			}
			
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		allCounters = new ArrayList<CounterModel>();
			for (File element : dir.listFiles()){ // Load all counter files into allCounters
				allCounters.add(loadFromFile(element.getName()));
			}
		Collections.sort(allCounters); // Sort counters by current count
		adapter = new ArrayAdapter<CounterModel>(this,
				R.layout.list_item, allCounters);
		counterList.setAdapter(adapter);
	}

	private CounterModel loadFromFile(String FILENAME) {
		// Procedure for loading Json files navigated by Victoria Bobey for LonelyTwitter lab
		
		try {
			String tweets;
			FileInputStream fis = openFileInput(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tweets = (String) ois.readObject();
			ois.close();
			fis.close();
			Gson gson = new Gson();
			Type tweetList = new TypeToken<CounterModel>(){}.getType();
			CounterModel json = gson.fromJson(tweets, tweetList);
			return json;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Should never hit this line
		return new CounterModel((String) "This counter shouldn't exist");
	}
	
	private void saveInFile(CounterModel counter, String FILENAME) {
		// Procedure for saving Json files navigated by Victoria Bobey for LonelyTwitter lab
		
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			Gson gson = new Gson();
			String json = gson.toJson(counter);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(json);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}