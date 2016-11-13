package nl.tcilegnar.dndcharactersheet.Abilities.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Abilities.Ability;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class AbilityView extends LinearLayout {
    private final Storage storage;
    private final Ability ability;

    private ImageView abilityImageview;
    private TextView abilityAbbreviation;
    private TextView abilityIndicator;

    public AbilityView(Context context, Ability ability) {
        this(context, ability, new Storage());
    }

    @VisibleForTesting
    public AbilityView(Context context, Ability ability, Storage storage) {
        super(context);
        inflate(context, R.layout.ability_view, this);
        this.ability = ability;
        this.storage = storage;
        initView();
        initValues();
    }

    private void initView() {
        abilityImageview = (ImageView) findViewById(R.id.ability_image);
        abilityAbbreviation = (TextView) findViewById(R.id.ability_abbreviation);
        abilityIndicator = (TextView) findViewById(R.id.ability_indicator);
    }

    private void initValues() {
        abilityImageview.setImageResource(ability.getImageRes());
        abilityImageview.setContentDescription(ability.getImageDescription());
        abilityAbbreviation.setText(ability.getAbbreviation());
        abilityIndicator.setText("1");
    }
}
