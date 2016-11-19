package nl.tcilegnar.dndcharactersheet.abilities.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import nl.tcilegnar.dndcharactersheet.abilities.entities.Ability;
import nl.tcilegnar.dndcharactersheet.abilities.viewGroup.AbilityView;

public class AbilitiesAdapter extends BaseAdapter {
    private final Context activityContext;

    public AbilitiesAdapter(Context activityContext) {
        this.activityContext = activityContext;
    }

    @Override
    public int getCount() {
        return getAbilities().length;
    }

    @Override
    public Ability getItem(int i) {
        return getAbilities()[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public AbilityView getView(int position, View convertView, ViewGroup viewGroup) {
        AbilityView abilityView;

        if (convertView == null) {
            Ability ability = getAbilities()[position];
            abilityView = new AbilityView(activityContext, ability);
        } else {
            abilityView = (AbilityView) convertView;
        }
        return abilityView;
    }

    private Ability[] getAbilities() {
        return Ability.values();
    }
}
