package com.amk2.musicrunner.main;

import com.amk2.musicrunner.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Main activity of MusicRunner+
 *
 * @author DannyLin
 */
public class MusicRunnerActivity extends Activity {

	private UIController mUIController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_runner);
		initialize();
		mUIController.onActivityCreate(savedInstanceState);
	}

	private void initialize() {
		mUIController = new UIController(this);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mUIController.onActivityRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mUIController.onActivityResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mUIController.onActivitySaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mUIController.onActivityPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mUIController.onActivityDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.music_runner, menu);
		return true;
	}

}
