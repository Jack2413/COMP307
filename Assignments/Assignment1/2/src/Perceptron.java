import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Perceptron {

	private static final int LIMTE = 1000;//training LIMTE
	private static final double STOPRATE = 1;
	private static final double LEARNINGRATE = 0.15;
	private List<Feature> features = new ArrayList<Feature>();
	private List<PImage> images = new ArrayList<PImage>();
	private List<Double> weights = new ArrayList<>();
	private StringBuffer sBuffer = new StringBuffer();

	public Perceptron(String[] args) {
		loadImage("image");
		generateRandomFeature();
		calculateFeatureValues();
		// LearningFromEachImage();
		generateRandomWeight();
		learningPerceptron();
		report("training");
		// Test:
		test();

	}

	private void test() {
		sBuffer = new StringBuffer();
		loadImage("test");
		generateRandomFeature();
		calculateFeatureValues();
		cheakAccuracyRate();
		report("test");
	}

	private void report(String s) {

		sBuffer.append("The LIMTE SIZE: " + LIMTE + "\n");
		sBuffer.append("The LEARNINGRATE: " + LEARNINGRATE + "\n");
		sBuffer.append("Correct set of weights: \n");
		sBuffer.append(weights.toString());

		try {
			Path currentRelativePath = Paths.get("");
			String path = currentRelativePath.toAbsolutePath().toString() + "/report-" + s;
			File file = new File(path);
			BufferedWriter ow = new BufferedWriter(new FileWriter(file));
			ow.write(sBuffer.toString());
			ow.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void learningPerceptron() {
		int count = 0;
		sBuffer.append("Process training:\n");
		while (count < LIMTE) {
			// System.out.println(weights.toString()+"\n");
			sBuffer.append("training " + count + " times\n");
			if (percetronIsAlwaysRight()) {
				break;
			}
			LearningFromEachImage();
			// System.out.println(weights.toString()+"\n");
			count++;

		}
	}

	private void LearningFromEachImage() {

		for (int i = 0; i < images.size(); i++) {
			int output = images.get(i).getLable().equals("X") ? 1 : 0;
			int Predicted = calculatePredicted(images.get(i));

			// System.out.print(output);
			// System.out.print(Predicted+"\n");
			if (output != Predicted) {
				adjustTheWeight(images.get(i), output - Predicted);
			}
			if (percetronIsAlwaysRight()) {
				break;
			}
			// sBuffer.append(weights.toString()+"\n");
		}

	}

	private void adjustTheWeight(PImage pImage, int value) {
		// System.out.println(value);
		for (int i = 0; i < pImage.getFeatureValue().size(); i++) {
			if (pImage.getFeatureValue().get(i) == 1) {
				weights.set(i, weights.get(i) + value * LEARNINGRATE * pImage.getFeatureValue().get(i));
			}
		}

	}

	private int calculatePredicted(PImage image) {
		double predicted = 0;
		for (int i = 0; i < image.getFeatureValue().size(); i++) {
			int f = image.getFeatureValue().get(i);
			double w = weights.get(i);
			predicted += (double) f * w;

		}
		return predicted > 0 ? 1 : 0;
	}

	private boolean percetronIsAlwaysRight() {
		double rate = cheakAccuracyRate();
		return rate >= STOPRATE;
	}

	private double cheakAccuracyRate() {
		String o = "";
		String p = "";
		double count = 0;
		for (int i = 0; i < images.size(); i++) {

			int output = images.get(i).getLable().equals("X") ? 1 : 0;
			int Predicted = calculatePredicted(images.get(i));
			o = o + output;
			p = p + Predicted;
			if (output == Predicted) {
				count++;
			}
		}
		sBuffer.append("Correct Output " + o + "\n");
		sBuffer.append("Predicted      " + p + "\n");
		sBuffer.append("Accurate rate: " + count / images.size() + "\n");
		System.out.println("Accurate rate: " + count / images.size());
		return (double) count / images.size();
	}

	private void generateRandomWeight() {
		weights = new ArrayList<>();
		for (int i = 0; i < 51; i++) {
			double weight = Math.random();
			weights.add(weight);
		}

	}

	private void calculateFeatureValues() {
		for (int i = 0; i < images.size(); i++) {
			calculatePerImageFeatureValues(images.get(i));
		}

	}

	private void calculatePerImageFeatureValues(PImage image) {
		List<Integer> featureValue = new ArrayList<Integer>();
		featureValue.add(1);// dummy value
		for (int i = 0; i < features.size(); i++) {
			int value = calculateSingleValue(features.get(i), image);
			featureValue.add(value);
		}
		image.setFeatureValue(featureValue);
	}

	private int calculateSingleValue(Feature feature, PImage image) {
		int sum = 0;
		for (int i = 0; i < 4; i++) {
			int row = feature.getRow()[i];
			int col = feature.getCol()[i];
			int value = feature.getSgn()[i] ? 1 : 0;
			if (image.getData()[row][col] == value)
				sum++;
		}

		return (sum >= 3) ? 1 : 0;
	}

	private void loadImage(String s) {
		images = new ArrayList<PImage>();
		try { // loading

			Path currentRelativePath = Paths.get("");
			String path = currentRelativePath.toAbsolutePath().toString() + "/"+s+".data";

			File f = new File(path);

			@SuppressWarnings("resource")
			BufferedReader data = new BufferedReader(new FileReader(f));
			String line;

			while ((line = data.readLine()) != null) {
				line = data.readLine();
				String lable = line.substring(1);
				int count = 0;
				line = data.readLine();
				String[] value = line.split(" ");
				int row = Integer.parseInt(value[0]);
				int col = Integer.parseInt(value[1]);
				PImage image = new PImage(row, col, lable);

				line = data.readLine();
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						if (count == line.length()) {
							line = data.readLine();
							count = 0;
						}
						int value1 = Character.getNumericValue(line.charAt(count));
						image.setValue(i, j, value1);
						count++;
					}
				}
				System.out.println(image.toString());
				images.add(image);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);

		}

	}

	private void generateRandomFeature() {
		features = new ArrayList<Feature>();
		for (int i = 0; i < 50; i++) {
			Feature f = new Feature();
			features.add(f);
		}

	}

}
