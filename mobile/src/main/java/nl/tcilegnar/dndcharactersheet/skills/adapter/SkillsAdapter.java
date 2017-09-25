package nl.tcilegnar.dndcharactersheet.skills.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.List;

import nl.tcilegnar.dndcharactersheet.skills.model.Skill;
import nl.tcilegnar.dndcharactersheet.skills.viewgroup.SkillView;

public class SkillsAdapter extends BaseAdapter {
    private final Context activityContext;
    private final List<Skill> skills;

    public SkillsAdapter(Context activityContext) {
        this.activityContext = activityContext;
        this.skills = Arrays.asList(Skill.values());
    }

    @Override
    public int getCount() {
        return skills.size();
    }

    @Override
    public Skill getItem(int i) {
        return skills.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public SkillView getView(int position, View convertView, ViewGroup viewGroup) {
        SkillView skillView;

        if (convertView == null) {
            Skill skill = skills.get(position);
            skillView = new SkillView(activityContext, skill);
        } else {
            skillView = (SkillView) convertView;
        }
        return skillView;
    }
}
