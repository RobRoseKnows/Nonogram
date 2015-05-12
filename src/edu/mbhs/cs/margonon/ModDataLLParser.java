/**
 * Credit: https://github.com/jkincali/Android-LinearLayout-Parser
 * SO: http://stackoverflow.com/questions/5991968/how-to-force-an-entire-layout-view-refresh
 */

package edu.mbhs.cs.margonon;

import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class ModDataLLParser implements Runnable {
	// ModDataLLParser object runs as a separate thread, not the main UI thread.
	// Handles things that might get blocked in efforts to update data for the app.
	
	public GridView mGridView;  // This is the pointer to the root layout object

	public ModDataLLParser(GridView mLL) {
		mGridView = mLL; // Constructor saves a pointer to the root layout
	}
	
	public void resetDataCell(ImageView x) {
		x.invalidate();
	}
	
	public void parseLayoutEntity(Object le) {
		if (le instanceof GridView) {
			View v;
			int childcount = ((GridView) le).getChildCount();
			Log.d("THREAD_DO_WORK", "Children: " + childcount);
			for (int i=0; i < childcount; i++){
				v = ((GridView) le).getChildAt(i);
				if (v instanceof ImageView) {
				  // As is, the code is looking specifically for TextView, which will find
				  // all TextView objects as well as any objects based on classes extended
				  // from TextView. If forcing a redraw of the TextView object is not what
				  // is desired, then the code can be modified to suit here. (But remember
				  // this is not the UI thread so it cannot modify the object directly.)
					Log.d("THREAD_DO_WORK", "" + v);
					resetDataCell((ImageView) v);
				}
				else parseLayoutEntity(v);
			}
		}
	}
	
	@Override
    public void run() {
    // Make sure this thread is running low priority in the background.
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

    // Do something like get data from server and update global app data
		// ...
		
		// Then call the parser to get all the children. (In this case, each child
		// will be forced to redraw itself in order to display the new data.
		Log.d("THREAD_RUN", "ModDataLLParser Thread Running");
		if (mGridView != null) parseLayoutEntity(mGridView);
	}

}