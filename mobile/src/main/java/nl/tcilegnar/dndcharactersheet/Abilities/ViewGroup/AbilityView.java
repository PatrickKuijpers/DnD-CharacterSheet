package nl.tcilegnar.dndcharactersheet.abilities.viewGroup;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.VisibleForTesting;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
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

        abilityNumberEditor.setOnEditorActionListener(this);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        abilityValue.setOnClickListener(this);
        abilityModifier.setOnClickListener(this);
        abilitySaveButton.setOnClickListener(this);
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
