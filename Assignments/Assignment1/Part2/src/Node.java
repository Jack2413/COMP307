import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {

	public static String baseline;
	private String bestAttr = "";
	private Node leftNode = null;
	private Node rightNode = null;
	private String name = "";
	private boolean isleafnode = false;
	private double probability = 0;
	private int count = 0;

	public Node(String bestAttr, Node leftNode, Node rightNode) {
		super();
		this.bestAttr = bestAttr;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	public Node(String name, double probability, boolean isleafnode, int count) {
		this.setCount(count);
		this.setIsleafnode(isleafnode);
		this.setProbability(probability);
		this.setName(name);

	}

	private void setCount(int count) {
		this.count = count;

	}

	public String getBestAttr() {
		return bestAttr;
	}

	public void setBestAttr(String bestAttr) {
		this.bestAttr = bestAttr;
	}

	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

	public Node BuildTree(Set<Training> instances, List<String> attributes) {
		this.count = instances.size();
		if (instances.isEmpty()) {
			return new Node(baseline, 0, true, count);
		} else if (isPure(instances)) {
			// System.out.print(instances);
			for (Training a : instances) {
				// System.out.print(a.getName());
				return new Node(a.getName(), 100, true, count);
			}
		} else if (attributes.isEmpty()) {
			int m = 0;
			int n = 0;
			String nameM = "";
			String nameN = "";
			for (Training a : instances) {
				if (m == 0) {
					nameM = a.getName();
					m++;
				} else if (nameM.equals(a.getName())) {
					m++;
				} else {
					nameN = a.getName();
					n++;
				}
			}
			if (m > n)
				return new Node(nameM, (double) m / (n + m) * 100, true, count);
			else {
				return new Node(nameN, (double) n / (n + m) * 100, true, count);
			}
		} else {// find best attribute:
			double purity = 0;
			double np = 0;
			int bestIndex = 0;

			Set<Training> BestTrueInstance = new HashSet<Training>();
			Set<Training> BestFalseInstance = new HashSet<Training>();
			Set<Training> trueInstance = new HashSet<Training>();
			Set<Training> falseInstance = new HashSet<Training>();

			for (int i = 0; i < attributes.size(); i++) {

				trueInstance = new HashSet<Training>();
				falseInstance = new HashSet<Training>();
				// System.out.println(attributes.get(i));
				for (Training a : instances) {
					if (a.getFlag().get(i)) {

						trueInstance.add(a);

						// System.out.print(a.getFlag().get(i));
						// System.out.println(" "+i);

					} else {
						falseInstance.add(a);
						// System.out.print(a.getFlag().get(i));
						// System.out.println(" "+i);
						// System.out.println(falseInstance+" "+i);
					}
					// System.out.println(trueInstance+" "+i);
					// System.out.println(falseInstance+" "+i);
				}
				// System.out.println(instances.size());
				// System.out.println(trueInstance + " " + i);
				// System.out.println(falseInstance + " " + i);

				np = purity(trueInstance, falseInstance);
				System.out.printf(attributes.get(i)+ " %5f%n",+ np);
				if (purity < np) {
					purity = np;
					this.bestAttr = attributes.get(i);

					// System.out.println(attributes.size());
					// this.name = bestAttr;
					bestIndex = i;
					BestTrueInstance = trueInstance;
					BestFalseInstance = falseInstance;
				}
			}
			System.out.println(attributes.get(bestIndex));
			System.out.println();
			// System.out.println(BestTrueInstance);
			// System.out.println(BestFalseInstance);
			// System.out.println(bestIndex);
			attributes.remove(bestIndex);

			// System.out.println(this.bestAttr);
			for (Training a : BestTrueInstance) {
				a.getFlag().remove(bestIndex);
			}
			for (Training b : BestFalseInstance) {
				b.getFlag().remove(bestIndex);
			}
			// System.out.println(this.bestAttr);

			// System.out.print(BestFalseInstance.size());

			Node newnode = new Node("", null, null);
			
			//if (this.leftNode == null) {
				//System.out.print("11111");
				this.leftNode = newnode.BuildTree(BestTrueInstance, attributes);
			//} else {
				this.rightNode = newnode.BuildTree(BestFalseInstance, attributes);
			//}

			// System.out.println(leftNode.bestAttr+"000");

			// System.out.println(rightNode.bestAttr+"111");
			// return new Node(bestAttr,leftNode,rightNode);
			// System.out.println(bestAtt);
		}
		// System.out.println(attributes);
		// System.out.println(this.bestAttr);
		return new Node(this.bestAttr, leftNode, rightNode);

	}

	public double purity(Set<Training> trueInstance, Set<Training> falseInstance) {
		int m = 0;
		int n = 0;
		double pt = (double) trueInstance.size() / (trueInstance.size() + falseInstance.size());
		double pf = (double) falseInstance.size() / (trueInstance.size() + falseInstance.size());
		String name = "";
		for (Training a : trueInstance) {
			if (m == 0) {
				name = a.getName();
				m++;
			} else if (name.equals(a.getName())) {
				m++;
			} else {
				n++;
			}
		}
		double impurityt = trueInstance.isEmpty() ? 0 : pt * 2 * ((double) m / (m + n)) * ((double) n / (m + n));
		// System.out.printf("pt " + pt + " m %d n %d\n", m, n);

		m = 0;
		n = 0;
		name = "";
		for (Training b : falseInstance) {
			if (m == 0) {
				name = b.getName();
				m++;
			} else if (name.equals(b.getName())) {
				m++;
			} else {
				n++;
			}

		}
		double impurityf = falseInstance.isEmpty() ? 0 : pf * 2 * ((double) m / (m + n)) * ((double) n / (m + n));
		// System.out.printf("pf " + pf + " m %d n %d\n", m, n);
		// System.out.println("impurityf: " + impurityf);
		// System.out.println("impurityt: " + impurityt);
		return 1 - (impurityf + impurityt);
	}

	public boolean isPure(Set<Training> instances) {
		for (Training a : instances) {
			for (Training b : instances) {
				if (!a.getName().equals(b.getName())) {
					return false;
				}
			}
		}
		return true;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void report(String indent,StringBuffer sBuffer) {

		if (isleafnode) {
			if (count == 0) {
				sBuffer.append(String.format("%sClass %s\n", indent, name));
			} else {
				sBuffer.append(String.format("%sClass %s, prob=%4.2f count: %d\n", indent, name, probability, count));
			}
		} else {
			sBuffer.append(String.format("%s%s = True:\n", indent, bestAttr));
			leftNode.report(indent + " ",sBuffer);
			sBuffer.append(String.format("%s%s = False:\n", indent, bestAttr));
			rightNode.report(indent + " ",sBuffer);
		}

	}

	public String DecisionTree(Training instance, List<String> attributes) {

		// System.out.println(instance);
		// System.out.println(attributes);
		if (isleafnode) {

			return name;
		}
		int size = attributes.size();
		for (int i = 0; i < size; i++) {
			// System.out.print(i);
			// System.out.println(this.bestAttr);
			if (attributes.get(i).equals(this.bestAttr)) {
				attributes.remove(i);
				// System.out.println(instance);
				// System.out.println(i);
				// System.out.println(instance.getFlag().get(i));
				if (instance.getFlag().get(i)) {
					instance.getFlag().remove(i);
					// System.out.println(this.leftNode);
					return leftNode.DecisionTree(instance, attributes);
				} else {
					// System.out.println(this.leftNode);
					instance.getFlag().remove(i);
					return rightNode.DecisionTree(instance, attributes);
				}
			}
		}
		// System.out.println(this.name);
		return this.name;
	}

	public boolean isIsleafnode() {
		return isleafnode;
	}

	public void setIsleafnode(boolean isleafnode) {
		this.isleafnode = isleafnode;
	}

}
