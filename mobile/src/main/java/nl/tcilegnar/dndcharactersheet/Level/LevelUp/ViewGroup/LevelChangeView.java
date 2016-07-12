package nl.tcilegnar.dndcharactersheet.Level.LevelUp.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelChange;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelChange.ChangeLevelListener;
import nl.tcilegnar.dndcharactersheet.R;

public class LevelChangeView extends LinearLayout implements OnClickListener {
    private ImageButton changeLevelButton;
    private TextView changeLevelTimes;

    private LevelChange levelChange;

    public LevelChangeView(Context context, AttributeSet attrs) {
        this(context, attrs, new LevelChange());
    }

    @VisibleForTesting
    protected LevelChangeView(Context context, AttributeSet attrs, LevelChange levelChange) {
        super(context, attrs, R.attr.levelUpStyle);
        this.levelChange = levelChange;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.level_change, this);
        initViews();

        handleChangeLevelButtonVisibility();
    }

    private void initViews() {
        changeLevelButton = (ImageButton) findViewById(R.id.change_level_button);
        changeLevelTimes = (TextView) findViewById(R.id.change_level_times);

        changeLevelButton.setOnClickListener(this);
    }

    private void handleChangeLevelButtonVisibility() {
        if (levelChange.isReadyForLevelDown() || levelChange.isReadyForLevelUp()) {
            changeLevelButton.setVisibility(View.VISIBLE);
            if (levelChange.showNumberOfLevelsReadyForChange()) {
                changeLevelTimes.setVisibility(View.VISIBLE);
                setChangeLevelTimesText();
            }
        } else {
            changeLevelButton.setVisibility(View.GONE);
            changeLevelTimes.setVisibility(View.GONE);
        }
    }

    private void setChangeLevelTimesText() {
        changeLevelTimes.setText("x " + levelChange.getNumberOfLevelsReadyForChange());
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