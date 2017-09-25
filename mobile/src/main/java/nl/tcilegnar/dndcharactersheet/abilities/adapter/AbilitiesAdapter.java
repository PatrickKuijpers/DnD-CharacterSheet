package nl.tcilegnar.dndcharactersheet.abilities.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.tcilegnar.dndcharactersheet.abilities.entities.Ability;
import nl.tcilegnar.dndcharactersheet.abilities.viewgroup.AbilityView;

public class AbilitiesAdapter extends BaseAdapter {
    private final Context activityContext;
    private final List<Ability> abilities;

    public AbilitiesAdapter(Context activityContext) {
        this.activityContext = activityContext;
        this.abilities = getOrderedAbilities();
    }

    private List<Ability> getOrderedAbilities() {
        List<Ability> abilities = Arrays.asList(Ability.values());
        Collections.sort(abilities, new Ability.OrderComparator());
        return abilities;
    }

    @Override
    public int getCount() {
        return abilities.size();
    }

    @Override
    public Ability getItem(int i) {
        return abilities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public AbilityView getView(int position, View convertView, ViewGroup viewGroup) {
        AbilityView abilityView;

        if (convertView == null) {
            Ability ability = abilities.get(position);
            abilityView = new AbilityView(activityContext, ability);
        } else {
            abilityView = (AbilityView) convertView;
        }
        return abilityView;
    }
}
