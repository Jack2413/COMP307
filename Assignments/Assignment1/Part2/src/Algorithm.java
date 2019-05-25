import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algorithm {
	private String[] args;

	public Algorithm(String[] args) {
		super();
		Load(args, "");	
		double rate = 0;
		for (int i = 1; i <= 10; i++) {
			String num = i != 10 ? ("-run0" + i) : "-run10";
			rate += Load(args, num);		
		}
		System.out.println(rate/10*100+"%");
	}

	private double Load(String[] args, String num) {
		if (args.length > 1)
			System.out.println("Should input two test file");
		else {
			// File file = new File(args[0]);
			String line;
			BufferedReader data = null;
			List<String> attributes = new ArrayList<String>();
			Set<Training> instances = new HashSet<Training>();

			try { // loading
					// Node root = new Node("", null, null);
				Path currentRelativePath = Paths.get("");
				String path = currentRelativePath.toAbsolutePath().toString()
						+ "/hepatitis-training" + num + ".dat";

				File f = new File(path);

				data = new BufferedReader(new FileReader(f));
				line = data.readLine();
				String[] values = line.split("\\s+");
				String classA = values[0];
				String classB = values[1];
				line = data.readLine();
				values = line.split("\\s+");

				for (int i = 0; i < values.length; i++) {
					attributes.add(values[i]);
				}
				System.out.print(attributes + "\n");

				int count = 0;
				while ((line = data.readLine()) != null) {
					System.out.println(line);
					values = line.split("\\s+");
					if (values.length < 4)
						break;
					if (values[0].equals(classA))
						count++;
					String name = values[0];
					List<Boolean> flags = new ArrayList<Boolean>();
					for (int i = 1; i < values.length; i++) {
						flags.add(Boolean.parseBoolean(values[i]));
						// System.out.println(values[i]);
					}

					Training t = new Training(name, flags);
					System.out.println(t.toString());
					instances.add(t);

				}
				Node.baseline = (double) count / instances.size() > 0.5 ? classA : classB;
				System.out.print("finish load training\n");
				data.close();
				return BuildTree(instances, attributes, num);

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);

			}

		}
		return 0;
	}

	private double BuildTree(Set<Training> instances, List<String> attributes, String num) {
		Node root = new Node("", null, null);
		root = root.BuildTree(instances, attributes);
		// System.out.print(root.getBestAttr());
		return Test(root, num);
	}

	private double Test(Node root, String num) {
		List<Training> instance = new ArrayList<Training>();
		List<String> attributes = new ArrayList<String>();
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString() + 
				"/hepatitis-test" + num + ".dat";

		File f = new File(path);
		String line;
		BufferedReader data = null;

		try {
			data = new BufferedReader(new FileReader(f));
			line = data.readLine();

			line = data.readLine();
			String[] values = line.split("\\s+");

			for (int i = 0; i < values.length; i++) {
				attributes.add(values[i]);
			}

			while ((line = data.readLine()) != null) {

				values = line.split("\\s+");
				if (values.length < 4)
					break;

				String name = values[0];
				List<Boolean> flags = new ArrayList<Boolean>();
				for (int i = 1; i < values.length; i++) {
					flags.add(Boolean.parseBoolean(values[i]));
				}
				Training t = new Training(name, flags);
				// System.out.println(t.toString());
				instance.add(t);

			}
			data.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return report(root, attributes, instance, num);
	}

	private double report(Node root, List<String> attributes, List<Training> instance, String num) {
		
		
		// System.out.print("finish load test\n");
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("Reading training data from file hepatitis-training"+num+".data\n");
		sBuffer.append("2 categories\n");
		sBuffer.append("16 attributes");
		sBuffer.append("Reading test data from file hepatitis-test"+num+".data\n");
		sBuffer.append("Read " +instance.size()+" instances\n");
		sBuffer.append("Read " +attributes.size()+" attributes\n");
		sBuffer.append(attributes + "\n\n");
		sBuffer.append("Desction tree: \n");
		root.report("	",sBuffer);
		String result;
		List<String> attribute = new ArrayList<String>();
		clone(attribute, attributes);
		int count2 = 0;
		sBuffer.append("\nTest VS Algorithm output : \n");
		for (int i = 0; i < instance.size(); i++) {
			clone(attributes, attribute);
			// System.out.println(instance.get(1));
			result = root.DecisionTree(instance.get(i), attributes);
			sBuffer.append(instance.get(i).getName() + " ");
			sBuffer.append(result + "\n");
			if (result.equals(instance.get(i).getName()))
				count2++;
			System.out.println(root.DecisionTree(instance.get(i), attributes));
			// System.out.println(result );
		}
		double Accurate_rate = (double) count2 / instance.size();
		sBuffer.append("Accurate rate: " + Accurate_rate * 100 + "%");
		
		// sBuffer.append(root.report(" ",""));
		// System.out.println();
		try {
			Path currentRelativePath = Paths.get("");
			String path = currentRelativePath.toAbsolutePath().toString() + "/txt" + num;
			File file = new File(path);
			BufferedWriter ow = new BufferedWriter(new FileWriter(file));
			ow.write(sBuffer.toString());
			ow.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.print(num);
		return Accurate_rate;

	}

	private static void clone(List<String> a, List<String> b) {
		for (int i = 0; i < b.size(); i++) {
			if (a.size() == b.size()) {
				a.set(i, b.get(i));
			} else {
				a.add(i, b.get(i));
			}
		}

	}

}
