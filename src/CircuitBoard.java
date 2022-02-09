import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 * @author Eli Sorensen
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException, InvalidFileFormatException {

		boolean hasStart = false;
		boolean hasEnd = false;

		int expectedTotal;

		int fileRows = 0;
		int fileCols;

		int totalValues = 0;

		int lineCount = 0;

		File file = new File(filename);

		Scanner fileScan = new Scanner(file);
		Scanner lineScan = new Scanner(file);

		try {

			ROWS = Integer.parseInt(fileScan.next());
			COLS = Integer.parseInt(fileScan.next());

			if (ROWS <= 0 || COLS <= 0) {

				throw new InvalidFileFormatException("Invalid file format. Grid size cannot be less than or equal to 0.");

			}

			expectedTotal = ROWS * COLS;

			board = new char[ROWS][COLS];

			while(lineScan.hasNextLine()) {

				lineScan.nextLine();
				lineCount++;

			}

			while (fileScan.hasNext()) {

				for (int row = 0; row < board.length; row++) {

					for (int col = 0; col < board[row].length; col++) {

						try {

							char nextChar = fileScan.next().charAt(0);

							if (ALLOWED_CHARS.indexOf(nextChar) == -1) {

								throw new InvalidFileFormatException("Invalid file format. Grid contains invalid character.");

							}

							if (nextChar == START) {

								if (hasStart) {

									throw new InvalidFileFormatException("Invalid file format. Grid contains more than one starting circuit position.");

								} else {

									board[row][col] = nextChar;
									startingPoint = new Point(row, col);
									hasStart = true;

								}

							} else if (nextChar == END) {

								if (hasEnd) {

									throw new InvalidFileFormatException("Invalid file format. Grid contains more than one ending circuit position.");

								} else {

									board[row][col] = nextChar;
									endingPoint = new Point(row, col);
									hasEnd = true;
								}

							} else if (nextChar == TRACE) {

								throw new InvalidFileFormatException("Invalid file format. Grid contains traced circuit positions.");

							} else {

								board[row][col] = nextChar;

							}

							totalValues++;

						} catch (NoSuchElementException e) {

							throw new InvalidFileFormatException("Invalid file format. Actual grid size does not match expected input size.");

						}

					}

					fileRows++;

				}

			}

		} catch (NumberFormatException e) {

			throw new InvalidFileFormatException("Invalid file format. Input file does not contain valid size.");

		}

		if (!hasStart) {

			throw new InvalidFileFormatException("Invalid file format. Grid does not contain starting circuit position.");

		} else if (!hasEnd) {

			throw new InvalidFileFormatException("Invalid file format. Grid does not contain ending circuit position.");

		}

		fileCols = totalValues / fileRows;

		if (totalValues != expectedTotal || ROWS != fileRows || COLS != fileCols || ROWS != lineCount - 1) {

			throw new InvalidFileFormatException("Invalid file format. Actual grid size does not match expected input size.");

		}

		fileScan.close();

	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
