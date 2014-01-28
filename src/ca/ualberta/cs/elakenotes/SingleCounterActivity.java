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


	private TextView countText;
	private TextView counterTitle;
	private CounterModel activeCounter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter);
		Intent i = getIntent();
		String count = i.getStringExtra("count");
		Gson gson = new Gson();
		Type counter = new TypeToken<CounterModel>(){}.getType();
		activeCounter = gson.fromJson(count, counter);
		countText = (TextView) findViewById(R.id.CurCount);
		counterTitle = (TextView) findViewById(R.id.CounterTitle);
		Button incButton = (Button) findViewById(R.id.increment);
		Button deleteButton = (Button) findViewById(R.id.delete);
		Button resetButton = (Button) findViewById(R.id.reset);
		Button renameButton = (Button) findViewById(R.id.rename);
		
		resetButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				setResult(RESULT_OK);
				activeCounter.resetCount();
				countText.setText(Integer.toString(activeCounter.getLength()));
				saveInFile(activeCounter, activeCounter.getFilename());
			}
		});
		
		incButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				setResult(RESULT_OK);
				activeCounter.addCount();
				countText.setText(Integer.toString(activeCounter.getLength()));
				saveInFile(activeCounter, activeCounter.getFilename());
			}
		});
		
		deleteButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				File file = new File(getFilesDir(), activeCounter.getFilename());
				file.delete();
				finish();
			}
		});
		
		renameButton.setOnClickListener(new View.OnClickListener() {
			
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
				  activeCounter.setName(value);
				  saveInFile(activeCounter, activeCounter.getFilename());
				  counterTitle.setText(activeCounter.getName());
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