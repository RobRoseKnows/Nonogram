package edu.mbhs.cs.margonon;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class gameGridAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Cell> cells;
	private int rows;
	private int cols;
	
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
					Log.d("DRAWING", "display was set to invalid state");
			} // end switch;
			
		} else {
			view = convertView;
		} // end if
		
		
		return view;
	}
	//TODO http://ocddevelopers.com/2014/extend-baseadapter-instead-of-arrayadapter-for-custom-list-items/
	//TODO http://stackoverflow.com/questions/5046320/a-grid-layout-of-icon-text-buttons
}
