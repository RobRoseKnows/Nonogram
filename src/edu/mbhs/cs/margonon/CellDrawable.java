package edu.mbhs.cs.margonon;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;

public class CellDrawable extends ShapeDrawable {
	Cell theCell;
	float size;
	int x;
	
	public CellDrawable(int r, int c, boolean wb, float s, int mx, int my) {
		super();
		theCell = new Cell(r, c, wb);
		size = s;
		super.setPadding(3, 3, 3, 3);
	}

	@Override
	public void draw(Canvas arg0) {
		int display = theCell.getDisplay();
		switch(display)
		{
			case 0:
				break;
			case 1:
				super.setShape(new RectShape());
				break;
			case 2:
				super.setShape(new OvalShape());
				break;
		} // end switch
		super.draw(arg0);
	} // end public void draw(Canvas arg0)
}
