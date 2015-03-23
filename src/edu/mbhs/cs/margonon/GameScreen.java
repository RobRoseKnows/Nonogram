package edu.mbhs.cs.margonon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameScreen extends Activity {
	private int[][] gridSolution;
	private int rows;
	private int cols;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_screen);
		gridSolution = readGrids();
		
	}
	
	/**
	 * This function takes the data from the raw resource grids.txt and puts it in a 2D array.
	 * Currently it can only accept one grid.
	 * TODO Make this function work for multiple puzzles (grids) in the file.
	 * @return A 2D-array containing the solution for the grid.
	 */
	public int[][] readGrids() {
		int[][] gs;
		InputStream is = getResources().openRawResource(R.raw.grids);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			String[] lineStrings;
			int[] lineInts;
			
			/* This do-while statement is a work around so that we can define the first two values without 
			 * defining lineStrings and lineInts outside of a loop.
			 */
			do {
				lineStrings = line.split("\\s");
				lineInts = new int[lineStrings.length];
				//TODO Make sure there are only two numbers.
				for(int i = 0; i < lineStrings.length; i++){
					try{
						lineInts[i] = Integer.parseInt(lineStrings[i]);
					} catch(NumberFormatException e){
						lineInts[i] = 1;
					} // end try-catch
				} // end for
				cols = lineInts[0];
				rows = lineInts[1];
			} while(false);
			gs = new int[rows][cols];
			
			/* This section of code is intended to read each line and plop it into the array.
			 */
			line = br.readLine();
			//TODO Add in default values if no grid is found. (And line is therefore null)
			int on = 0;
			while(line != null)
			{
				//TODO Check to make sure the array is as big as the cols and rows values say it is.
				lineStrings = line.split("\\s");
				//lineInts = new int[lineStrings.length];
				for(int i = 0; i < lineStrings.length; i++) {
					//TODO Handle what happens if the grid is bigger than what was originally specified.
					try {
						gs[on][i] = Integer.parseInt(lineStrings[i]);
					} catch(NumberFormatException e) {
						//TODO Add something to respond to the exception.
					} // end try-catch
				} // end for
				br.readLine();
				on++;
			} // end while
			
		} catch (IOException e) {
			gs = new int[1][1];
			gs[0][0] = 1;
			e.printStackTrace();
		} // end try-catch
		return gs;
	}

}
