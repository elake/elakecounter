package ca.ualberta.cs.elakenotes;
import java.util.Random;

public class CounterModel implements Comparable<CounterModel> {
	private String name;
	private CountListModel countList;
	private String filename;

	public CounterModel(String name) {
		super();
		this.name = name;
		countList = new CountListModel();
		filename = Integer.toString(new Random().nextInt((int) 400000000)); // Functions as a UUID for filenames
		
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
		return name + ": " + Integer.toString(countList.getLength());
	}
	
	public void addCount() {
		countList.addCount();
	}
	
	public void resetCount() {
		this.countList = new CountListModel();
	}

	@Override
	public int compareTo(CounterModel arg0) throws ClassCastException {
		if (!(arg0 instanceof CounterModel))
		      throw new ClassCastException("A CounterModel object expected.");
		return Integer.valueOf(arg0.getLength()).compareTo(this.getLength());
	}

}
