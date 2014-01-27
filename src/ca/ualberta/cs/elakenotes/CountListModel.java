package ca.ualberta.cs.elakenotes;
import java.util.Date;
import java.util.ArrayList;

public class CountListModel {
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
