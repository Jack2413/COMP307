import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class NodeTest {

	@Test
	public void test() {
		
		Node root = new Node("", null, null);
		Set<Training> trueInstance = new HashSet<Training>();
		Set<Training> falseInstance = new HashSet<Training>();
		
		List<Boolean> flags = new ArrayList<Boolean>();
		flags.add(true);
		Training a = new Training("1",flags);
		Training b = new Training("2",flags);
		trueInstance.add(a);
		trueInstance.add(b);
		falseInstance.add(a);
		falseInstance.add(b);
		//root.purity(trueInstance, falseInstance);
		System.out.println(root.purity(trueInstance, falseInstance));
		assert(0.5==root.purity(trueInstance, falseInstance));
		//fail("Not yet implemented");
	}
	
	public void test2() {
		Node root = new Node("", null, null);
		List<Boolean> flags = new ArrayList<Boolean>();
		flags.add(false);
		flags.add(false);
		flags.add(false);
		flags.add(false);
		flags.add(true);
		flags.add(false);
		Training a = new Training("1",flags);
		List<String> attributes = new ArrayList<>();
		System.out.print(root.DecisionTree(a, attributes));
		
	}

}
