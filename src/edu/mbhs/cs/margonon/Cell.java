package edu.mbhs.cs.margonon;

import android.widget.ImageView;

public class Cell {
	private final boolean willBeFull; 
	private int display = 0; // 0 = empty, 1 = filled, 2 = crossed out
	private boolean correctNow;
	private int rowIndex = -1;
	private int columnIndex = -1;
	private ImageView iv;
	
	public Cell(int ri, int ci, boolean willBe) {
		rowIndex = ri;
		columnIndex = ci;
		willBeFull = willBe;
		display = 0;
		correctNow = isCorrect();
	} // end public Cell(int ri, int ci, boolean willBe)
	
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
