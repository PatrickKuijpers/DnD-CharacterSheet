package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import nl.tcilegnar.dndcharactersheet.Experience.View.ExperienceInput;
import nl.tcilegnar.dndcharactersheet.Experience.View.ExperiencePicker;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceUpdater extends LinearLayout implements OnClickListener {
	private ExperiencePicker expPicker;
	private ExperienceInput expInput;

	private ExperienceUpdateListener experienceUpdateListener;

	public ExperienceUpdater(Context context, AttributeSet attrs) {
		super(context, attrs, R.attr.expUpdaterStyle);
		init(context);
	}

	private void init(Context context) {
		inflate(context, R.layout.experience_updater, this);

		expPicker = (ExperiencePicker) findViewById(R.id.experience_picker);
		expInput = (ExperienceInput) findViewById(R.id.experience_input);

		(findViewById(R.id.experience_plus_button)).setOnClickListener(this);
		(findViewById(R.id.experience_min_button)).setOnClickListener(this);
	}

	public int getExpValue() {
		if (expInput.hasInput()) {
			return expInput.getExpValue();
		} else {
			return expPicker.getCurrentSelectedExpValue();
		}
	}

	@Override
	public void onClick(View view) {
		int viewId = view.getId();
		if (viewId == R.id.experience_plus_button) {
			experienceUpdateListener.onUpdateExperience(getExpValue());
		}
		if (viewId == R.id.experience_min_button) {
			experienceUpdateListener.onUpdateExperience(-getExpValue());
		}
	}

	public void setExperienceUpdateListener(ExperienceUpdateListener experienceUpdateListener) {
		this.experienceUpdateListener = experienceUpdateListener;
	}

	public interface ExperienceUpdateListener {
		void onUpdateExperience(int expUpdateValue);
	}
}
