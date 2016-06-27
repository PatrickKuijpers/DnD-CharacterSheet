package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceUpdater.ExperienceUpdateListener;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceCurrentLevel extends LinearLayout implements ExperienceUpdateListener {
	private Experience exp = new Experience();

	private ProgressBar expProgressBar;

	public ExperienceCurrentLevel(Context context, AttributeSet attrs) {
		super(context, attrs, R.attr.expCurrentLvlStyle);
		init(context);
	}

	private void init(Context context) {
		inflate(context, R.layout.experience_current_lvl, this);

		initViews();
		updateViewValues(exp.getCurrentExp());
	}

	private void initViews() {
		expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
		expProgressBar.setMax(exp.getMax());
	}

	private void updateViewValues(int newExp) {
		String expLabelText = App.getAppResources().getString(R.string.experience_label);
		String expText = expLabelText + " " + newExp;
		((TextView) findViewById(R.id.experience_text)).setText(expText);

		expProgressBar.setProgress(newExp);
	}

	public void saveExp() {
		exp.saveExp();
	}

	@Override
	public void onUpdateExperience(int expUpdateValue) {
		try {
			int newExp = exp.updateExperience(expUpdateValue);
			updateViewValues(newExp);
		} catch (Experience.ExpTooLowException | Experience.ExpTooHighException e) {
			e.printStackTrace();
			Toast.makeText(App.getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
		}
	}
}
