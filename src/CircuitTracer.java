import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 * @author Eli Sorensen
 */
public class CircuitTracer {

	private final boolean GUI_SUPPORTED = false;

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {

		System.out.println("Usage: java CircuitTracer <-s | -q> <-c | -g> file" +
				"\n options: \n -s 		uses Stack storage configuration" +
				"\n -q 		uses Queue storage configuration" +
				"\n -c 		prints solutions to console" +
				"\n -g 		displays solutions in a GUI window" +
				"\n file 		the name of a valid .dat input file");

	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {

		Storage stateStore;
		Storage.DataStructure structureToUse = Storage.DataStructure.stack;

		ArrayList<TraceState> bestPaths;

		CircuitBoard board;

		int startingRow;
		int startingCol;

		int boardRows;
		int boardCols;

		// Too many arguments
		if (args.length != 3) {

			printUsage();
			return; //exit the constructor immediately

		}

		// First argument must be -s OR -q
		if (!args[0].equalsIgnoreCase("-s") && !args[0].equalsIgnoreCase("-q")) {

			printUsage();
			return; //exit the constructor immediately

		}

		// Second argument must be -c OR -g
		if (!args[1].equalsIgnoreCase("-c") && !args[1].equalsIgnoreCase("-g")) {

			printUsage();
			return; //exit the constructor immediately

		}

		if (args[1].equalsIgnoreCase("-g") && !GUI_SUPPORTED) {

			System.out.println("Program GUI is not currently supported. Please run the program in console mode" +
					"using argument -c");
			return;

		}

		String filename = args[2];

		if (args[0].equalsIgnoreCase("-s")) {
			// stack storage

			structureToUse = Storage.DataStructure.stack;


		} else if (args[0].equalsIgnoreCase("-q")) {
			//queue storage

			structureToUse = Storage.DataStructure.queue;

		}

		stateStore = new Storage(structureToUse);

		try {

			bestPaths = new ArrayList();

			board = new CircuitBoard(filename);

			Point startingPoint = board.getStartingPoint();

			startingRow = startingPoint.x;
			startingCol = startingPoint.y;

			boardRows = board.numRows();
			boardCols = board.numCols();

			// add a new initial TraceState object (a path with one trace) to stateStore for each open position adjacent to the starting component

			if (board.isOpen(startingRow - 1, startingCol)) {

				stateStore.store(new TraceState(board, startingRow - 1, startingCol));

			}

			if (board.isOpen(startingRow + 1, startingCol)) {

				stateStore.store(new TraceState(board, startingRow + 1, startingCol));

			}

			if (board.isOpen(startingRow, startingCol - 1)) {

				stateStore.store(new TraceState(board, startingRow, startingCol - 1));

			}

			if (board.isOpen(startingRow, startingCol + 1)) {


				stateStore.store(new TraceState(board, startingRow, startingCol + 1));
			}

			while (!stateStore.isEmpty()) {

				TraceState nextState = (TraceState) stateStore.retrieve();

				if (nextState.isComplete()) {

					if (bestPaths.isEmpty() || nextState.pathLength() == bestPaths.get(0).pathLength()) {

						bestPaths.add(nextState);

					} else if (nextState.pathLength() < bestPaths.get(0).pathLength()) {

						bestPaths.clear();
						bestPaths.add(nextState);

					}

				} else {

					// generate all valid next TraceState objects from the current TraceState and add them to stateStore

					int currentRow = nextState.getRow();
					int currentCol = nextState.getCol();

					ArrayList<Point> possiblePoints = new ArrayList();

					possiblePoints.add(new Point(currentRow - 1, currentCol));
					possiblePoints.add(new Point(currentRow + 1, currentCol));
					possiblePoints.add(new Point(currentRow, currentCol - 1));
					possiblePoints.add(new Point(currentRow, currentCol + 1));

					for (Point possiblePoint : possiblePoints) {

						if(nextState.isOpen(possiblePoint.x, possiblePoint.y)) {

							stateStore.store(new TraceState(nextState, possiblePoint.x, possiblePoint.y));

						}

					}

				}


			}

			for (TraceState path : bestPaths) {

				CircuitBoard solutionBoard = path.getBoard();

				System.out.println(solutionBoard.toString());

			}

		} catch (FileNotFoundException e) {

			System.out.println("FileNotFoundException file " + filename + " does not exist in the program directory.");

		} catch (InvalidFileFormatException e) {

			System.out.println("InvalidFileFormatException " + e.getMessage());

		}

	}
	
}
