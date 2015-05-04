package edu.mbhs.cs.margonon;

import android.util.Log;
import android.widget.ImageView;

/**
 * This is the cell object for the nonogram program. Each cell has its on cell
 * object that holds what the current status of the cell is.
 * 
 * @author Robert Rose
 */
public class Cell {
	private final boolean willBeFull; // Whether the cell will be full (true) or not (false)
	private int display = 0; // 0 = empty, 1 = filled, 2 = crossed out
	private boolean correctNow; // Whether the cell is currently in its correct state.
	private int rowIndex = -1; // The row on which this cell is located.
	private int columnIndex = -1; // The column on which this cell is located.
	private ImageView iv;
	
	/**
	 * This is the primary constructor that takes a row index and a column index and
	 * creates a cell with the value based on a boolean value.
	 * 
	 * @param ri		Row on which the cell lies.
	 * @param ci		Column on which the cell lies.
	 * @param willBe	Whether cell will be full or whether it is empty true = 
	 * 					full, false = empty.
	 */
	public Cell(int ri, int ci, boolean willBe) {
		rowIndex = ri;
		columnIndex = ci;
		willBeFull = willBe;
		display = 0;
		correctNow = isCorrect();
		Log.d( "Cell Constructor", "Cell created (" + ci + ", " + ri + ") = " + willBe);
	} // end public Cell(int ri, int ci, boolean willBe)
	
	/**
	 * This is an identical constructor to the one above, but can take an int instead
	 * of a boolean value.
	 * 
	 * @param ri 		Row on which the cell lies.
	 * @param ci 		Column on which the cell lies.
	 * @param willBe 	Whether cell will be full or whether it is empty. 1 = full 
	 * 					(true), 0 = empty (false).
	 * 					
	 */
	public Cell(int ri, int ci, int willBe) {
		boolean b;
		if(willBe == 1)
			b = true;
		else
			b = false;
		rowIndex = ri;
		columnIndex = ci;
		willBeFull = b;
		display = 0;
		correctNow = isCorrect();
	}
	
	/**
	 * Called to determine whether the current status of the cell is correct.
	 * @return T/F. Whether the current status of the cell is correct.
	 */
	public boolean isCorrect()
	{
		switch(display)
		{
			case 0:
				if(willBeFull)
					return false;
				else
					return true;
			case 1:
				if(willBeFull) 
					return true;
				else 
					return false;
			case 2:
				if(willBeFull)
					return false;
				else
					return true;
			default:
				return false;
		} // end switch
	} // end public boolean isCorrect()
	
	/**
	 * Cycles the display variabble between different states.
	 */
	public void cycleNext()
	{
		if(display == 2)
			display = 0;
		else
			display++;
	}

	/**
	 * Getter function for the willBeFull variable.
	 * @return T/F. Whether the cell should be filled or empty
	 */
	public boolean getWillBeFull()
	{
		return willBeFull;
	} // end boolean getWillBeFull()
	
	/**
	 * Getter function for the display variable.
	 * @return 0 if cell is empty, 1 if cell is full, 2 if cell is crossed out
	 */
	public int getDisplay()
	{
		return display;
	} // end public int getDisplay()
	
	/**
	 * Getter function for the correctNow variable. Somewhat obsolete considering
	 * the isCorrect() function exists, but I want it here, so I'm going to keep it here.
	 * @return T/F. Whether the cell in its current state is correct.
	 */
	public boolean getCorrectNow()
	{
		return correctNow;
	} // end public boolean getCorrectNow()
	
	/**
	 * Getter function for the rowIndex, indicating which row the cell resides on the nonogram.
	 * @return int. Index of row of the nonogram the cell resides on.
	 */
	public int getRowIndex()
	{
		return rowIndex;
	} // end public int getRowIndex()
	
	/**
	 * Getter for the columnIndex, indicating which column the cell resides on the nongram.
	 * @return int. Index of coulmn of the nonogram the cell resides on.
	 */
	public int getColumnIndex()
	{
		return columnIndex;
	} // end public int getColumnIndex()
}
