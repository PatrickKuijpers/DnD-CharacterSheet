package nl.tcilegnar.dndcharactersheet.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Manager.Storage;
import nl.tcilegnar.dndcharactersheet.R;

public class MainMenu extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
	private static final int EXP_DEFAULT = 1050;
	private static final int EXP_MAX = 2500;
	private static final int PICKER_MIN_VALUE = 0;
	private static final int PICKER_MAX_VALUE = 100;
	private static final int PICKER_STEP_SIZE = 10;

	public enum SavedValues {
		CURRENT_PICKER_INDEX
	}

	private int currentExperience = EXP_DEFAULT;
	private int currentPickerIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		if (savedInstanceState != null) {
			currentPickerIndex = savedInstanceState.getInt(SavedValues.CURRENT_PICKER_INDEX.name());
		} else {
			currentPickerIndex = 0;
		}

		initExperienceBar();
		initNumberPicker();

		setListeners();
	}

	private void initExperienceBar() {
		ProgressBar expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
		expProgressBar.setMax(EXP_MAX);
	}

	private void initNumberPicker() {
		NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
		String[] expValues = getExperienceValues(PICKER_MIN_VALUE, PICKER_MAX_VALUE);
		numberPicker.setDisplayedValues(expValues);
		numberPicker.setMaxValue(expValues.length - 1);
		numberPicker.setMinValue(PICKER_MIN_VALUE);
		numberPicker.setValue(currentPickerIndex);
	}

	private String[] getExperienceValues(int minValue, int maxValue) {
		int numberOfSteps = ((maxValue - minValue) / PICKER_STEP_SIZE) + 1;
		String[] experienceValues = new String[numberOfSteps];
		int nextValue = minValue;
		for (int i = 0; i < experienceValues.length; i++) {
			experienceValues[i] = String.valueOf(nextValue);
			nextValue = nextValue + PICKER_STEP_SIZE;
		}
		return experienceValues;
	}

	private void setListeners() {
		(findViewById(R.id.experience_plus_button)).setOnClickListener(this);
		(findViewById(R.id.experience_min_button)).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		currentExperience = Storage.getSharedPreference(Storage.Key.CURRENT_EXP);
		updateExperienceViews(currentExperience);
	}

	private void updateExperienceViews(int newExperience) {
		TextView expTextView = (TextView) findViewById(R.id.experience_text);
		String expText = getString(R.string.experience_label) + " " + newExperience;
		expTextView.setText(expText);

		ProgressBar expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
		expProgressBar.setProgress(newExperience);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int currentPickerIndex = ((NumberPicker) findViewById(R.id.numberPicker)).getValue();
		outState.putInt(SavedValues.CURRENT_PICKER_INDEX.name(), currentPickerIndex);
	}

	@Override
	protected void onPause() {
		Storage.saveSharedPreference(Storage.Key.CURRENT_EXP, currentExperience);
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_char_A) {

		} else if (id == R.id.nav_char_B) {

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		if (viewId == R.id.experience_plus_button) {
			addExperience(getCurrentNumberPickerValue());
		}
		if (viewId == R.id.experience_min_button) {
			subtractExperience(getCurrentNumberPickerValue());
		}
	}

	public int getCurrentNumberPickerValue() {
		NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
		int currentIndex = numberPicker.getValue();
		return Integer.valueOf(numberPicker.getDisplayedValues()[currentIndex]);
	}

	private void addExperience(int addedExperience) {
		currentExperience = currentExperience + addedExperience;
		updateExperienceViews(currentExperience);
	}

	private void subtractExperience(int subtractedExperience) {
		currentExperience = currentExperience - subtractedExperience;
		updateExperienceViews(currentExperience);
	}
}
