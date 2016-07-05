package model;

import java.util.Arrays;
import java.util.Random;

public class Field {
	private Cell[][] cells;
	private int falseFlags;
	private int trueFlags;
	private int trueFlagsLeft;
	private int rows;
	private int cols;
	private int emptyCellsLeft;

	// default value for field type is 10*10 array of cells
	public Field() {
		cells = new Cell[10][10];
		createCells("Easy");
	}

	// initiate the field type with initial value number of cells and a level
	public Field(int rows, int cols, String level) {
		this.rows = rows;
		this.cols = cols;

		cells = new Cell[rows][cols];
		createCells(level);

		// set up the TrueFlags in the Array
		setFlagsInField(trueFlags, 1);

		// set up the FalseFlags in the Array
		setFlagsInField(falseFlags, 2);

		// set the amount of flags left to find
		trueFlagsLeft = trueFlags;

		// set number of empty cells
		emptyCellsLeft = getfieldsize() - falseFlags - trueFlags;
	}

	public int getWidth() {
		return rows;
	}

	public int getHeight() {
		return cols;
	}

	// does the cell have any nearby flags? return true if it does
	public boolean getCellState(int x, int y) {
		if (cells[x][y].hasNearbyFlags() == true) {
			return true;
		} else
			return false;
	}

	// Helper Method: method creates a 2D cell array with a defined size (x and
	// y)
	private void createCells(String level) {
		if (level == "Easy") {
			falseFlags = (int) (getfieldsize() * 0.05);
			trueFlags = (int) (getfieldsize() * 0.01);
		} else if (level == "Normal") {
			falseFlags = (int) (getfieldsize() * 0.10);
			trueFlags = (int) (getfieldsize() * 0.05);
		} else if (level == "Hard") {
			falseFlags = (int) (getfieldsize() * 0.20);
			trueFlags = (int) (getfieldsize() * 0.10);
		}
		// ensure that there is at least 1 falseFlag and 1 trueFlag per game
		if (falseFlags < 1) {
			falseFlags = 1;
		}
		if (trueFlags < 1) {
			trueFlags = 1;
		}
		// initiate the Cell array with Cell objects
		for (int i = 0; i < cells.length; ++i) {
			for (int j = 0; j < cells[0].length; ++j) {
				cells[i][j] = new Cell();
			}
		}
	}

	/*
	 * takes a value for the number of true or false flags and a second int for
	 * assigning to the cell then places the required number of true of false
	 * flags in random positions around the field
	 */
	private void setFlagsInField(int flags, int flagType) {
		int count = 0;
		Random random = new Random();
		while (count < flags) {
			int randRow = random.nextInt(cells.length);
			int randCol = random.nextInt(cells[0].length);
			if (cells[randRow][randCol].getFlag() != 1
					&& cells[randRow][randCol].getFlag() != 2) {
				// give this cell the respective true or false value for flag
				// member
				cells[randRow][randCol].setFlag(flagType);
				// loop until all flags have been set
				count += 1;
				// set the surrounding cells to have nearby true/false flags
				setNearByFlags(flagType, randRow, randCol);
			}

		}
	}

	/*
	 * Helper Method: sets the nearby flags in each cell check for out of bound
	 * values assign the number of false flags and number of true flags to each
	 * cell that are nearby
	 */
	private void setNearByFlags(int flagType, int cellRow, int cellCol) {
		// set the boundaries top,bottom,left and right around the cell that
		// contains the flag
		int top = cellRow - 1;
		int bottom = cellRow + 1;
		// NB: because of the way the cells are populated, (0,0) is at the top
		// Right Hand Corner.

		int left = cellCol - 1;
		int right = cellCol + 1;

		if (top < 0)
			++top; // don't let top go out of bounds
		if (bottom > cells.length - 1)
			--bottom; // don't let bottom go out of bounds

		if (left < 0)
			++left; // don't let top go out of bounds
		if (right > cells[0].length - 1)
			--right; // don't let bottom go out of bounds

		for (int x = top; x <= bottom; ++x) { // hold the row constant and step
												// through the columns
			for (int y = left; y <= right; ++y) {
				if (x == cellRow && y == cellCol) {// don't add a nearby flag to
													// itself
					// do nothing
				} else {
					// set the surrounding cells to have a nearby flag of this
					// type
					cells[x][y].setNearbyFlags(flagType);
				}
			}

		}
	}

	// Helper Method: Return the size of the field (field width * field height)
	private int getfieldsize() {
		return cells.length * cells[0].length;
	}

	@Override
	public String toString() {
		StringBuilder stringbuilder = new StringBuilder();
		for (int i = 0; i < cells.length; ++i) {
			stringbuilder.append(Arrays.toString(cells[i]) + "\n");
		}
		return stringbuilder.toString();
	}

	public void unHide(int row, int col) {
		// set the current cell to an unhidden state
		cells[row][col].setHiddenState(false);

		// if the has no nearby flags (which means its surround cells are empty
		// also
		// then recursion needs to be completed
		if (cells[row][col].getFlag() == 0
				&& cells[row][col].hasNearbyFlags() == false) {
			// display all the empty cells next to it
			unHideNearbyEmptyCells(row, col);

		} else if (cells[row][col].getFlag() == 1) {
			// display the true flag
			System.out.println("found treasure!");

		} else if (cells[row][col].getFlag() == 2) {
			// display the false flag
			System.out.println("hit bomb :(");
		}

	}

	private void unHideNearbyEmptyCells(int cellRow, int cellCol) {
		// cellRow/cellCol refers to the CURRENT cell
		// set the boundaries top,bottom,left and right around the cell that
		// contains the flag
		int top = cellRow - 1;
		int bottom = cellRow + 1;
		// NB: because of the way the cells are populated, (0,0) is at the top
		// LEFT Hand Corner.

		int left = cellCol - 1;
		int right = cellCol + 1;
		// don't let top go out of bounds
		if (top < 0)
			++top;
		// don't let bottom go out of bounds
		if (bottom > cells.length - 1)
			--bottom;
		// don't let top go out of bounds
		if (left < 0)
			++left;
		// don't let bottom go out of bounds
		if (right > cells[0].length - 1)
			--right;
		// hold the row constant and step through the columns
		for (int x = top; x <= bottom; ++x) {
			for (int y = left; y <= right; ++y) {
				if (cells[x][y].hasNearbyFlags() == false
						&& cells[x][y].isHidden() == true) {
					cells[x][y].setHiddenState(false);
					// System.out.printf("cell checked in recursion: %d,%d", x,
					// y); //uncomment if you wish to debug
					int emptyCellsLeft = getEmptyCellsLeft();
					setEmptyCellsLeft(--emptyCellsLeft);
					unHideNearbyEmptyCells(x, y); // repeat check for the
													// surrounding cell
					
//					&& (cells[x][left].hasNearbyFlags() == false
//							|| cells[x][right].hasNearbyFlags() == false
//							|| cells[top][y].hasNearbyFlags() == false || cells[bottom][y]
//							.hasNearbyFlags() == false)
				}
			}

		}
	}

	public String getUnHiddenText(int row, int col) {
		return cells[row][col].getUnHiddenString();
	}

	public boolean isHidden(int row, int col) {
		if (cells[row][col].isHidden() == true) {
			return true;
		} else
			return false;
	}

	public int getCellFlag(int row, int col) {
		return cells[row][col].getFlag();
	}

	public boolean hasNearbyFlags(int i, int j) {
		return cells[i][j].hasNearbyFlags();
	}

	public int getTrueFlagsLeft() {
		return trueFlagsLeft;
	}

	public void decrementTrueFlagsLeft() {
		if (trueFlagsLeft != 0) {
			--trueFlagsLeft;
		}
	}

	public int getTotalFalseFlags() {
		return falseFlags;
	}

	public void unHideAll() {
		for (int i = 0; i < getWidth(); ++i) {
			for (int j = 0; j < getHeight(); ++j) {
				cells[i][j].setHiddenState(false);
			}
		}
	}

	public int getEmptyCellsLeft() {
		return emptyCellsLeft;
	}

	public void setEmptyCellsLeft(int emptyCellsLeft) {
		this.emptyCellsLeft = emptyCellsLeft;
	}
}
