package comp307Assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length > 1)
			System.out.println("Should input two test file");
		else {
			// File file = new File(args[0]);

			String line;
			BufferedReader data = null;
			ArrayList<Iris> iristrain = new ArrayList<Iris>();
			ArrayList<Iris> irisTest = new ArrayList<Iris>();

			try { // loading
					// fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				Path currentRelativePath = Paths.get("");
				String path = currentRelativePath.toAbsolutePath().toString() + "/iris-training.txt";

				File f = new File(path);
				// JFileChooser fd = new JFileChooser();
				// fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				// fd.showOpenDialog(null);
				// File f = fd.getSelectedFile();

				data = new BufferedReader(new FileReader(f));

				// line = data.readLine();
				while ((line = data.readLine()) != null) {

					String[] values = line.split("\\s+");
					if (values.length < 4)
						break;
					System.out.println(values[0]);
					double sepalLength = Double.parseDouble(values[0]);
					double sepalWidth = Double.parseDouble(values[1]);
					double petalLength = Double.parseDouble(values[2]);
					double petalWidth = Double.parseDouble(values[3]);
					String type = values[4];
					Iris iris = new Iris(sepalLength, sepalWidth, petalLength, petalWidth, type);
					iristrain.add(iris);

				}
				data.close();
				// file = new File(args[1]);
				currentRelativePath = Paths.get("");
				path = currentRelativePath.toAbsolutePath().toString() + "/iris-test.txt";

				f = new File(path);
				data = new BufferedReader(new FileReader(f));

				// line = data.readLine();
				while ((line = data.readLine()) != null) {

					String[] values = line.split("\\s+");
					if (values.length < 4)
						break;
					double sepalLength = Double.parseDouble(values[0]);
					double sepalWidth = Double.parseDouble(values[1]);
					double petalLength = Double.parseDouble(values[2]);
					double petalWidth = Double.parseDouble(values[3]);
					String type = values[4];
					Iris iris = new Iris(sepalLength, sepalWidth, petalLength, petalWidth, type);
					irisTest.add(iris);

				}
				data.close();
				// System.out.println(data);// for test
				new KNearest(iristrain, irisTest);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);

			}
		}
	}
}
