package app;

import constants.NeighborsType;
import constants.Rules;
import model.Grid;
import model.hashlife.Hashlife;
import model.rule.Rule;
import util.AbstractListenableModel;

/**
 * The class which play the game
 */
public class Game extends AbstractListenableModel {
    protected Grid grid;
	protected Grid previousGrid;
    protected Generator generator;
	/** If true generate next gen using hashlife else use classic algo */
	protected boolean useHashlife = false;

	/** The thread which generate generation in loop. We're using another class cause
	 * game already extends AbstractListenableModel and connot extends Thread again
	 */
	protected GeneratorThread generatorThread;
	protected Hashlife hashlife;

	private int iteration,nBLiveCell;

	/** Number of milliseconds between each generation */
	protected int genWaitIntervalInMls = 100;

	/** Last generation computation time in milliseconds */
	protected double lastGenComputeTimeInNnos = 0;

	/**
     * Build a new instance
     * @param grid
     * @param generator
     */
	public Game(Grid grid, Generator generator) {
		super();
		this.grid = grid;
		this.previousGrid = null;
		this.generator = generator;
		this.hashlife = new Hashlife(generator);
		this.iteration = 0;
		this.nBLiveCell = 0;
	}
    
    // Getters and setters
	public Grid getGrid() {
		return grid;
	}
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	public Generator getGenerator() {
		return generator;
	}
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

	public boolean isUseHashlife() {
		return useHashlife;
	}

	public void setUseHashlife(boolean useHashlife) {
		this.useHashlife = useHashlife;
	}

	public double getLastGenComputeTimeInNnos() {
		return lastGenComputeTimeInNnos;
	}

	public void setLastGenComputeTimeInNnos(double timeInNnos) {
		this.lastGenComputeTimeInNnos = timeInNnos;
	}

	

	
	public int getGenWaitIntervalInMls() {
		return genWaitIntervalInMls;
	}

	public void setGenWaitIntervalInMls(int genWaitIntervalInMls) {
		this.genWaitIntervalInMls = genWaitIntervalInMls;
	}


	public int getIteration() {
		return this.iteration;
	}

	public void setIteration(int iter) {
		this.iteration=iter;
	}

	public int getNbLiveCell() {
		 this.nBLiveCell=this.grid.getAliveCell();
		 return this.nBLiveCell;
	}


	// Methods

	/**
	 * Start the simulator thread
	 */
	public void runGenThread() {
		this.generatorThread = new GeneratorThread(this);
		this.generatorThread.start();
	}

	/**
	 * Stop the simulator thread
	 */
	public void stopGenThread() {
		this.generatorThread.stopThread();
	}

	/** @todo: implements this function */
	public void playConsole() {
	}

	/**
	 * Backup the current grid to use it after for the prev function
	 */
	public void backupCurrentGrid() {
		this.previousGrid = this.grid;
	}

	/**
	 * Generate the next generation of grid using classic gol algo
	 */
	public void nextGenerationClassic() {
		backupCurrentGrid();

		double start = System.nanoTime();
		
		this.grid = this.generator.nextGeneration(this.grid);

		double end = System.nanoTime();
		setLastGenComputeTimeInNnos(end - start); // calculate gen computation time
		
		this.fireChangement(null); // notify view for changement

	}

	/**
	 * Generate next generation usign hashlife algo
	 */
	public void nextGenerationHashlife() {
		backupCurrentGrid();

		double start = System.nanoTime();

		this.grid = hashlife.jumpGenerations(grid,1);

		double end = System.nanoTime();
		setLastGenComputeTimeInNnos(end - start); // calculate gen computation time

		this.fireChangement(null); // notify view for changement

	}


	/**
	 * Boutton back: retourne true quand il a pu charger la grille prec
	 * et false sinon. Donc on ne peut pas aller deux fois en arrière max
	 * une fois. quand c'est plus d'une fois ca retourne false
	 * @return s'il existe une grille prev ou pas
	 */
	public boolean previousGeneration() {
		if(this.previousGrid != null) {
			this.grid = this.previousGrid;
			this.previousGrid = null;
			this.fireChangement(null);
			return true;
		} else {
			this.fireChangement(null);
			return false;
		}

	}

	/**
	 * Increase the speed of the simulation
	 */
	public void increaseSpeed() {
		if(this.genWaitIntervalInMls > 100)
			this.genWaitIntervalInMls -= 100;
	}

	/**
	 * Decrease the speed of the simulation
	 */
	public void decreaseSpeed() {
		if(this.genWaitIntervalInMls < 10000)
			this.genWaitIntervalInMls += 100;
	}

	/**
	 * Reset the grid
	 */
	public void resetGrid() {
		this.grid.reset();
		this.iteration = 0;
		this.fireChangement(null);
	}

	/**
	 * Use classic algo
	 */
	public void useClassicAlgo() {
		this.useHashlife = false;
	}

	/**
	 * Use hashlife algo
	 */
	public void useHashlifeAlgo() {
		this.useHashlife = true;
	}

	/**
	 * Randomize the grid cells states
	 */
	public void useRandom() {
		this.getGrid().initRandomGrid();
		this.fireChangement(null);
	}

	/**
	 * Change the grid pattern 
	 * @param pattern the new pattern to use
	 */
	public void usePattern(String pattern) {
		this.grid.initPattern(pattern);
		this.fireChangement(null);
	}

	/**
	 * Change the neighbors type
	 * @param type the neighbors type
	 */
	public void changeNeighborsType(String type) {
		int[][] nType = NeighborsType.getType(type);
		generator.setNeighbors(nType);
	}

	/**
	 * Change the neighbors type using custom coord
	 * @param type the neighbors type
	 * @return wether the operation worked or not
	 */
	public boolean changeNeighborsTypeCustom(String type) {
		boolean res = true;
		
		// Check if the string matches the correct format
		String regex = "\\((-?\\d+),(-?\\d+)\\)(;\\((-?\\d+),(-?\\d+)\\))*";
		if (!type.matches(regex)) {
			res = false;
		}
		
		generator.setNeighbors(NeighborsType.stringToCoord(type));

		return res;
	}

	/**
	 * Change the rule  of the generation
	 * @param rule the new rule
	 * @return wether the operation worked or not
	 */
	public boolean useCustomRule(String rulestr) {
		try {
			Rule cRule = new Rule(rulestr);
			this.generator.setRule(cRule);
			return true;
		} catch (IllegalArgumentException e) { // thrown when rule does not match required format
			return false;
		}
	}

	/**
	 * Use gol rule for simulation
	 */
	public void useGolRule() {
		this.generator.setRule(new Rule(constants.Rules.GAMEOFLIFE));
	}
}