package nl.tcilegnar.dndcharactersheet.Experience.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class ExperienceInput extends EditText {
	public ExperienceInput(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean hasInput() {
		return getText().length() > 0;
	}

	public int getExpValue() {
		String expValueInput = getText().toString();
		return Integer.valueOf(expValueInput);
	}
}
