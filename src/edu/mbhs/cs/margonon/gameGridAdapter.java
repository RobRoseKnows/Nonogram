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
public class gameGridAdapter extends BaseAdapter {
	private LayoutInflater mInflater; // The inflater.
	private List<Cell> cells = new ArrayList<Cell>(); // A list containing all the cell objects.
	private int rows; // The number of rows.
	private int cols; // The number of columns.
	
	/**
	 * This is the constructor which takes the context, a list of cell objects and
	 * the grid dimensions to create an adapter which will render the grid.
	 * 
	 * @param context Context passed in from another class.
	 * @param cellsIn 
	 * @param r
	 * @param c
	 */
	public gameGridAdapter(Context context, List<Cell> cellsIn, int r, int c) {
		mInflater = LayoutInflater.from(context);
		for(int i = 0; i < rows; i++)
			for(int k = 0; k < cols; k++)
				cells.add(cellsIn.get(i*(cols - 1) + k));
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if(convertView == null) {
			view = mInflater.inflate(R.layout.button_grid_view, parent, false);
			ImageView imgView = (ImageView) view.findViewById(R.id.imageInGrid);
			//ImageView imgView = new ImageView(null);
			imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imgView.setPadding(4, 4, 4, 4);
			switch(cells.get(position).getDisplay())
			{
				case 0:
					imgView.setImageResource(R.drawable.white);
					break;
				case 1:
					imgView.setImageResource(R.drawable.colored);
					break;
				case 2:
					imgView.setImageResource(R.drawable.cross);
					break;
				default:
					Log.e("DRAWING", "display was set to invalid state");
			} // end switch;
			//imgView.setLayoutParams(new GridView.LayoutParams(250,250));
			view = imgView;
		} else {
			view = convertView;
		} // end if
		
		
		return view;
	}
	//TODO http://ocddevelopers.com/2014/extend-baseadapter-instead-of-arrayadapter-for-custom-list-items/
	//TODO http://stackoverflow.com/questions/5046320/a-grid-layout-of-icon-text-buttons
	//TODO http://androidexample.com/Grid_Layout_-_Android_Example/index.php?view=article_discription&aid=75&aaid=99
	//TODO http://androidexample.com/Custom_Grid_Layout_-_Android_Example/index.php?view=article_discription&aid=76&aaid=100
}
