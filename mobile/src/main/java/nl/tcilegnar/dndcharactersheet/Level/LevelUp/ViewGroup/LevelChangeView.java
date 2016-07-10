package nl.tcilegnar.dndcharactersheet.Level.LevelUp.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Level.LevelUp.ExperienceRemainder;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelChange;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelChange.ChangeLevelListener;
import nl.tcilegnar.dndcharactersheet.R;

public class LevelChangeView extends LinearLayout implements OnClickListener {
    private ImageButton changeLevelButton;
    private TextView experienceRemainderText;

    private LevelChange levelChange;
    private ExperienceRemainder experienceRemainder;

    public LevelChangeView(Context context, AttributeSet attrs) {
        this(context, attrs, new LevelChange(), new ExperienceRemainder());
    }

    @VisibleForTesting
    protected LevelChangeView(Context context, AttributeSet attrs, LevelChange levelChange, ExperienceRemainder
            experienceRemainder) {
        super(context, attrs, R.attr.levelUpStyle);
        this.levelChange = levelChange;
        this.experienceRemainder = experienceRemainder;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.level_change, this);
        initViews();

        handleChangeLevelButtonVisibility();
        setExperienceRemainderText();
    }

    private void initViews() {
        changeLevelButton = (ImageButton) findViewById(R.id.change_level_button);
        experienceRemainderText = (TextView) findViewById(R.id.experience_remainder);

        changeLevelButton.setOnClickListener(this);
    }

    private void handleChangeLevelButtonVisibility() {
        if (levelChange.isReadyForLevelDown() || levelChange.isReadyForLevelUp()) {
            changeLevelButton.setVisibility(View.VISIBLE);
            experienceRemainderText.setVisibility(View.VISIBLE);
        } else {
            changeLevelButton.setVisibility(View.INVISIBLE);
            experienceRemainderText.setVisibility(View.INVISIBLE);
        }
    }

    private void setExperienceRemainderText() {
        if (experienceRemainderText.getVisibility() == View.VISIBLE) {
            experienceRemainderText.setText(experienceRemainder.getExperienceRemainder());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.change_level_button) {
            handleChangeLevel();
        }
    }

    private void handleChangeLevel() {
        levelChange.handleLevelChange();
        handleChangeLevelButtonVisibility();
    }

    public void save() {
        levelChange.save();
        experienceRemainder.save();
    }

    public void onReadyForLevelUp() {
        levelChange.onReadyForLevelUp();
        handleChangeLevelButtonVisibility();
    }

    public void onReadyForLevelDown() {
        levelChange.onReadyForLevelDown();
        handleChangeLevelButtonVisibility();
    }

    public void setChangeLevelListener(ChangeLevelListener changeLevelListener) {
        levelChange.setChangeLevelListener(changeLevelListener);
    }
}