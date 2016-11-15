package nl.tcilegnar.dndcharactersheet.Abilities.ViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView.OnEditorActionListener;

import nl.tcilegnar.dndcharactersheet.R;

public class AbilityNumberEditor extends LinearLayout implements OnClickListener {
    private EditText editText;
    private Button buttonPlus;
    private Button buttonMin;

    public AbilityNumberEditor(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.abilityNumberEditorStyle);
        inflate(context, R.layout.ability_number_editor, this);
        initView();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.ability_number_editor_edittext);
        buttonPlus = (Button) findViewById(R.id.ability_number_editor_button_plus);
        buttonMin = (Button) findViewById(R.id.ability_number_editor_button_min);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        buttonPlus.setOnClickListener(this);
        buttonMin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.ability_number_editor_button_plus) {
            addValue();
        } else if (viewId == R.id.ability_number_editor_button_min) {
            substractValue();
        }
    }

    public void validateInput() {
        if (getValue().isEmpty()) {
            setValue(0);
        }
    }

    public void setValue(int value) {
        editText.setText(String.valueOf(value));
    }

    public int getNumberValue() {
        return Integer.valueOf(getValue());
    }

    public String getValue() {
        return editText.getText().toString();
    }

    private void addValue() {
        int value = getNumberValue();
        value++;
        setValue(value);
    }

    private void substractValue() {
        int value = getNumberValue();
        value--;
        setValue(value);
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        editText.setOnEditorActionListener(onEditorActionListener);
    }
}
