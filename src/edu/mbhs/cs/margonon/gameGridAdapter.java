package edu.mbhs.cs.margonon;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class gameGridAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Cell> cells;
	
	public gameGridAdapter(Context context, List<Cell> cellsIn) {
		mInflater = LayoutInflater.from(context);
		//for(int i = 0; i < cellsIn.length; i++)
			//for(int k = 0; k < cellsIn[0].length; k++)
				//cells.add(cellsIn[i][k]);
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
			view.setTag(position);
		} else {
			view = convertView;
		}
		return view;
	}
	//TODO http://ocddevelopers.com/2014/extend-baseadapter-instead-of-arrayadapter-for-custom-list-items/
	//TODO http://stackoverflow.com/questions/5046320/a-grid-layout-of-icon-text-buttons
}
