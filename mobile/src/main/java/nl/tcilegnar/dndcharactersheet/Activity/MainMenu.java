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

import nl.tcilegnar.dndcharactersheet.R;

public class MainMenu extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
	public enum SavedValues {
		CURRENT_EXP, CURRENT_PICKER_NUMBER
	}

	private static final int EXP_DEFAULT = 1050;
	private static final int EXP_MAX = 2500;
	private static final int PICKER_MIN_VALUE = 0;
	private static final int PICKER_MAX_VALUE = 100;

	private int currentExperience = EXP_DEFAULT;
	private int currentPickerNumber = 0;

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
			currentExperience = savedInstanceState.getInt(SavedValues.CURRENT_EXP.name());
			currentPickerNumber = savedInstanceState.getInt(SavedValues.CURRENT_PICKER_NUMBER.name());
		} else {
			currentExperience = EXP_DEFAULT;
			currentPickerNumber = 0;
		}

		initExperience(currentExperience);
		initNumberPicker(currentPickerNumber);

		setListeners();
	}

	private void setListeners() {
		(findViewById(R.id.experience_plus_button)).setOnClickListener(this);
		(findViewById(R.id.experience_min_button)).setOnClickListener(this);
	}

	private void initExperience(int exp) {
		ProgressBar expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
		expProgressBar.setMax(EXP_MAX);

		updateExperienceViews(exp);
	}

	private void updateExperienceViews(int newExperience) {
		TextView expTextView = (TextView) findViewById(R.id.experience_text);
		String expText = getString(R.string.experience_label) + " " + newExperience;
		expTextView.setText(expText);

		ProgressBar expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
		expProgressBar.setProgress(newExperience);
	}

	private void initNumberPicker(int pickerNumber) {
		NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
		//		String[] test = getExperienceValues(PICKER_MIN_VALUE, PICKER_MAX_VALUE);
		//		numberPicker.setDisplayedValues(test);
		//		numberPicker.setMaxValue(test.length - 1);
		numberPicker.setMaxValue(PICKER_MAX_VALUE);
		numberPicker.setMinValue(PICKER_MIN_VALUE);
		numberPicker.setValue(pickerNumber);
	}

	private String[] getExperienceValues(int minValue, int maxValue) {
		int stapGrootte = 2;
		int aantalStappen = ((maxValue - minValue) / stapGrootte) + 1;
		String[] experienceValues = new String[aantalStappen];
		int nextValue = minValue;
		for (int i = 0; i < experienceValues.length; i++) {
			experienceValues[i] = String.valueOf(nextValue);
			nextValue = nextValue + stapGrootte;
		}
		return experienceValues;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int currentPickerNumber = ((NumberPicker) findViewById(R.id.numberPicker)).getValue();
		outState.putInt(SavedValues.CURRENT_PICKER_NUMBER.name(), currentPickerNumber);
		outState.putInt(SavedValues.CURRENT_EXP.name(), currentExperience);
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
		NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
		if (viewId == R.id.experience_plus_button) {
			addExperience(numberPicker.getValue());
		}
		if (viewId == R.id.experience_min_button) {
			subtractExperience(numberPicker.getValue());
		}
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
