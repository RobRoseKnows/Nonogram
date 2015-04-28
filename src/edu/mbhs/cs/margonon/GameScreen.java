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
	 * Currently limited to accepting one grid within the grids.txt file.
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
		System.out.println("lvh: " + lvh.lineInts);
		
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
	public void putPuzzleInList(LineValueHolder lvh)
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
	}
}