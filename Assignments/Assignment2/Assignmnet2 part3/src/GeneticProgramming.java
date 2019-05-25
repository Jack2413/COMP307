
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Pow;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;
public class GeneticProgramming extends GPProblem {

	private static Variable CT,USz,UShp,MA,SESz,BC,M,BN,NN;
	private EvaluateFunction funcition;
	
	public GeneticProgramming() throws InvalidConfigurationException {
		super(new GPConfiguration());

		GPConfiguration config = getGPConfiguration();

		CT = Variable.create(config, "CT", CommandGene.FloatClass);
		USz = Variable.create(config, "USz", CommandGene.FloatClass);
		UShp = Variable.create(config, "UShp", CommandGene.FloatClass);
		MA = Variable.create(config, "MA", CommandGene.FloatClass);
		SESz = Variable.create(config, "SESz", CommandGene.FloatClass);
		BC = Variable.create(config, "BC", CommandGene.FloatClass);
		M = Variable.create(config, "M", CommandGene.FloatClass);
		BN = Variable.create(config, "BN", CommandGene.FloatClass);
		NN = Variable.create(config, "NN", CommandGene.FloatClass);
		
		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setMaxInitDepth(7);
		config.setPopulationSize(1024);
		config.setMaxCrossoverDepth(17);
		funcition = new EvaluateFunction(CT,USz,UShp,MA,SESz,BC,M,BN,NN,"training");
		config.setFitnessFunction(funcition);
		config.setStrictProgramCreation(true);
	}

	public GPGenotype create() throws InvalidConfigurationException {

		GPConfiguration config = getGPConfiguration();
		// The return type of the GP program.
		Class[] types = { CommandGene.FloatClass };

		// Arguments of result-producing chromosome: none
		Class[][] argTypes = { {} };

		// Next, we define the set of available GP commands and terminals to
		// use.
		CommandGene[][] nodeSets = { {

				CT,USz,UShp,MA,SESz,BC,M,BN,NN,
				new Multiply(config, CommandGene.FloatClass),
				new Divide(config, CommandGene.FloatClass),
				new Subtract(config, CommandGene.FloatClass),
				new Add(config, CommandGene.FloatClass),
				new Pow(config, CommandGene.FloatClass),
				new Terminal(config, CommandGene.FloatClass, -1.0d, 1.0d, true) 
				} };

		GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);

		return result;
	}

	public static void main(String[] args) throws InvalidConfigurationException {
		
		//training
		GPProblem problem = new GeneticProgramming();
		GPGenotype gp = problem.create();
		gp.setVerboseOutput(true);
		gp.evolve(100);
		gp.outputSolution(gp.getAllTimeBest());
		IGPProgram best = gp.getAllTimeBest();
		
		//test
		EvaluateFunction testFunction = new EvaluateFunction(CT,USz,UShp,MA,SESz,BC,M,BN,NN,"test");
		double testFitness = testFunction.evaluate(best);
		System.out.printf("Fitness on test set: %f", testFitness);
	
	}
}