package edu.mbhs.cs.margonon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GameScreen extends Activity {
	private int[][] gridSolution;
	private int rows;
	private int cols;
	private List<Integer> solList = new ArrayList<Integer>();
	private List<Cell> cellList = new ArrayList<Cell>();
	private List<Boolean> solListBool = new ArrayList<Boolean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_screen);
		createCellList();
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
			 *
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
	public void createCellList()
	{
		List<Cell> cl;
		InputStream is = getResources().openRawResource(R.raw.grids);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String[] lineStrings;
		int[] lineInts;
		String puzzleText = "";
		
		try {
			puzzleText = br.readLine();
			lineStrings = puzzleText.split("\\s");
			lineInts = new int[lineStrings.length - 2];
			System.out.println(puzzleText);
			System.out.println(lineStrings.length);
			
			for(int i = 0; i < lineStrings.length; i++)
			{
				if(i == 0) {
					cols = Integer.parseInt(lineStrings[i]);
					continue;
				} else if(i == 1) {
					rows = Integer.parseInt(lineStrings[i]);
					continue;
				} else {
					lineInts[i-2] = Integer.parseInt(lineStrings[i]);
					System.out.println(i + " " + lineInts[i-2]);
					solList.add(lineInts[i-2]);
					if(solList.get(i-2) == 1)
						solListBool.add(true);
					else
						solListBool.add(false);
				}
			}
			
			gridSolution = new int[rows][cols];
			
			for(int i = 0; i < lineInts.length; i++)
			{
				int y = i/cols;
				int x = i - y * cols;
				System.out.println("i=" + i + ", x=" + x + ", y=" + y);
				gridSolution[y][x] = lineInts[i];
				cellList.add(new Cell(x, y, solListBool.get(i)));
			}
		} catch (IOException e) {e.printStackTrace();}
		
	}
}