package edu.mbhs.cs.margonon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GameScreen extends Activity {
	private int[][] gridSolution;
	private int rows;
	private int cols;
	private List<Cell> cellList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_screen);
		gridSolution = readGrids();
		final GridView gameGrid = (GridView) findViewById(R.id.gameGridView);
		gameGrid.setLayoutParams(new LayoutParams(rows, cols));
		gameGrid.setAdapter(new gameGridAdapter(this, cellList, rows, cols));
		gameGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				cellList.get(position).cycleNext();
				gameGrid.invalidate();
			}
		});
	}
	
	/**
	 * This function takes the data from the raw resource grids.txt and puts it in a 2D array.
	 * Currently it can only accept one grid.
	 * TODO Make this function work for multiple puzzles (grids) in the file.
	 * @return A 2D-array containing the solution for the grid.
	 *
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
			 *
			/*
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
			*
			
			/* This section of code is intended to read each line and plop it into the array.
			 *
			line = br.readLine();
			//TODO Add in default values if no grid is found. (And line is therefore null)
			lineStrings = line.split("\\s");
				//lineInts = new int[lineStrings.length];
				for(int i = 0; i < lineStrings.length; i++) {
					//TODO Handle what happens if the grid is bigger than what was originally specified.
					try {
						gs[on][i] = Integer.parseInt(lineStrings[i]);
						Log.d("PARSING", (on + ", " + i + " = "  + gs[on][i]));
						if(gs[on][i] == 0)
							cellList.add(new Cell(on, i, false));
						else
							cellList.add(new Cell(on, i, true));
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
	} // end public int[][] readGrids
	 */
	
	public List<Cell> createCellList()
	{
		List<Cell> cl;
		InputStream is = getResources().openRawResource(R.raw.grids);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		return cl;
	}
	
	private Cell[][] createCells()
	{
		Cell[][] c = new Cell[rows][cols];
		
		
		return c;
	} // end private Cell[][] createCells()

}
