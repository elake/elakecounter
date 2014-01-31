package ca.ualberta.cs.elakenotes;
import java.util.ArrayList;
import java.util.Random;

public class CounterModel implements Comparable<CounterModel> {
	/**
	 * CountModel represents a single counter. It has a CountListModel and is
	 * dependent on DateAggregationModel to produce its statistics.
	 */
	
	private String name;
	private CountListModel countList;
	private String filename;

	public CounterModel(String name) {
		// Construct a new counter with the given name, filename will be generated not specified
		
		super();
		this.name = name;
		countList = new CountListModel();
		filename = Integer.toString(new Random().nextInt((int) 400000000)); // Functions as a UUID for filenames
		
	}
	
	public ArrayList<String> getPrintableAggregation (int depth) {
		// Produces a printable aggregation based on the given depth, dependent on DateAggregationModel
		
		DateAggregationModel da = new DateAggregationModel(this.getCountList().getCountList(), depth);
		return da.getPrintableResult();
		
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public CountListModel getCountList() {
		return countList;
	}
	
	public int getLength(){
		return countList.getLength();
	}

	public void setCountList(CountListModel countList) {
		this.countList = countList;
	}

	@Override
	public String toString() {
		// Prints the name and the number of counts
		
		return name + ": " + Integer.toString(countList.getLength());
	}
	
	public void addCount() {
		// Implemented via countList
		
		countList.addCount();
	}
	
	public void resetCount() {
		// A new CountListModel will have a length of zero
		
		this.countList = new CountListModel();
	}

	@Override
	public int compareTo(CounterModel arg0) throws ClassCastException {
		// Compare by the number of counts a counter has
		
		if (!(arg0 instanceof CounterModel))
		      throw new ClassCastException("A CounterModel object expected.");
		return Integer.valueOf(arg0.getLength()).compareTo(this.getLength());
	}

}
