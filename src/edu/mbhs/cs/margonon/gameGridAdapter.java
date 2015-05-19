package edu.mbhs.cs.margonon;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * This is the adapter that draws the grid.
 * 
 * @author Robert Rose
 */
public class GameGridAdapter extends BaseAdapter {
	private LayoutInflater mInflater; // The inflater.
	private List<Cell> cells = new ArrayList<Cell>(); // A list containing all the cell objects.
	private int rows; // The number of rows.
	private int cols; // The number of columns.
	private Context mContext;
	
	/**
	 * This is the constructor which takes the context, a list of cell objects and
	 * the grid dimensions to create an adapter which will render the grid.
	 * 
	 * @param context Context passed in from another class.
	 * @param cellsIn 
	 * @param r
	 * @param c
	 */
	public GameGridAdapter(Context context, List<Cell> cellsIn, int r, int c) {
		mInflater = LayoutInflater.from(context);
		rows = r;
		cols = c;
		mContext = context;
		for(int i = 0; i < rows*cols; i++)
			cells.add(cellsIn.get(i));
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cells.size();
	}

	@Override
	public Object getItem(int position) {
		return cells.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public List<Cell> getCells(int position) {
		return cells;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view;
		if(convertView == null) {
			view = new ImageView(mContext);
			view.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view.setPadding(1, 1, 1, 1);
			view.setLayoutParams(new GridView.LayoutParams(48,48));
		} else {
			view = (ImageView) convertView;
		} // end if
		
		switch(((Cell) getItem(position)).getDisplay())
		{
			case 0:
				view.setImageResource(R.drawable.white);
				break;
			case 1:
				view.setImageResource(R.drawable.colored);
				break;
			case 2:
				view.setImageResource(R.drawable.cross);
				break;
			default:
				Log.e("DRAWING", "display was set to invalid state");
		} // end switch;
		return view;
	}
	//TODO http://ocddevelopers.com/2014/extend-baseadapter-instead-of-arrayadapter-for-custom-list-items/
	//TODO http://stackoverflow.com/questions/5046320/a-grid-layout-of-icon-text-buttons
	//TODO http://androidexample.com/Grid_Layout_-_Android_Example/index.php?view=article_discription&aid=75&aaid=99
	//TODO http://androidexample.com/Custom_Grid_Layout_-_Android_Example/index.php?view=article_discription&aid=76&aaid=100
}
