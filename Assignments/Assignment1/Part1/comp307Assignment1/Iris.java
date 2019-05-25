package comp307Assignment1;
import java.util.ArrayList;

public class Iris {

	private String type;
	private ArrayList<Double> data= new ArrayList<Double>();

	public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String type) {
	
		this.setType(type);
		data.add(sepalLength);
		data.add(sepalWidth);
		data.add(petalLength);
		data.add(petalWidth);
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public ArrayList<Double> getData(){
		return this.data;
	}

}
