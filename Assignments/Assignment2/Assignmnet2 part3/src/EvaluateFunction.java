
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

import org.jgap.gp.terminal.Variable;

@SuppressWarnings("serial")
public class EvaluateFunction extends GPFitnessFunction {
	
	private final static int TrainSize = 600;
	private final static int TestSize = 99;

	private Variable CTVariable, USzVariable, UShpVariable, MAVariable, SESzVariable, BCVariable, MVariable, BNVariable,
			NNVariable;
	float[] CT = new float[TrainSize], USz = new float[TrainSize], UShp = new float[TrainSize], MA = new float[TrainSize],
			SESz = new float[TrainSize], BN = new float[TrainSize], BC = new float[TrainSize], NN = new float[TrainSize], M = new float[TrainSize],Class = new float[TrainSize];

	public EvaluateFunction(Variable cT2, Variable uSz2, Variable uShp2, Variable mA2, Variable sESz2, Variable bC2,
			Variable m2, Variable bN2, Variable nN2, String dataname) {
		InitializeArray(dataname);
		Loaddata(dataname);
		CTVariable = cT2;
		USzVariable = uSz2;
		UShpVariable = uShp2;
		MAVariable = mA2;
		SESzVariable = sESz2;
		BCVariable = bC2;
		MVariable = m2;
		BNVariable = bN2;
		NNVariable = nN2;
		
	}
	
	private void InitializeArray(String dataname) {
		if(dataname.equals("test")) {
			CT = new float[TestSize];
			USz = new float[TestSize];
			UShp = new float[TestSize];
			MA = new float[TestSize];
			SESz = new float[TestSize];
			BN = new float[TestSize]; 
			BC = new float[TestSize]; 
			NN = new float[TestSize]; 
			M = new float[TestSize];
			Class = new float[TrainSize];
		}else {
			CT = new float[TrainSize];
			USz = new float[TrainSize];
			UShp = new float[TrainSize];
			MA = new float[TrainSize];
			SESz = new float[TrainSize];
			BN = new float[TrainSize];
			BC = new float[TrainSize];
			NN = new float[TrainSize];
			M = new float[TrainSize];
			Class = new float[TrainSize];
		}
		
	}

	@Override
	protected double evaluate(final IGPProgram program) {
		double error = 0.0f;
		double begins = 0;
		double malignant = 0;
		double beginsSize = 0;
		double malignantSize = 0;

		Object[] noargs = new Object[0];
		for (int i = 0; i < CT.length; i++) {
			// Set the input values
			CTVariable.set(CT[i]);
			USzVariable.set(USz[i]);
			UShpVariable.set(UShp[i]);
			MAVariable.set(MA[i]);
			SESzVariable.set(SESz[i]);
			BCVariable.set(BC[i]);
			MVariable.set(M[i]);
			BNVariable.set(BN[i]);
			NNVariable.set(NN[i]);

			double value = program.execute_float(0, noargs);
			if (Double.isNaN(error)) {
				value = Double.MAX_VALUE;
			}

			if(Class[i]==2) {
				beginsSize++;
				if(value>=0) {
					begins++;
				}
			}else if (Class[i]==4) {
				malignantSize++;
				if(value<0) {
					malignant++;
				}
			}
		}
		//System.out.println( begins / beginsSize);
		//System.out.println( malignant / malignantSize);
		error = 1 - (begins / beginsSize + malignant / malignantSize) / 2;
		//System.out.println("Error: "+error);
		return error;
	}

	@SuppressWarnings("resource")
	private void Loaddata(String dataname) {
		try { // loading

			Path currentRelativePath = Paths.get("");
			String path = currentRelativePath.toAbsolutePath().toString() + "/breast-cancer-wisconsin-"+dataname+".data";

			File f = new File(path);
			BufferedReader data = new BufferedReader(new FileReader(f));
			String line;
			// data.readLine();
			// data.readLine();
			int i = 0;
			while ((line = data.readLine()) != null) {

				//System.out.println(line);
				String[] value = line.split(",");
				// System.out.println(CT[i]);
				for (int j = 1; j < 10; j++) {
					if (value[j].equals("?")) {
						value[j] = "-1";
					}
				}
				CT[i] = Float.parseFloat(value[1]);
				USz[i] = Float.parseFloat(value[2]);
				UShp[i] = Float.parseFloat(value[3]);
				MA[i] = Float.parseFloat(value[4]);
				SESz[i] = Float.parseFloat(value[5]);
				BN[i] = Float.parseFloat(value[6]);
				BC[i] = Float.parseFloat(value[7]);
				NN[i] = Float.parseFloat(value[8]);
				M[i] = Float.parseFloat(value[9]);
				Class[i]=Integer.parseInt(value[10]);
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);

		}
	}
}
