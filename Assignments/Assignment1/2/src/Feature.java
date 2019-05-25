
public class Feature {
	private int[] row = new int[4];
	private int[] col = new int[4];;
	private boolean[] sgn = new boolean[4];;

	public Feature() {

		randomFeature();
	}

	private void randomFeature() {
		for (int i = 0; i < 4; i++) {
			row[i] = (int) (Math.random() * 10);
			col[i] = (int) (Math.random() * 10);
			sgn[i] = Math.random() > 0.5;
		}

	}

	public void print() {
		for (int i = 0; i < 4; i++) {
			System.out.print(row[i]+" ");
		}
		System.out.println();
		for (int i = 0; i < 4; i++) {
			System.out.print(col[i]+" ");
		}
		System.out.println();
		for (int i = 0; i < 4; i++) {
			System.out.print(sgn[i]+" ");
		}
		System.out.println();
	}

	public int[] getRow() {
		return row;
	}

	public void setRow(int[] row) {
		this.row = row;
	}

	public int[] getCol() {
		return col;
	}

	public void setCol(int[] col) {
		this.col = col;
	}

	public boolean[] getSgn() {
		return sgn;
	}

	public void setSgn(boolean[] sgn) {
		this.sgn = sgn;
	}
}
