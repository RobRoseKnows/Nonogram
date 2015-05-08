/**
 * Credit: https://github.com/jkincali/Android-LinearLayout-Parser
 */

package edu.mbhs.cs.margonon;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ModDataLLParser implements Runnable {
	// ModDataLLParser object runs as a separate thread, not the main UI thread.
	// Handles things that might get blocked in efforts to update data for the app.
	
	public RelativeLayout mRelativeLayout;  // This is the pointer to the root layout object

	public ModDataLLParser(RelativeLayout mLL) {
		mRelativeLayout = mLL; // Constructor saves a pointer to the root layout
	}
	
	public void resetDataCell(ImageView x) {
		x.postInvalidate();
	}
	
	public void parseLayoutEntity(Object le) {
		if (le instanceof RelativeLayout) {
			View v;
			int childcount = ((RelativeLayout) le).getChildCount();
			for (int i=0; i < childcount; i++){
				v = ((RelativeLayout) le).getChildAt(i);
				if (v instanceof ImageView) {
				  // As is, the code is looking specifically for TextView, which will find
				  // all TextView objects as well as any objects based on classes extended
				  // from TextView. If forcing a redraw of the TextView object is not what
				  // is desired, then the code can be modified to suit here. (But remember
				  // this is not the UI thread so it cannot modify the object directly.)
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
		if (mRelativeLayout != null) parseLayoutEntity(mRelativeLayout);
	}

}