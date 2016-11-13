package nl.tcilegnar.dndcharactersheet.Abilities.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import nl.tcilegnar.dndcharactersheet.Abilities.Ability;
import nl.tcilegnar.dndcharactersheet.Abilities.ViewGroup.AbilityView;

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
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
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
