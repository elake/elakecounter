package ca.ualberta.cs.elakenotes;
import java.util.Date;
import java.util.ArrayList;

public class CountListModel {
	/**
	 * CountLisModel is basically a wrapper for an arraylist of dates,
	 * but it was created in case I decided to expand the data contained
	 * by a single count.
	 */
	private ArrayList<Date> countList;

	public ArrayList<Date> getCountList() {
		return countList;
	}

	public void setCountList(ArrayList<Date> countList) {
		this.countList = countList;
	}
	
	public void addCount() {
		countList.add(new Date());
	}
	
	public CountListModel() {
		super();
		countList = new ArrayList<Date>();
	}
	
	public int getLength() {
		return countList.size();
	}
}
