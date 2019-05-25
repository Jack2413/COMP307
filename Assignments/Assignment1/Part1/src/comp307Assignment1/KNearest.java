package comp307Assignment1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNearest {
	private ArrayList<Iris>  iristrain= new ArrayList<Iris>();
	private ArrayList<Iris> irisTest= new ArrayList<Iris>();
	private List<Double> ListR =new ArrayList<Double>();
	//private ArrayList<Iris> virginica= new ArrayList<Iris>();;
	
	//List<Double> setosaR;
	//List<Double> versicolorR;
	//List<Double> virginicaR;
	
	private String txt="";
	


	public KNearest(ArrayList<Iris> iristrain, ArrayList<Iris> irisTest) {
		
		this.setIristrain(iristrain);
		this.setIrisTest(irisTest);
		this.ListR=range(iristrain);
		//train( iristrain);
		
		for (int k=1; k<10; k++) {
			test(irisTest,iristrain,ListR,k);
			totext(txt,k);
		}
		
	}

	private void totext(String txt2, int k) {
		//System.out.print(txt);
		try {  
			   Path currentRelativePath = Paths.get("");
			   String path = currentRelativePath.toAbsolutePath().toString()+"/txt"+k;
			   File file = new File(path);
			   BufferedWriter ow = new BufferedWriter(new FileWriter(file));
			   ow.write(txt2);
			   ow.close();
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		

		
	}

	//private void train(ArrayList<Iris> iristrain2) {
		// TODO Auto-generated method stub
		/*
		for (int i=0;i<iristrain2.size();i++) {
			switch (iristrain2.get(i).getType()) {
			
			case "Iris-setosa": setosa.add(iristrain2.get(i)); break;
			case "Iris-versicolor": versicolor.add(iristrain2.get(i)); break;
			case "Iris-virginica": virginica.add(iristrain2.get(i)); break;
				
			}
			*/
			
			//setosaR=range(setosa);
			//versicolorR=range(versicolor);
			//virginicaR=range(virginica);
			
		//}

		
	
	
	public void test(ArrayList<Iris> irisTest,ArrayList<Iris> iristrain, List<Double> listR, int k2) {
		double count =0;
		StringBuffer sBuffer = new StringBuffer();
		for (int i=0; i<irisTest.size();i++) {
			Iris a = irisTest.get(i);
			Map<Double,String>Dlist = new HashMap<Double, String>();
			List <Double> Nearest = new ArrayList<Double>();
			for (int j=0; j<iristrain.size(); j++) {

				Iris b = iristrain.get(j);
				//List<Double> Rlist = R(b);
				//List<Double> Rlist
				double d = 0;
				for (int k=0; k<4;k++) {
					if(j==0) sBuffer.append(a.getData().get(k)+" ");
					d += (Math.pow(a.getData().get(k)-b.getData().get(k),2))/Math.pow(listR.get(k),2);
				}
				double dis = Math.pow(d, 0.5);
				
				Dlist.put(dis, b.getType());
				Nearest.add(dis);
			}
			String type = decision(NearestNeighboursort(Nearest,k2),Dlist);
			sBuffer.append(a.getType());
			sBuffer.append("  result: "+type);
			if(type.equals(a.getType())) {
				count+=1;
				sBuffer.append("   O\n");
			}else {
				sBuffer.append("   X\n");
			}
		}
		double rate = count/irisTest.size()*100;
		sBuffer.append("the correct rata: " + count + "/" + irisTest.size() +" = "+rate+"%\n");
		txt=sBuffer.toString();
		//System.out.print(sBuffer);
		System.out.print("the correct rata: "+ rate+"%\n");
	}


	public String decision(List<Double> Neighboursorts, Map<Double, String> dlist) {
		
		int a = 0;
		int b = 0;
		int c = 0;

		for (int i = 0; i<Neighboursorts.size(); i++) {
			switch (dlist.get(Neighboursorts.get(i))) {

			case "Iris-setosa": a+=1; break;
			case "Iris-versicolor": b+=1; break;
			case "Iris-virginica": c+=1; break;

			}
		}
		//System.out.print("\na: "+a +"b: "+b+"c "+c);
		
		if(a>b&&a>c) {
			return "Iris-setosa";
		}else if (b>c&&b>a){
			return "Iris-versicolor";
		}else if (c>a&&c>b) {
			return "Iris-virginica";
		}
		return dlist.get(Neighboursorts.get(0)); //return the closest neighbour 
	}

	public List<Double> NearestNeighboursort(List<Double> nearest, int k) {
		
		nearest.sort(null);
		List <Double> Nearest2 = new ArrayList<Double>();
		for(int i = 0; i<k; i++) {
			Nearest2.add(nearest.get(i));
		}
		
		return Nearest2;
	}

/*
	private List<Double> R(Iris b) {
		switch (b.getType()) {
		
		case "Iris-setosa": return setosaR;
		case "Iris-versicolor": return versicolorR;
		case "Iris-virginica": return virginicaR;
		default: return null; 
			
		}
		
	}
	*/

	public List<Double> range(ArrayList<Iris> irisList) {
		List<Double> Rlist = new ArrayList<Double>(); 
		for (int j=0; j<4; j++) {
			double min = Double.MAX_VALUE;
			double max = 0;
			for (int i=0; i<irisList.size();i++) {
				double x = irisList.get(i).getData().get(j);
				
				if(x<min) min=x;
				if(x>max) max=x;
				//System.out.print("X: "+x +"min: "+min +"max: "+max+"\n");
			}
			Rlist.add(round(max-min,1));
			
		}
		return Rlist;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public ArrayList<Iris> getIristrain() {
		return iristrain;
	}

	public void setIristrain(ArrayList<Iris> iristrain) {
		this.iristrain = iristrain;
	}

	public ArrayList<Iris> getIrisTest() {
		return irisTest;
	}

	public void setIrisTest(ArrayList<Iris> irisTest) {
		this.irisTest = irisTest;
	}


}
