package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import nl.tcilegnar.dndcharactersheet.Experience.View.ExperienceInput;
import nl.tcilegnar.dndcharactersheet.Experience.View.ExperiencePicker;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceUpdater extends FrameLayout implements OnClickListener {
	private ExperiencePicker expPicker;
	private ExperienceInput expInput;

	private ExperienceUpdateListener experienceUpdateListener;

	public ExperienceUpdater(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		View view = inflate(context, R.layout.experience_updater, this);

		expPicker = (ExperiencePicker) view.findViewById(R.id.experience_picker);
		expInput = (ExperienceInput) view.findViewById(R.id.experience_input);

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
		int expUpdateValue = getExpValue();
		if (viewId == R.id.experience_plus_button) {
			experienceUpdateListener.onUpdateExperience(expUpdateValue);
		}
		if (viewId == R.id.experience_min_button) {
			experienceUpdateListener.onUpdateExperience(-expUpdateValue);
		}
	}

	public void setExperienceUpdateListener(ExperienceUpdateListener experienceUpdateListener) {
		this.experienceUpdateListener = experienceUpdateListener;
	}

	public interface ExperienceUpdateListener {
		void onUpdateExperience(int expUpdateValue);
	}
}
