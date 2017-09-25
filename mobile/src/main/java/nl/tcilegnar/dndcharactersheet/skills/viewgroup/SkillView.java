package nl.tcilegnar.dndcharactersheet.skills.viewgroup;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.skills.model.Skill;

public class SkillView extends LinearLayout {
    private final Skill skill;

    private TextView skillTitleView;
    private TextView skillBonusView;
    private TextView skillAbilityValueView;
    private TextView skillClassCheckbox;
    private TextView skillRankView;

    public SkillView(Context context, Skill skill) {
        super(context);
        inflate(context, R.layout.skill_view, this);
        this.skill = skill;
        initView();
        //        initValues(skill);
    }

    private void initView() {
        skillTitleView = (TextView) findViewById(R.id.skill_title);
        skillBonusView = (TextView) findViewById(R.id.skill_bonus);
        skillAbilityValueView = (TextView) findViewById(R.id.skill_ability_value);
        skillClassCheckbox = (CheckBox) findViewById(R.id.skill_class_checkbox);
        skillRankView = (TextView) findViewById(R.id.skill_rank);
    }
}
