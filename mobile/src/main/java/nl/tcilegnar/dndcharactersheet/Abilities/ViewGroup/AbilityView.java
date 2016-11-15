package nl.tcilegnar.dndcharactersheet.Abilities.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import nl.tcilegnar.dndcharactersheet.Abilities.Ability;
import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.KeyboardUtil;

public class AbilityView extends LinearLayout implements OnClickListener, OnEditorActionListener {
    private final Context context;
    private final Ability ability;

    private ImageView abilityImageview;
    private TextView abilityAbbreviation;
    private TextView abilityIndicator;
    private View abilityEditor;
    private EditText abilityEditText;
    private ImageButton abilityEditButton;
    private Button abilityEditorButtonPlus;
    private Button abilityEditorButtonMin;

    @VisibleForTesting
    public AbilityView(Context context, Ability ability) {
        super(context);
        inflate(context, R.layout.ability_view, this);
        this.context = context;
        this.ability = ability;
        initView();
        initValues();
    }

    private void initView() {
        abilityImageview = (ImageView) findViewById(R.id.ability_image);
        abilityAbbreviation = (TextView) findViewById(R.id.ability_abbreviation);
        abilityIndicator = (TextView) findViewById(R.id.ability_indicator);

        abilityEditor = findViewById(R.id.ability_editor);
        abilityEditText = (EditText) findViewById(R.id.ability_editor_edittext);
        abilityEditorButtonPlus = (Button) findViewById(R.id.ability_editor_button_plus);
        abilityEditorButtonMin = (Button) findViewById(R.id.ability_editor_button_min);

        abilityEditButton = (ImageButton) findViewById(R.id.ability_edit_button);

        abilityEditText.setOnEditorActionListener(this);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        abilityEditButton.setOnClickListener(this);
        abilityEditorButtonPlus.setOnClickListener(this);
        abilityEditorButtonMin.setOnClickListener(this);
    }

    private void initValues() {
        abilityImageview.setImageResource(ability.getImageRes());
        abilityImageview.setContentDescription(ability.getImageDescription());
        abilityAbbreviation.setText(ability.getAbbreviation());
        abilityIndicator.setText(String.valueOf(ability.loadValue()));
        setEditorNumberValue(ability.loadValue());
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.ability_edit_button) {
            toggleEditor();
        } else if (viewId == R.id.ability_editor_button_plus) {
            addValue();
        } else if (viewId == R.id.ability_editor_button_min) {
            substractValue();
        }
    }

    private void toggleEditor() {
        boolean startEditting = abilityIndicator.getVisibility() == VISIBLE;
        if (startEditting) {
            abilityIndicator.setVisibility(INVISIBLE);
            abilityEditor.setVisibility(VISIBLE);
            abilityEditButton.setImageResource(R.drawable.ic_action_save);
            abilityEditButton.setContentDescription(App.getResourceString(R.string.image_description_ability_save));
        } else {
            saveAbilityValue();
            abilityIndicator.setVisibility(VISIBLE);
            abilityEditor.setVisibility(INVISIBLE);
            abilityEditButton.setImageResource(R.drawable.ic_edit);
            abilityEditButton.setContentDescription(App.getResourceString(R.string.image_description_ability_edit));
        }
    }

    private void saveAbilityValue() {
        validateInput(abilityEditText);
        ability.saveValue(getEditorNumberValue());
        abilityIndicator.setText(getEditorValue());
        KeyboardUtil.hideKeyboard(abilityEditText);
    }

    private int getEditorNumberValue() {
        return Integer.valueOf(getEditorValue());
    }

    private String getEditorValue() {
        return abilityEditText.getText().toString();
    }

    private void addValue() {
        int editorValue = getEditorNumberValue();
        editorValue++;
        setEditorNumberValue(editorValue);
    }

    private void substractValue() {
        int editorValue = getEditorNumberValue();
        editorValue--;
        setEditorNumberValue(editorValue);
    }

    private void setEditorNumberValue(int editorValue) {
        abilityEditText.setText(String.valueOf(editorValue));
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            toggleEditor();
        }
        return false; // Voer de standaard action uit: done = verberg soft keyboard
    }

    private void validateInput(TextView textView) {
        String value = textView.getText().toString();
        if (value.isEmpty()) {
            setEditorNumberValue(0);
        }
    }
}
