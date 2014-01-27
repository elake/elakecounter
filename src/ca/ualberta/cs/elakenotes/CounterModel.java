package ca.ualberta.cs.elakenotes;
import java.util.Random;

public class CounterModel {
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

}
