package nl.tcilegnar.dndcharactersheet.abilities.viewgroup;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.KeyboardUtil;
import nl.tcilegnar.dndcharactersheet.abilities.entities.Ability;

public class AbilityView extends LinearLayout implements OnClickListener, OnEditorActionListener {
    private Ability ability;

    private ImageView abilityImageview;
    private TextView abilityAbbreviation;

    private TextView abilityValue;
    private TextView abilityModifier;
    private AbilityNumberEditor abilityNumberEditor;
    private ImageButton abilitySaveButton;

    private ImageView abilityIconTemp;
    private TextView abilityValueTemp;
    private TextView abilityModifierTemp;

    private boolean isTempActivated;

    @VisibleForTesting
    public AbilityView(Context context, Ability ability) {
        super(context);
        inflate(context, R.layout.ability_view, this);
        this.ability = ability;
        initView();
        initValues(ability);
        setTextColor();
    }

    private void initView() {
        abilityImageview = (ImageView) findViewById(R.id.ability_image);
        abilityAbbreviation = (TextView) findViewById(R.id.ability_abbreviation);

        abilityValue = (TextView) findViewById(R.id.ability_value);
        abilityModifier = (TextView) findViewById(R.id.ability_modifier);
        abilityNumberEditor = (AbilityNumberEditor) findViewById(R.id.ability_editor);
        abilitySaveButton = (ImageButton) findViewById(R.id.ability_save_button);

        abilityIconTemp = (ImageView) findViewById(R.id.ability_icon_temp);
        abilityValueTemp = (TextView) findViewById(R.id.ability_value_temp);
        abilityModifierTemp = (TextView) findViewById(R.id.ability_modifier_temp);

        abilityNumberEditor.setOnEditorActionListener(this);
        setOnClickListeners();

        deactivateTempViews();
    }

    private void setOnClickListeners() {
        abilityValue.setOnClickListener(this);
        abilityModifier.setOnClickListener(this);
        abilitySaveButton.setOnClickListener(this);

        abilityIconTemp.setOnClickListener(this);
    }

    private void initValues(Ability ability) {
        abilityImageview.setImageResource(ability.getImageRes());
        abilityImageview.setContentDescription(ability.getImageDescription());
        abilityAbbreviation.setText(ability.getAbbreviation());

        updateValues(ability.loadValue());
    }

    private void updateValues(int value) {
        abilityValue.setText(String.valueOf(value));
        abilityNumberEditor.setValue(value);
        abilityModifier.setText(getModifierText(value));
    }

    private String getModifierText(int value) {
        int modifier = (value - 10) / 2;
        String modifierText = String.valueOf(modifier);
        if (modifier > 0) {
            modifierText = "+" + modifierText;
        }
        return modifierText;
    }

    private void setTextColor() {
        @ColorInt int color = ability.getColor();
        abilityAbbreviation.setTextColor(color);
        abilityValue.setTextColor(color);
        abilityModifier.setTextColor(color);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.ability_value || viewId == R.id.ability_modifier) {
            startEdit();
        } else if (viewId == R.id.ability_save_button) {
            finishEdit();
        } else if (viewId == R.id.ability_icon_temp) {
            if (isTempActivated) {
                deactivateTempViews();
            } else {
                showDialog();
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            finishEdit();
        }
        return false; // Voer de standaard action uit: done = verberg soft keyboard
    }

    private void startEdit() {
        abilityValue.setVisibility(INVISIBLE);
        abilityModifier.setVisibility(INVISIBLE);
        abilityNumberEditor.setVisibility(VISIBLE);
        abilitySaveButton.setVisibility(VISIBLE);
    }

    private void finishEdit() {
        saveAbilityValue();
        abilityValue.setVisibility(VISIBLE);
        abilityModifier.setVisibility(VISIBLE);
        abilityNumberEditor.setVisibility(INVISIBLE);
        abilitySaveButton.setVisibility(INVISIBLE);
    }

    private void saveAbilityValue() {
        abilityNumberEditor.validateInput();
        int value = abilityNumberEditor.getNumberValue();
        ability.saveValue(value);
        updateValues(value);
        KeyboardUtil.hideKeyboard(abilityNumberEditor);
    }

    private void activateTempViews() {
        isTempActivated = true;
        abilityValueTemp.setVisibility(VISIBLE);
        abilityModifierTemp.setVisibility(VISIBLE);
        abilityValue.setAlpha(0.2F);
        abilityModifier.setAlpha(0.2F);
        abilityIconTemp.setAlpha(1F);
    }

    private void deactivateTempViews() {
        isTempActivated = false;
        abilityValueTemp.setVisibility(INVISIBLE);
        abilityModifierTemp.setVisibility(INVISIBLE);
        abilityValue.setAlpha(1F);
        abilityModifier.setAlpha(1F);
        abilityIconTemp.setAlpha(0.2F);
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Temp ability points");
        alert.setMessage("How many temporary ability points?");

        final EditText edittext = getEditText();
        alert.setView(edittext);

        alert.setPositiveButton("+", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int inputValue = Integer.valueOf(edittext.getText().toString());
                changeTempAbility(inputValue);
                activateTempViews();
            }
        });

        alert.setNegativeButton("-", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int inputValue = Integer.valueOf(edittext.getText().toString());
                changeTempAbility(-inputValue);
                activateTempViews();
            }
        });

        alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    private EditText getEditText() {
        final EditText edittext = new EditText(getContext());
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        return edittext;
    }

    private void changeTempAbility(int inputValue) {
        int tempAbilityValue = abilityNumberEditor.getNumberValue() + inputValue;
        abilityValueTemp.setText(String.valueOf(tempAbilityValue));
        abilityModifierTemp.setText(getModifierText(tempAbilityValue));
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof AbilityView) {
            AbilityView abilityView = (AbilityView) obj;
            isEqual = abilityView.getAbility().equals(this.ability);
        }
        return isEqual;
    }
}
