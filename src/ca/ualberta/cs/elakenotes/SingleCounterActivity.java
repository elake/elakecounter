package ca.ualberta.cs.elakenotes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;

import ca.ualberta.cs.elakecounter.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SingleCounterActivity extends Activity {
	/**
	 * SingleCounterActivity allows the user to control the counter they selected in
	 * ElakeCounterActivity. It allows for incrementing, individual stats, renaming,
	 * zeroing, and deleting a counter.
	 */

	private TextView countText;
	private TextView counterTitle;
	private CounterModel activeCounter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Similar creation to other activities
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter);
		Intent i = getIntent();
		String count = i.getStringExtra("count"); // Get the counter we're going to use
		Gson gson = new Gson();
		Type counter = new TypeToken<CounterModel>(){}.getType();
		activeCounter = gson.fromJson(count, counter);
		countText = (TextView) findViewById(R.id.CurCount);
		counterTitle = (TextView) findViewById(R.id.CounterTitle);
		Button incButton = (Button) findViewById(R.id.increment);
		Button deleteButton = (Button) findViewById(R.id.delete);
		Button resetButton = (Button) findViewById(R.id.reset);
		Button renameButton = (Button) findViewById(R.id.rename);
		Button statsButton = (Button) findViewById(R.id.singlestats);
		
		statsButton.setOnClickListener(new View.OnClickListener() {
			// View the stats for this particular counter
			
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), SingleStatsActivity.class);
				Gson gson = new Gson();
				String json = gson.toJson(activeCounter); // Send the counter to SingleStatsActivity
				i.putExtra("count", json);
				startActivity(i); // Start SingleStatsActivity
			}
		});

		resetButton.setOnClickListener(new View.OnClickListener() {
			// Zero the counter
			
			public void onClick(View v) {
				setResult(RESULT_OK);
				activeCounter.resetCount(); // Zeroing handled by counter
				countText.setText(Integer.toString(activeCounter.getLength())); // Reset the shown number
				saveInFile(activeCounter, activeCounter.getFilename()); // Commit immediately
			}
		});
		
		incButton.setOnClickListener(new View.OnClickListener() {
			// Increment the counter
			
			public void onClick(View v) {
				setResult(RESULT_OK);
				activeCounter.addCount(); // Adding handled by counter
				countText.setText(Integer.toString(activeCounter.getLength())); // Update the shown number
				saveInFile(activeCounter, activeCounter.getFilename()); // Commit immediately
			}
		});
		
		deleteButton.setOnClickListener(new View.OnClickListener() {
			// Delete the counter forever
			
			public void onClick(View v) {
				File file = new File(getFilesDir(), activeCounter.getFilename()); // Select the current counter's file
				file.delete(); // Delete it
				finish(); // Go back
			}
		});
		
		renameButton.setOnClickListener(new View.OnClickListener() {
			// Rename the current counter
			
			public void onClick(View v) {
				
				// http://www.androidsnippets.com/prompt-user-input-with-an-alertdialog
				AlertDialog.Builder alert = new AlertDialog.Builder(SingleCounterActivity.this);
				alert.setTitle("Rename Counter");
				alert.setMessage("Enter new counter name:");

				// Set an EditText view to get user input 
				final EditText input = new EditText(SingleCounterActivity.this);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = input.getText().toString();
				  activeCounter.setName(value); // Name setting handled by counter
				  saveInFile(activeCounter, activeCounter.getFilename()); // Commit immediately
				  counterTitle.setText(activeCounter.getName()); // Show the new name in the title
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		countText.setText(Integer.toString(activeCounter.getLength()));
		counterTitle.setText(activeCounter.getName());
	}

	
	private void saveInFile(CounterModel counter, String FILENAME) {
		// See credit for saveInFile to Victoria Bobey in ElakeCounterActivity
		
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