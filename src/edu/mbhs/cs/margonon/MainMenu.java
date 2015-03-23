package edu.mbhs.cs.margonon;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Button startButton = (Button) findViewById(R.id.play_game_button);
		Button howToButton = (Button) findViewById(R.id.mm_instructions_button);
		Button settingsButton = (Button) findViewById(R.id.mm_settings_button);
		
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainMenu.this, GameScreen.class);
				startActivity(i);
			}	
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
