package comp307Assignment1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class testsTest {

	@Test
	public void test() { //test range
		ArrayList<Iris> irisList = new ArrayList<Iris>();
		irisList.add(new Iris(5.1, 3.5,  1.4,  0.2, "Iris-setosa"));
		irisList.add(new Iris(4.9, 3.0,  1.4,  0.2, "Iris-setosa"));
		irisList.add(new Iris(4.7, 3.2,  1.3,  0.2, "Iris-setosa"));
		irisList.add(new Iris(4.6, 3.1,  1.5,  0.2, "Iris-setosa"));
		irisList.add(new Iris(5.0, 3.6,  1.4,  0.2, "Iris-setosa"));
		
		
		KNearest k =new KNearest(irisList,irisList);
		List<Double> L = k.range(irisList);
		assertEquals(L.size(),4 );
		//System.out.print(k.range(irisList));
		assert(L.get(0)==0.5);
		assert(L.get(1)==0.6);
		assert(L.get(2)==0.2);
		assert(L.get(3)==0);
		
		//fail("Not yet implemented");
	}
	
	@Test
	public void test2() { //test NearestNeighboursort
		ArrayList<Iris> irisList = new ArrayList<Iris>();
		irisList.add(new Iris(5.1, 3.5,  1.4,  0.2, "Iris-setosa"));
		List<Double> nearest = new ArrayList<Double>();
		nearest.add(0.2);
		nearest.add(0.1);
		nearest.add(0.3);
		nearest.add(0.5);
		nearest.add(0.4);
		nearest.add(0.7);
		nearest.add(0.9);
		nearest.add(0.6);
		nearest.add(0.8);
	    int k = 5;
		KNearest kN =new KNearest(irisList,irisList);
		List<Double> List =kN.NearestNeighboursort(nearest, k);
		//System.out.print(List);
		assert(List.get(0)==0.1);
		assert(List.get(1)==0.2);
		assert(List.get(2)==0.3);
		assert(List.get(3)==0.4);
		assert(List.get(4)==0.5);
		
	}
	
	@Test
	public void test3() {
		ArrayList<Iris> irisList = new ArrayList<Iris>();
		irisList.add(new Iris(5.1, 3.5,  1.4,  0.2, "Iris-setosa"));
		
		List<Double> Neighboursorts = new ArrayList<Double>();
		Map<Double, String> dlistMap = new HashMap<Double, String>();
		
		
	    Neighboursorts.add(0.1);
	    Neighboursorts.add(0.2);
	    Neighboursorts.add(0.3);
	    Neighboursorts.add(0.4);
	    Neighboursorts.add(0.5);
	    Neighboursorts.add(0.6);
		
		dlistMap.put(0.1,"Iris-setosa");
		dlistMap.put(0.2,"Iris-versicolor");
		dlistMap.put(0.3,"Iris-virginica");
		dlistMap.put(0.4,"Iris-setosa");
		dlistMap.put(0.5,"Iris-versicolor");
		dlistMap.put(0.6,"Iris-setosa");
		
		//System.out.print(dlistMap+"\n");
		//System.out.print(Neighboursorts);
		
		KNearest kN =new KNearest(irisList,irisList);
		assertEquals("Iris-setosa",kN.decision(Neighboursorts, dlistMap));
	
	}
	
	@Test
	public void test4() {
		ArrayList<Iris> irisList = new ArrayList<Iris>();
		irisList.add(new Iris(5.1, 3.5,  1.4,  0.2, "Iris-setosa"));
		irisList.add(new Iris(4.9, 3.0,  1.4,  0.2, "Iris-setosa"));
		irisList.add(new Iris(4.7, 3.2,  1.3,  0.2, "Iris-setosa"));
		irisList.add(new Iris(4.6, 3.1,  1.5,  0.2, "Iris-setosa"));
		irisList.add(new Iris(5.0, 3.6,  1.4,  0.2, "Iris-setosa"));
		
		
		KNearest k =new KNearest(irisList,irisList);
		k.test(k.getIrisTest(),k.getIristrain(),k.range(irisList),1);
	}
	
	

}
