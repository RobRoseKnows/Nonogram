package edu.mbhs.cs.margonon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class GameScreen extends Activity {
	private int[][] gridSolution;
	private int rows;
	private int cols;
	
	private List<Integer> solList = new ArrayList<Integer>();
	private List<Cell> cellList = new ArrayList<Cell>();
	private List<Boolean> solListBool = new ArrayList<Boolean>();
	
	private GridView gameGrid;
	
	private String verHint = "";
	private String horHint = "";
	
	private LinearLayout vertical;
	private LinearLayout horizontal;
	private boolean hintsDrawn = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_screen);
		createCellList(); // I'm wondering if the problem has to do with the way it's putting the values into cellList.
		// TODO Handle errors if there's no grid.
		
		gameGrid = (GridView) findViewById(R.id.gameGridView);
		GameGridAdapter gameGridA = new GameGridAdapter(this, cellList, rows, cols);
		gameGrid.setAdapter(gameGridA);
		gameGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				Cell cellClicked = (Cell) gameGrid.getAdapter().getItem(position);
				cellClicked.cycleNext();
				refreshImageView(position);
			}
		});
		
		vertical = (LinearLayout) findViewById(R.id.vertical_hint_layout);
		horizontal = (LinearLayout) findViewById(R.id.horizontal_hint_layout);
		makeXHints();
		makeYHints();
	}
	
	// TODO Set up this to return false when gridSolution is null and then make onCreate cease operations.
	public void redrawHints() {
		if(hintsDrawn) {
			Log.i("DRAW_HINTS", "Hints already drawn.");
			return;
		}
		
		if(gameGrid.getChildCount() != cellList.size()) { 
			Log.i("DRAW_HINTS", "Grid not complete.");
			return;
		}
	}

	/* Test grid:
	 * 
	 * 1 0 1 0 1 | 1,1,1
	 * 0 1 1 0 0 | 2
	 * 1 1 0 0 0 | 2
	 * 0 1 1 1 1 | 4
	 * 0 1 1 0 1 | 2,1
	 * ø ø ø ø ø
	 * 1 4 2 1 1
	 * 1   2   2
	 */
	
	public void makeXHints() {
		String vh;
		for(int y = 0; y < gridSolution.length; y++) {
			vh = "";
			int cummulative = 0;
			for(int x = 0; x < gridSolution[y].length; x++) {
				cummulative += gridSolution[y][x];
				if(gridSolution[y][x] == 0 && cummulative > 0) {
					vh += " " + cummulative + " ";
					cummulative = 0;
				}
			}
			if(cummulative > 0){
				vh += " " + cummulative + " ";
			}
			
			//Log.d("MAKE_HINTS", "gameGrid= " + gameGrid + " Child= " + gameGrid.getChildAt(0) + " Child count= " + gameGrid.getChildCount());
			TextView tv = new TextView(GameScreen.this);
			LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
			tv.setText(vh);
			vertical.addView(tv, lp);
		}
	}
	
	public void makeYHints() {
		String hh;
		
		// This does assume it's a rectangle, which is important to note.
		for(int x = 0; x < gridSolution[0].length; x++) {
			hh = "";
			int cummulative = 0;
			for(int y = 0; y < gridSolution.length; y++) {
				cummulative += gridSolution[y][x];
				if(gridSolution[y][x] == 0 && cummulative > 0) {
					hh += cummulative + "\n";
					cummulative = 0;
				}
			}
			if(cummulative > 0) {
				hh += cummulative + "\n";
			}
			
			TextView tv = new TextView(GameScreen.this);
			LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
			tv.setText(hh);
			horizontal.addView(tv, lp);
		}
	}
	
	/**
	 * Function is in response to players clicking the checkAnswer button. It runs
	 * through all the cells in cellList and checks to see if they are all solved
	 * correctly. If they are, it sets the text of the button to "Correct!". If not,
	 * it sets it to "Incorrect!".
	 * @param v The button clicked.
	 */
	public void checkAnswerClick(View v) {
		boolean isCorrect = true;
		for(int i = 0; i < cellList.size(); i++) {
			isCorrect = isCorrect && cellList.get(i).getCorrectNow();
		}
		
		Button bv = (Button) v;
		if(isCorrect) {
			Log.i("BUTTON_CLICK", "Puzzle is correctly solved!");
			bv.setText("Correct!");
		} else {
			Log.i("BUTTON_CLICK", "Puzzle is not solved!");
			bv.setText("Incorrect! Click to check again!");
		}
	} // end public void checkAnswerClick(View v)
	
	/**
	 * This god damned function took forever to create and triggers the ImageView within
	 * gameGrid to redraw.
	 * @param pos The position of the users click.
	 */
	public void refreshImageView(final int pos){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView runningImg = (ImageView) gameGrid.getChildAt(pos);
				switch(((Cell) gameGrid.getAdapter().getItem(pos)).getDisplay())
				{
					case 0:
						runningImg.setImageResource(R.drawable.white);
						break;
					case 1:
						runningImg.setImageResource(R.drawable.colored);
						break;
					case 2:
						runningImg.setImageResource(R.drawable.cross);
						break;
					default:
						Log.e("DRAWING", "display was set to invalid state");
				} // end switch;
				runningImg.invalidate();
			}					
		});
	}
	
	
	/**
	 * This function takes the data from the raw resource grids.txt and puts it in a 2D array.
	 * Currently limited to accepting one grid within the grids.txt file.
	 * TODO Make it accept more than one grid sometime over the summer.
	 */
	public void createCellList()
	{
		List<Cell> cl;
		InputStream is = getResources().openRawResource(R.raw.grids);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String[] lineStrings;
		int[] lineInts;
		String puzzleText = "";
		LineValueHolder lvh; // This will act as a way to handle 
		
		try {
			puzzleText = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		lineStrings = puzzleText.split("\\s");
		lineInts = new int[lineStrings.length - 2];
		lvh = new LineValueHolder(lineStrings, lineInts);
		
		putPuzzleInList(lvh);
		createCellList(lvh);
	}
	
	/**
	 * Creates a Cell object for each cell in the grid and puts them into the ArrayList
	 * solListBool. It also puts the integer values of each Cell and puts them into the 
	 * 2D-array gridSolution.
	 * @param lvh
	 */
	private void createCellList(LineValueHolder lvh){
		gridSolution = new int[rows][cols];
		
		for(int i = 0; i < lvh.lineInts.length; i++)
		{
			int y = i/cols;
			int x = i - y * cols;
			gridSolution[y][x] = lvh.lineInts[i];
			cellList.add(new Cell(x, y, solListBool.get(i)));
		}
	}
	
	/**
	 * Takes a LineValueHolder object which it uses to define lineInts, solList
	 * and solListBool. LineValueHolder initially only contains numbers as strings that
	 * define the grid in LineValueHolder.lineStrings[]. Method takes the numbers and 
	 * puts them in an array of integers held in LineValueHolder. Method also adds 
	 * @param lvh
	 */
	private void putPuzzleInList(LineValueHolder lvh)
	{
		for(int i = 0; i < lvh.lineStrings.length; i++)
		{
			if(i == 0) {
				cols = Integer.parseInt(lvh.lineStrings[i]);
				continue;
			} else if(i == 1) {
				rows = Integer.parseInt(lvh.lineStrings[i]);
				continue;
			} else {
				lvh.lineInts[i-2] = Integer.parseInt(lvh.lineStrings[i]);
				solList.add(lvh.lineInts[i-2]);
				if(solList.get(i-2) == 1)
					solListBool.add(true);
				else
					solListBool.add(false);
			}
		}
	}
	
	private class LineValueHolder
	{
		public String[] lineStrings;
		public int[] lineInts;
		
		public LineValueHolder(String[] ls, int[] li)
		{
			lineStrings = ls;
			lineInts = li;
		}
		
		public String toString()
		{
			return "LS: " + lineStrings + ", LI: " + lineInts;
		}
	}
}