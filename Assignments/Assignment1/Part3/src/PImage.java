import java.util.ArrayList;
import java.util.List;

public class PImage {
	private int[][] data;
	private String lable;
	private List<Integer> featureValue;

	public PImage(int row, int col,String lable) {
		featureValue  = new ArrayList<Integer>();
		setData(new int[row][col]);
		setLable(lable);
	}

	public int[][] getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
	}

	public void setValue(int row, int col, int value) {
		this.data[row][col] = value;
	}

	public String toString() {
		String s=this.lable+"\n";
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				s+=data[i][j];
			}
			s=s+"\n";
		}
		return s;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public void setFeatureValue(List<Integer> featureValue) {
		for(int i = 0; i<featureValue.size(); i++) {
		 this.featureValue.add(featureValue.get(i));
		}
		
	}
	
	public List<Integer> getFeatureValue() {
		return this.featureValue;
		
	}
}
