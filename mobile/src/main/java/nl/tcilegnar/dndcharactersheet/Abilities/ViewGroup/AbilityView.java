package nl.tcilegnar.dndcharactersheet.Abilities.ViewGroup;

import android.content.Context;
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

import nl.tcilegnar.dndcharactersheet.Abilities.Ability;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.KeyboardUtil;

public class AbilityView extends LinearLayout implements OnClickListener, OnEditorActionListener {
    private Ability ability;

    private ImageView abilityImageview;
    private TextView abilityAbbreviation;

    private TextView abilityIndicator;
    private AbilityNumberEditor abilityNumberEditor;
    private ImageButton abilitySaveButton;

    @VisibleForTesting
    public AbilityView(Context context, Ability ability) {
        super(context);
        inflate(context, R.layout.ability_view, this);
        this.ability = ability;
        initView();
        initValues(ability);
    }

    private void initView() {
        abilityImageview = (ImageView) findViewById(R.id.ability_image);
        abilityAbbreviation = (TextView) findViewById(R.id.ability_abbreviation);

        abilityIndicator = (TextView) findViewById(R.id.ability_indicator);
        abilityNumberEditor = (AbilityNumberEditor) findViewById(R.id.ability_editor);
        abilitySaveButton = (ImageButton) findViewById(R.id.ability_save_button);

        abilityNumberEditor.setOnEditorActionListener(this);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        abilityIndicator.setOnClickListener(this);
        abilitySaveButton.setOnClickListener(this);
    }

    private void initValues(Ability ability) {
        abilityImageview.setImageResource(ability.getImageRes());
        abilityImageview.setContentDescription(ability.getImageDescription());
        abilityAbbreviation.setText(ability.getAbbreviation());

        abilityIndicator.setText(String.valueOf(ability.loadValue()));
        abilityNumberEditor.setValue(ability.loadValue());
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.ability_indicator) {
            startEdit();
        } else if (viewId == R.id.ability_save_button) {
            finishEdit();
        }
    }

    private void startEdit() {
        abilityIndicator.setVisibility(INVISIBLE);
        abilityNumberEditor.setVisibility(VISIBLE);
        abilitySaveButton.setVisibility(VISIBLE);
    }

    private void finishEdit() {
        saveAbilityValue();
        abilityIndicator.setVisibility(VISIBLE);
        abilityNumberEditor.setVisibility(INVISIBLE);
        abilitySaveButton.setVisibility(INVISIBLE);
    }

    private void saveAbilityValue() {
        abilityNumberEditor.validateInput();
        ability.saveValue(abilityNumberEditor.getNumberValue());
        abilityIndicator.setText(abilityNumberEditor.getValue());
        KeyboardUtil.hideKeyboard(abilityNumberEditor);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            finishEdit();
        }
        return false; // Voer de standaard action uit: done = verberg soft keyboard
    }
}
